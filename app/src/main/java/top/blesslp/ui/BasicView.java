package top.blesslp.ui;

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

public abstract class BasicView implements IBaseView, UIEventFun {
    private DialogProxy mDialogProxy;
    private PresenterProxy mPresenterProxy;
    private Context _context;
    private View rootView;
    private Object target;
    private List<IViewEventChannel> viewChannels = new ArrayList<>();
    private Handler mHandler = new Handler();

    /**
     * 添加监听器
     * @param channel
     */
    public void addViewChannel(IViewEventChannel channel) {
        if(channel == null) return;
        viewChannels.add(channel);
    }

    /**
     * 移除监听器
     * @param channel
     */
    public void removeViewChannel(IViewEventChannel channel) {
        if(channel == null) return;
        viewChannels.remove(channel);
    }

    /**
     * 通知事件
     * @param what
     * @param args
     */
    protected void notifyEvent(int what, Object... args) {
        for (IViewEventChannel viewChannel : viewChannels) {
            viewChannel.onBasicViewEvent(what,args);
        }
    }

    /**
     * 组建View
     * @param context   Activity
     * @param root      根View
     * @return          当前BasicView代表的布局
     */
    public View buildView(BasicActivity context, ViewGroup root) {
        this._context = context;
        this.target = context;
        this.addViewChannel(context);
        this.rootView = onCreateView(_context, root);
        this.mDialogProxy = new DialogProxy(mHandler, context, context);
        this.mPresenterProxy = new PresenterProxy(context);
        initEvent();
        return rootView;
    }
    /**
     * 组建View
     * @param context   Fragment
     * @param root      根View
     * @return          当前BasicView代表的布局
     */
    public View buildView(BasicFragment context, ViewGroup root) {
        this._context = context.getActivity();
        this.target = context;
        this.addViewChannel(context);
        this.rootView = onCreateView(_context, root);
        this.mDialogProxy = new DialogProxy(mHandler, this._context, context);
        this.mPresenterProxy = new PresenterProxy(context);
        initEvent();
        return rootView;
    }

    public Context getContext() {
        return _context;
    }

    public View getView() {
        return rootView;
    }

    protected void startFragment(BasicFragment fragment) {
        if(this.target == null) return;
        if (target instanceof BasicFragment) {
            ((BasicFragment) target).startFragment(fragment);
        }
    }

    /**
     * 获取Activity/Fragment
     * @param <T>
     * @return
     */
    public <T> T getTarget() {
        return (T) target;
    }

    protected void startFragmentAndDestroyCurrent(BasicFragment fragment) {
        if(this.target == null) return;
        if (target instanceof BasicFragment) {
            ((BasicFragment) target).startFragmentAndDestroyCurrent(fragment);
        }
    }

    protected void startActivity(Intent it) {
        if(this.target == null) return;
        if (target instanceof BasicFragment) {
            ((BasicFragment) target).startActivity(it);
        }else if(target instanceof BasicActivity){
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

    }

    @Override
    public void serviceError() {

    }

    @Override
    public void serviceNoData() {

    }

    @Override
    public void addPresenter(BasePresenter presenter) {
        mPresenterProxy.addPresenter(presenter);
    }

}
