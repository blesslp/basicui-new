package top.blesslp.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Administrator on 2017/6/19 0019.
 * 类描述：
 */

public class ExBindingViewHolder extends BaseViewHolder {
    private SparseArray<View> views = new SparseArray<>(5);

    private ViewDataBinding viewDataBinding;

    public ExBindingViewHolder(View itemView) {
        super(itemView);
        try {
            viewDataBinding = DataBindingUtil.bind(itemView);
        } catch (Exception e) {
        } finally {
        }
    }

    public ExBindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.viewDataBinding = binding;
    }

    public static ExBindingViewHolder get(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding inflate = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        if (inflate == null) {
            return new ExBindingViewHolder(inflateView(context, layoutId, parent));
        }
        return new ExBindingViewHolder(inflate);
    }

    public static View inflateView(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public <T extends ViewDataBinding> T getBinder() {
        return (T) this.viewDataBinding;
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
