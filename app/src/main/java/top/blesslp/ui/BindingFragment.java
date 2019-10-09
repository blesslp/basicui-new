package top.blesslp.ui;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BindingFragment<T extends ViewDataBinding> extends BasicFragment implements DataBindingable {

    private T mBinder;

    @Override
    public T getBinder() {
        return mBinder;
    }

    @Override
    protected void setContent4Baisc(View rootView) {
        setContent4Binding(rootView);
    }

    private void setContent4Binding(View rootView) {
        mBinder = DataBindingUtil.bind(rootView);
    }
}
