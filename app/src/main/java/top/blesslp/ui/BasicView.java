package top.blesslp.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.blesslp.intf.BasePresenter;
import top.blesslp.intf.IBaseView;
import top.blesslp.intf.PresenterProxy;

public abstract class BasicView implements IBaseView, UIEventFun, LifecycleObserver {
    private DialogProxy mDialogProxy;
    private PresenterProxy mPresenterProxy;
    private Context _context;
    private View rootView;
    private Object target;
    private List<IViewEventChannel> viewChannels = new ArrayList<>();
    private Handler mHandler = new Handler();

    /**
     * 添加监听器
     *
     * @param channel
     */
    public void addViewChannel(IViewEventChannel channel) {
        if (channel == null || viewChannels.contains(channel)) return;
        viewChannels.add(channel);
    }

    /**
     * 移除监听器
     *
     * @param channel
     */
    public void removeViewChannel(IViewEventChannel channel) {
        if (channel == null) return;
        viewChannels.remove(channel);
    }

    /**
     * 通知事件
     *
     * @param what
     * @param args
     */
    protected void notifyEvent(int what, Object... args) {
        for (IViewEventChannel viewChannel : viewChannels) {
            viewChannel.onBasicViewEvent(what, args);
        }
    }

    /**
     * 组建View
     *
     * @param context Activity
     * @param root    根View
     * @return 当前BasicView代表的布局
     */
    public View buildView(BasicActivity context, ViewGroup root) {
        this._context = context;
        initBasicView(context, root);
        return rootView;
    }

    /**
     * 组建View
     *
     * @param context Fragment
     * @param root    根View
     * @return 当前BasicView代表的布局
     */
    public View buildView(BasicFragment context, ViewGroup root) {
        this._context = context.getActivity();
        initBasicView(context, root);
        return rootView;
    }

    private void initBasicView(IViewEventChannel context, ViewGroup root) {
        this.target = context;
        this.addViewChannel(context);
        this.rootView = onCreateView(_context, root);
        this.mDialogProxy = new DialogProxy(mHandler, this._context, (LifecycleOwner) context);
        this.mPresenterProxy = new PresenterProxy((LifecycleOwner) context);
        ((LifecycleOwner) context).getLifecycle().addObserver(this);
        initEvent();
    }

    void onStop(){}

    void onPause(){}

    void onResume(){}

    void onCreate(){}

    void onDestroy(){}


    public Context getContext() {
        return _context;
    }

    public View getView() {
        return rootView;
    }

    protected void startFragment(BasicFragment fragment) {
        if (this.target == null) return;
        if (target instanceof BasicFragment) {
            ((BasicFragment) target).startFragment(fragment);
        }
    }

    /**
     * 获取Activity/Fragment
     *
     * @param <T>
     * @return
     */
    public <T> T getTarget() {
        return (T) target;
    }

    protected void startFragmentAndDestroyCurrent(BasicFragment fragment) {
        if (this.target == null) return;
        if (target instanceof BasicFragment) {
            ((BasicFragment) target).startFragmentAndDestroyCurrent(fragment);
        }
    }

    protected void startActivity(Intent it) {
        if (this.target == null) return;
        if (target instanceof BasicFragment) {
            ((BasicFragment) target).startActivity(it);
        } else if (target instanceof BasicActivity) {
            ((BasicActivity) target).startActivity(it);
        }
    }

    private void initEvent() {
        onViewCreated(getView());
        initPresenter();
        initListeners();
        initRecyclerView();
        initData();
    }

    public void onViewCreated(View view) {

    }

    public abstract View onCreateView(Context context, ViewGroup root);


    @Override
    public void initData() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void cancelLoading() {
        mDialogProxy.cancelDialog();
    }


    @Override
    public void showLoading(String message, boolean canCancel) {
        mDialogProxy.showLoading(message, canCancel);
    }


    @Override
    public void showToastLong(String message) {
        mDialogProxy.showLong(message);
    }

    @Override
    public void showToastShort(String message) {
        mDialogProxy.showShort(message);
    }

    @Override
    public void showFailed(String message) {
        mDialogProxy.showFailed(message);
    }

    @Override
    public void showSuccess(String message) {
        mDialogProxy.showSuccess(message);
    }

    @Override
    public void showTips(String message) {
        mDialogProxy.showTips(message);
    }


    @Override
    public void showFailed(String message, Runnable task) {
        mDialogProxy.showFailed(message, task);
    }

    @Override
    public void showSuccess(String message, Runnable task) {
        mDialogProxy.showSuccess(message, task);
    }

    @Override
    public void showTips(String message, Runnable task) {
        mDialogProxy.showTips(message, task);
    }


    @Override
    public void loginOut() {
        if(target == null)return;
        IBaseView view = getTarget();
        view.loginOut();
    }

    @Override
    public void serviceError() {

    }

    @Override
    public void serviceNoData() {

    }

    @Override
    public void addPresenter(BasePresenter presenter) {
        if(mPresenterProxy == null)return;
        mPresenterProxy.addPresenter(presenter);
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void __lifeCycle__onDestroy() {
        if (target != null && target instanceof LifecycleOwner) {
            ((LifecycleOwner) target).getLifecycle().removeObserver(this);
        }

        onDestroy();
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void __lifeCycle__onCreate() {
        onCreate();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void __lifeCycle__onPause() {
        onPause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void __lifeCycle__onStop() {
        onStop();
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void __lifeCycle__onResume() {
        onResume();
    }




}
