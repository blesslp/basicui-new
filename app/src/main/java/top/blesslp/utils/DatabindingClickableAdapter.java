package top.blesslp.utils;


import androidx.annotation.LayoutRes;

/**
 * Administrator on 2017/7/12 0012.
 * 类描述：
 */

public class DatabindingClickableAdapter<T> extends DatabindingAdapter<T> {
    private int[] viewIds;

    public DatabindingClickableAdapter(@LayoutRes int layoutResId, int br, int... viewIds) {
        super(layoutResId,br);
        this.viewIds = viewIds;
    }

    @Override
    protected void convert(ExBindingViewHolder helper, T item) {
        if (viewIds != null && viewIds.length > 0) {
            for (int id : viewIds) {
                helper.addOnClickListener(id);
            }
        }
        super.convert(helper,item);
    }
}
