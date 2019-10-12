package top.blesslp.ui;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public abstract class PermissionWraper<T> {
    ContainerHost<T> containerHost;
    private PermissionSession mPermissionSession;
    //用户点击不启用权限, 并且拒绝允许, 则提示一次之后 ,就不再提示了,否则会无限循环
    private boolean isDeniedPermissionAndNotModify = false;

    PermissionWraper(T activity) {
    }

    public static PermissionWraper of(Object host) {
        if (host instanceof Fragment) {
            return new PermissionFragmentImpl((Fragment) host);
        } else if (host instanceof Activity) {
            return new PermissionActivityImpl((Activity) host);
        }
        throw new IllegalArgumentException("host仅支持Fragment/Activity");
    }

    abstract PermissionRequest buildPermissionRequest(int permissionRequestCode, String[] permissions, String tips, PermissionGrantedListener listener);

    abstract T getHost();

    abstract ContainerHost<T> getContainerHost();

    abstract void dialog2Permission();

    abstract boolean somePermissionPermanentlyDenied(List<String> perms);

    abstract boolean hasPermissions(String[] perms);

    void requestPermission(int permissionRequestCode, String[] permissions, String tips, PermissionGrantedListener listener) {
        if (mPermissionSession == null)
            mPermissionSession = new PermissionSession(permissionRequestCode, permissions, tips, listener);
        PermissionRequest build = buildPermissionRequest(permissionRequestCode, permissions, tips, listener);
        EasyPermissions.requestPermissions(build);
    }

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mPermissionSession == null) return;
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getHost());
    }

    void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //用户同意了权限
        if(mPermissionSession == null) return;
        int grantedPermsSize = perms.size();
        int userNeedPermsSize = mPermissionSession.permissions.length;
        //如果用户已同意的权限数 == 所有申请的权限数,则代表完全同意权限
        if (grantedPermsSize == userNeedPermsSize && mPermissionSession.listener != null) {
            mPermissionSession.listener.onGranted(requestCode);
        }
    }

    void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(mPermissionSession == null) return;
        //判断是不是某些权限被永久关闭了,如果有, 则让其去设置里面开
        if (somePermissionPermanentlyDenied(perms) && !isDeniedPermissionAndNotModify) {
            this.isDeniedPermissionAndNotModify = true;
            dialog2Permission();
        }
    }

    void handleFromSettings(int requestCode, int resultCode, Intent data) {
        if(mPermissionSession == null) return;
        //从权限设置页面跳回
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            isDeniedPermissionAndNotModify = false;
            if (hasPermissions(mPermissionSession.permissions) && mPermissionSession.listener != null) {
                //如果在设置里面, 用户已经打开所需要权限,则可以进入到回调中去
//                requestPermission(mPermissionSession.permissionRequestCode,mPermissionSession.permissions,mPermissionSession.tips,mPermissionSession.listener);
                mPermissionSession.listener.onGranted(mPermissionSession.permissionRequestCode);
            }
        }
    }

    interface ContainerHost<T> {
        void set(T host);

        T get();
    }

    final static class FragemntContainerHost implements ContainerHost<Fragment> {
        private Fragment host;

        @Override
        public void set(Fragment host) {
            this.host = host;
        }

        @Override
        public Fragment get() {
            return host;
        }
    }

    final static class ActivityContainerHost implements ContainerHost<Activity> {
        private Activity host;

        @Override
        public void set(Activity host) {
            this.host = host;
        }

        @Override
        public Activity get() {
            return host;
        }
    }

    private final static class PermissionSession {
        private int permissionRequestCode;
        private String[] permissions;
        private String tips;
        private PermissionGrantedListener listener;

        public PermissionSession(int permissionRequestCode, String[] permissions, String tips, PermissionGrantedListener listener) {
            this.permissionRequestCode = permissionRequestCode;
            this.permissions = permissions;
            this.tips = tips;
            this.listener = listener;
        }
    }

    static class PermissionFragmentImpl extends PermissionWraper<Fragment> {

        public PermissionFragmentImpl(Fragment activity) {
            super(activity);
            this.containerHost = new FragemntContainerHost();
            this.containerHost.set(activity);
        }

        @Override
        PermissionRequest buildPermissionRequest(int permissionRequestCode, String[] permissions, String tips, PermissionGrantedListener listener) {
            return new PermissionRequest.Builder(containerHost.get()
                    , permissionRequestCode, permissions)
                    .setRationale(tips)
                    .setPositiveButtonText("授权")
                    .setNegativeButtonText("拒绝")
                    .build();
        }

        @Override
        Fragment getHost() {
            return this.containerHost.get();
        }

        @Override
        ContainerHost<Fragment> getContainerHost() {
            return this.containerHost;
        }

        @Override
        void dialog2Permission() {
            new AppSettingsDialog.Builder(getHost())
                    .setTitle("温馨提醒")
                    .setPositiveButton("设置")
                    .setNegativeButton("取消")
                    .setRationale("您当前关闭了部分授权, 这将影响app的正常使用\n您可以从 设置->权限管理->对应权限设置为允许")
                    .build().show();
        }

        @Override
        boolean somePermissionPermanentlyDenied(List<String> perms) {
            return EasyPermissions.somePermissionPermanentlyDenied(getHost(), perms);
        }

        @Override
        boolean hasPermissions(String[] perms) {
            return EasyPermissions.hasPermissions(getHost().getContext(), perms);
        }
    }

    static class PermissionActivityImpl extends PermissionWraper<Activity> {
        public PermissionActivityImpl(Activity activity) {
            super(activity);
            this.containerHost = new ActivityContainerHost();
            this.containerHost.set(activity);
        }


        @Override
        PermissionRequest buildPermissionRequest(int permissionRequestCode, String[] permissions, String tips, PermissionGrantedListener listener) {
            return new PermissionRequest.Builder(containerHost.get()
                    , permissionRequestCode, permissions)
                    .setRationale(tips)
                    .setPositiveButtonText("授权")
                    .setNegativeButtonText("拒绝")
                    .build();
        }

        @Override
        Activity getHost() {
            return this.containerHost.get();
        }

        @Override
        ContainerHost<Activity> getContainerHost() {
            return this.containerHost;
        }

        @Override
        void dialog2Permission() {
            new AppSettingsDialog.Builder(getHost())
                    .setTitle("温馨提醒")
                    .setPositiveButton("设置")
                    .setNegativeButton("取消")
                    .setRationale("您当前关闭了部分授权, 这将影响app的正常使用\n您可以从 设置->权限管理->对应权限设置为允许")
                    .build().show();
        }

        @Override
        boolean somePermissionPermanentlyDenied(List<String> perms) {
            return EasyPermissions.somePermissionPermanentlyDenied(getHost(), perms);
        }

        @Override
        boolean hasPermissions(String[] perms) {
            return EasyPermissions.hasPermissions(getHost(), perms);
        }

    }


}
