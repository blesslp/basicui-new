package top.blesslp.com.sample.fragments;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import top.blesslp.com.sample.R;
import top.blesslp.ui.BasicFragment;
import top.blesslp.utils.ExBindingViewHolder;
import top.blesslp.utils.RefreshViewHelper;

public class DFragment extends BasicFragment implements RefreshViewHelper.OnRefreshListener {
    private final List<String> cities = new ArrayList<>();
    private RefreshViewHelper mRefreshHelper;
    private BaseQuickAdapter<String, ExBindingViewHolder> mAdapter = new BaseQuickAdapter<String, ExBindingViewHolder>(R.layout.item_list) {
        @Override
        protected void convert(ExBindingViewHolder helper, String item) {
            helper.setText(R.id.txtListItem, item);
        }
    };
    private int loadLevel = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_d;
    }

    @Override
    public void initRecyclerView() {
        mRefreshHelper = new RefreshViewHelper(this,true);
        mRefreshHelper.setLayoutManagerAndAdapter(new LinearLayoutManager(getContext()),mAdapter);
        mRefreshHelper.setRefreshListener(this);
    }

    @Override
    public void onRefresh(RefreshViewHelper helper, int pageNo, int pageSize) {
        setData(helper.isRefreshing());

        mRefreshHelper.finishLoadOrRefresh();
    }

    @Override
    public void initData() {
        cities.add("湖北");
        cities.add("浙江");
        cities.add("黑龙江");
        cities.add("重庆");
        cities.add("上海");
        cities.add("北京");
        cities.add("四川");
        cities.add("吉林");
        cities.add("山西");
        cities.add("陕西");
        cities.add("山东");
        cities.add("台湾");
        cities.add("香港");

        mAdapter.setNewData(new ArrayList(cities));

    }

    private void setData(boolean isRefresh) {
        if (isRefresh) {
            mAdapter.setNewData(new ArrayList(cities));
        }else{
            loadLevel ++;
            ArrayList<String> temp = new ArrayList<>(cities);
            temp.add(0,"新加载地区");
            mAdapter.addData(temp);

            if (loadLevel >= 3) {
                mRefreshHelper.setOnLoadNoData(true);
            }

        }
    }
}
