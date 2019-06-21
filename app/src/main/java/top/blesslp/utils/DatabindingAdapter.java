package top.blesslp.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Administrator on 2017/7/12 0012.
 * 类描述：
 */

public class DatabindingAdapter<T> extends BaseQuickAdapter<T, ExBindingViewHolder> {

    private int br;

    public DatabindingAdapter(@LayoutRes int layoutResId, int br) {
        super(layoutResId);
        this.br = br;
    }

    @Override
    protected void convert(ExBindingViewHolder helper, T item) {
        ViewDataBinding bind = DataBindingUtil.bind(helper.itemView);
        bind.setVariable(br, item);
        bind.executePendingBindings();
    }
}
