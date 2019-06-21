package top.blesslp.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.concurrent.atomic.AtomicInteger;

import top.blesslp.R;


/**
 * Administrator on 2017/6/29 0029.
 * 类描述：
 */

public final class RefreshViewHelper {
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshView;
    private Context mContext;

    private int currentPosition = 0;
    private int currentPosOffset = 0;
    private int initalPageNo = 1;
    private AtomicInteger mCurrentPage = new AtomicInteger(initalPageNo);
    private AtomicInteger mPageSize = new AtomicInteger(10);
    private OnRefreshListener mRefreshListener;

    public RefreshViewHelper(Activity mContext, boolean canLoadMoreEnable, boolean canRefreshEnable) {
        this.mContext = mContext;
        this.mRefreshView = (SmartRefreshLayout) mContext.findViewById(R.id.mRefreshView);
        this.mRecyclerView = (RecyclerView) mContext.findViewById(R.id.mRecyclerView);
        setRefreshEnable(canRefreshEnable);
        setLoadMoreEnable(canLoadMoreEnable);
    }

    public RefreshViewHelper(Activity mContext, boolean canLoadMoreEnable) {
        this(mContext, canLoadMoreEnable, true);
    }

    public RefreshViewHelper(View rootView, boolean canLoadMoreEnable, boolean canRefreshEnable) {
        this.mContext = rootView.getContext();
        final View contentView = rootView;
        this.mRefreshView = (SmartRefreshLayout) contentView.findViewById(R.id.mRefreshView);
        this.mRecyclerView = (RecyclerView) contentView.findViewById(R.id.mRecyclerView);
        setLoadMoreEnable(canLoadMoreEnable);
        setRefreshEnable(canRefreshEnable);
    }

    public RefreshViewHelper(Fragment mContext, boolean canLoadMoreEnable) {
        this(mContext.getView(), canLoadMoreEnable, true);
    }
    public RefreshViewHelper(Fragment mContext, boolean canLoadMoreEnable,boolean canRefreshEnable) {
        this(mContext.getView(), canLoadMoreEnable, canRefreshEnable);
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        mRefreshView.setEnableLoadMore(loadMoreEnable);
    }

    public boolean isRefreshing() {
        return mRefreshView.getState()==RefreshState.Refreshing;
    }

    public void setRefreshEnable(boolean canRefresh) {
        mRefreshView.setEnableRefresh(canRefresh);
    }

    public void setLayoutManagerAndAdapter(final RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter<?> mAdapter) {
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (mRefreshListener != null) {
                    mCurrentPage.incrementAndGet();
                    mRefreshListener.onRefresh(RefreshViewHelper.this, getPageNo(), getPageSize());
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mRefreshView.setNoMoreData(false);
                mCurrentPage.set(initalPageNo);
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh(RefreshViewHelper.this, getPageNo(), getPageSize());
                }
            }
        });

        mRecyclerView.setLayoutManager(layoutManager);
        if (mAdapter instanceof BaseQuickAdapter) {
            setEmptyView((BaseQuickAdapter) mAdapter, R.layout.layout_empty_view);
        } else {
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void setEmptyView(BaseQuickAdapter<?, ?> mAdapter, @LayoutRes int layoutId) {
        try {
            mAdapter.bindToRecyclerView(mRecyclerView);
        } catch (Exception e) {
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setEmptyView(layoutId);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public RefreshViewHelper setDivider(@DrawableRes int divider) {
        return setDivider(RecyclerViewDividerDecoration.VERTICAL, divider);
    }

    public RefreshViewHelper setDivider(int orientation, @DrawableRes int divider) {
        mRecyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this.mContext, orientation, divider));
        return this;
    }

    public RefreshViewHelper setInitalPage(int initalPageNo) {
        this.initalPageNo = initalPageNo;
        mCurrentPage.set(initalPageNo);
        return this;
    }

    public int getPageNo() {
        return mCurrentPage.get();
    }

    public int getPageSize() {
        return mPageSize.get();
    }

    public RefreshViewHelper setPageSize(int pageSize) {
        mPageSize.set(pageSize);
        return this;
    }

    public void autoRefresh() {
        mRefreshView.autoRefresh();
    }

    /**
     * 结束上拉刷新
     */
    public void setOnRefreshComplete() {
        mRefreshView.finishRefresh();
    }

    /**
     * 结束下拉加载
     */
    public void setOnLoadMoreComplete() {
        mRefreshView.finishLoadMore();
    }

    /**
     * 结束刷新和加载
     */
    public void finishLoadOrRefresh() {
        if (isRefreshing()) {
            setOnRefreshComplete();
        } else {
            setOnLoadMoreComplete();
        }
    }

    /**
     * 无数据时，关闭上拉加载
     */
    public void setOnLoadNoData() {
        setOnLoadNoData(false);
    }

    public void setOnLoadNoData(boolean notify) {
        if (notify) {
            if (getPageNo() > initalPageNo) {
                ToastUtils.showShort("没有更多数据啦");
            }
        }
        mRefreshView.setNoMoreData(true);
    }

    public void setRefreshListener(OnRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }

    public void setRecyclerViewPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        paddingLeft = SizeUtils.dp2px(paddingLeft);
        paddingTop = SizeUtils.dp2px(paddingTop);
        paddingRight = SizeUtils.dp2px(paddingRight);
        paddingBottom = SizeUtils.dp2px(paddingBottom);
        mRecyclerView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public interface OnRefreshListener {
        public void onRefresh(final RefreshViewHelper helper, final int pageNo, final int pageSize);
    }

}
