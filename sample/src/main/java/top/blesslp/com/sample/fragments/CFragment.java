package top.blesslp.com.sample.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import top.blesslp.com.sample.R;
import top.blesslp.ui.BasicFragment;

public class CFragment extends BasicFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.putExtra("Content", "我是从C界面获取的数据");
                setFragmentResult(RESULT_OK,it);
                popBackStack();
            }
        });
        view.findViewById(R.id.btnJump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new DFragment());
            }
        });
    }
}
