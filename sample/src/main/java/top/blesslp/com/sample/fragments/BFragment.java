package top.blesslp.com.sample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import top.blesslp.com.sample.R;
import top.blesslp.com.sample.presenter.Banner;
import top.blesslp.com.sample.presenter.DemoContract;
import top.blesslp.ui.BasicFragment;

public class BFragment extends BasicFragment implements DemoContract.IDemoView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    private DemoContract.DemoPresenter presenter;

    @Override
    public void initPresenter() {
        presenter = new DemoContract.DemoPresenter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnJump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               presenter.getBannerList();

            }
        });
    }


    @Override
    public void onBannerLoaded(List<Banner> banners) {
        showSuccess(banners.toString());
    }
}
