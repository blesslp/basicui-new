package top.blesslp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import top.blesslp.R;
import top.blesslp.intf.BackInterceptorIntf;

public class BasicFragmentActivity extends QMUIFragmentActivity {
    @Override
    protected int getContextViewId() {
        return R.id.fragment_container;
    }

    protected void launchFragmentAtFirstPage(QMUIFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public static Intent of(Class<? extends BasicFragmentActivity> clazz, Context context, Class<? extends QMUIFragment> qmuiFragmentClass, Bundle args) {
        return of(clazz, context, qmuiFragmentClass, args, false);
    }


    public static Intent of(Class<? extends BasicFragmentActivity> clazz, Context context, Class<? extends QMUIFragment> qmuiFragmentClass, Bundle args, boolean singleTask) {
        Intent it = new Intent(context, clazz);
        it.putExtra("TAG_CLASS", qmuiFragmentClass);
        it.putExtra("TAG_ARGS", args);
        it.putExtra("TAG_MODE", singleTask);
        if (singleTask) {
            it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return it;
    }

    @Override
    public void onBackPressed() {

        //控制fragment可不可以返回
        QMUIFragment currentFragment = getCurrentFragment();
        if (currentFragment != null && currentFragment instanceof BackInterceptorIntf) {
            if (((BackInterceptorIntf) currentFragment).onPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
