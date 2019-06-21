package top.blesslp.ui;

import android.databinding.ViewDataBinding;

/**
 * Administrator on 2017/6/20 0020.
 * 类描述：
 */

public interface DataBindingable {

    public <T extends ViewDataBinding> T getBinder();
}
