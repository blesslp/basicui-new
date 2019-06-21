package top.blesslp.com.sample;

import android.os.Bundle;

import top.blesslp.com.sample.fragments.AFragment;
import top.blesslp.ui.BasicFragmentActivity;

public class LauncherActivity extends BasicFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            launchFragmentAtFirstPage(new AFragment());
        }
    }

}
