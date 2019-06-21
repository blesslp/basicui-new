package top.blesslp.com.sample.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragment;

import top.blesslp.com.sample.R;
import top.blesslp.ui.BasicFragment;
import top.blesslp.ui.PermissionGrantedListener;

public class BFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnJump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(0xff, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, "请打开定位权限", new PermissionGrantedListener() {
                    @Override
                    public void onGranted(int requestCode) {
                        startFragmentForResult(new CFragment(),0xff);
                    }
                });


            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == QMUIFragment.RESULT_OK && requestCode == 0xff) {
            TextView txtContent = getView().findViewById(R.id.txtResult);
            txtContent.setText(data.getStringExtra("Content"));
        }
    }
}
