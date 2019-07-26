package top.blesslp.intf;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

public interface IRefreshHelper_EmptyView {

    void initEmptyView(BaseQuickAdapter mAdapter,View emptyView);

    void finishRefresh(BaseQuickAdapter mAdapter);

}
