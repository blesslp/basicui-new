package top.blesslp.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Administrator on 2017/6/19 0019.
 * 类描述：
 */

public class ExViewHolder extends BaseViewHolder {
    private SparseArray<View> views = new SparseArray<>(5);

    public ExViewHolder(View itemView) {
        super(itemView);

    }

    public static ExViewHolder get(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        return new ExViewHolder(inflateView(context, layoutId, parent));
    }

    public static View inflateView(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public void setText(@IdRes int textViewId, @NonNull String text) {
        TextView textView = getView(textViewId);
        if (textView == null) return;
        textView.setText(text);
    }

    public void setText(@IdRes int textViewId, @NonNull SpannableStringBuilder text) {
        TextView textView = getView(textViewId);
        if (textView == null) return;
        textView.setText(text);
    }

    public void setSrc(@IdRes int imageViewId, @DrawableRes int icon) {
        ImageView imageView = getView(imageViewId);
        if (imageView == null) return;
        imageView.setImageResource(icon);
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }

    public <T extends View> T getView(@IdRes int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }
}
