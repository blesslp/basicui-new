package top.blesslp.com.sample.fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import top.blesslp.com.sample.R;
import top.blesslp.ui.BasicFragment;
import top.blesslp.ui.PermissionGrantedListener;

public class AFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnJump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(0xff, new String[]{Manifest.permission.CAMERA}, "请打开相机权限", new PermissionGrantedListener() {
                    @Override
                    public void onGranted(int requestCode) {
                        startFragment(new BFragment());
                    }
                });

            }
        });

    }

}
