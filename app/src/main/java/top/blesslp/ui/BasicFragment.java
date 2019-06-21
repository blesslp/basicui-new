package top.blesslp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import top.blesslp.intf.BasePresenter;
import top.blesslp.intf.IBaseView;
import top.blesslp.intf.PresenterProxy;

public abstract class BasicFragment extends QMUIFragment implements IBaseView, UIEventFun,EasyPermissions.PermissionCallbacks,IViewEventChannel {
    private DialogProxy mDialogProxy;
    private PresenterProxy mPresenterProxy;
    private Handler mUIHandler = new Handler();
    private int hasInit = 0; //IDLE
    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 权限处理
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    private PermissionWraper mPermissionWraper;

    @Override
    protected View onCreateView() {
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + "必须返回layoutId");
        }
        View rootView = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        setContent4Baisc(rootView);
        mDialogProxy = new DialogProxy(mUIHandler, getActivity(), this);
        mPresenterProxy = new PresenterProxy(this);
        mPermissionWraper = PermissionWraper.of(this);

        this.hasInit = 1;   //hasInit
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(hasInit == 1) {
            initPresenter();
            initListeners();
            initRecyclerView();
            initData();
            hasInit = 2;        //init success and not init again
        }
    }

    @Override
    public void onBasicViewEvent(int what, Object... args) {

    }

    @Override
    public void startFragment(QMUIFragment fragment) {
        super.startFragment(fragment);
    }

    @Override
    public void startFragmentAndDestroyCurrent(QMUIFragment fragment) {
        super.startFragmentAndDestroyCurrent(fragment);
    }

    @Override
    public void startFragmentAndDestroyCurrent(QMUIFragment fragment, boolean useNewTransitionConfigWhenPop) {
        super.startFragmentAndDestroyCurrent(fragment, useNewTransitionConfigWhenPop);
    }

    protected void setContent4Baisc(View rootView) {

    }

    protected abstract int getLayoutId();

    @Override
    public void showLoading(String message, boolean canCancel) {
        mDialogProxy.showLoading(message, canCancel);
    }

    @Override
    public void cancelLoading() {
        mDialogProxy.cancelDialog();
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
    public void showTips(String message) {
        mDialogProxy.showTips(message);
    }

    @Override
    public void showSuccess(String message) {
        mDialogProxy.showSuccess(message);
    }

    @Override
    public void showFailed(String message) {
        mDialogProxy.showFailed(message);
    }

    @Override
    public void showFailed(String message,Runnable task) {
        mDialogProxy.showFailed(message,task);
    }

    @Override
    public void showSuccess(String message,Runnable task) {
        mDialogProxy.showSuccess(message,task);
    }

    @Override
    public void showTips(String message,Runnable task) {
        mDialogProxy.showTips(message,task);
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

    @Override
    public void initPresenter() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean canDragBack() {
        return BasicActivity.isCanSwipeBack;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    /**
     * 请求权限
     *
     * @param permissionRequestCode 权限请求码
     * @param permissions           权限组
     * @param tips                  权限请求提示语
     * @param listener              同意的回调
     */
    protected void requestPermission(int permissionRequestCode, String[] permissions, String tips, PermissionGrantedListener listener) {
        mPermissionWraper.requestPermission(permissionRequestCode, permissions, tips, listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionWraper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        mPermissionWraper.onPermissionsGranted(requestCode, perms);
    }

    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        mPermissionWraper.onPermissionsDenied(requestCode, perms);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionWraper.handleFromSettings(requestCode, resultCode, data);
    }
}
