package top.blesslp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import top.blesslp.intf.BasePresenter;
import top.blesslp.intf.IBaseView;
import top.blesslp.intf.PresenterProxy;

public abstract class BasicActivity extends QMUIActivity implements IBaseView, UIEventFun, EasyPermissions.PermissionCallbacks ,IViewEventChannel{

    public static boolean isCanSwipeBack = false;
    private Handler mHandler = new Handler();
    private DialogProxy mDialogProxy;
    private PresenterProxy mPresenterProxy;
    private PermissionWraper mPermissionWraper;

    @Override
    protected boolean canDragBack() {
        return isCanSwipeBack;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(this, 100);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogProxy = new DialogProxy(mHandler, this, this);
        mPresenterProxy = new PresenterProxy(this);
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + "必须返回layoutId");
        }
        mPermissionWraper = PermissionWraper.of(this);
        setContentView4Basic(layoutId);
        initPresenter();
        initListeners();
        initRecyclerView();
        initData();
    }

    /**
     * 常规基类设置视图
     *
     * @param layoutId
     */
    protected void setContentView4Basic(int layoutId) {
        setContentView(layoutId);
    }

    @Override
    public void onBasicViewEvent(int what, Object... args) {

    }

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

    protected abstract int getLayoutId();

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
    protected void onStop() {
        super.onStop();
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void addPresenter(BasePresenter presenter) {
        mPresenterProxy.addPresenter(presenter);
    }

    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * 权限处理
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

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
