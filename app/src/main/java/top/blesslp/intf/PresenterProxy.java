package top.blesslp.intf;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PresenterProxy implements LifecycleObserver {

    private List<WeakReference<BasePresenter>> presenterReference = new ArrayList<>();
    private Lifecycle mLifeCycle;

    public PresenterProxy(LifecycleOwner lifecycleOwner) {
        this.mLifeCycle = lifecycleOwner.getLifecycle();
        mLifeCycle.addObserver(this);
    }

    public void addPresenter(BasePresenter presenter) {
        presenterReference.add(new WeakReference(presenter));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (!presenterReference.isEmpty()) {
            for (WeakReference<BasePresenter> basePresenterWeakReference : presenterReference) {
                BasePresenter basePresenter = basePresenterWeakReference.get();
                if (basePresenter != null) {
                    basePresenter.onDestory();
                    basePresenterWeakReference.clear();
                }
            }
            presenterReference.clear();
        }

        mLifeCycle.removeObserver(this);
    }
}
