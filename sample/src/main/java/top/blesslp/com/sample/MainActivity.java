package top.blesslp.com.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import top.blesslp.ui.BasicActivity;
import top.blesslp.ui.PermissionGrantedListener;

public class MainActivity extends BasicActivity {

    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCanSwipeBack = true;
        QMUIRoundButton btn = findViewById(R.id.btnJump);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(0, PERMISSIONS, "您需要授权这些访问权限,才可使用相应的功能", new PermissionGrantedListener() {
                    @Override
                    public void onGranted(int requestCode) {
                        startActivity(new Intent(MainActivity.this, LauncherActivity.class));
                    }
                });

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
