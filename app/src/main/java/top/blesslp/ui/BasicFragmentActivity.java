package top.blesslp.ui;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import top.blesslp.R;

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

}
