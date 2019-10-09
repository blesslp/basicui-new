package top.blesslp.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BindingActivity<T extends ViewDataBinding> extends BasicActivity implements DataBindingable {

    private T mBinder;

    @Override
    protected void setContentView4Basic(int layoutId) {
        setContentView4Binding(layoutId);
    }

    private void setContentView4Binding(int layoutId) {
        this.mBinder = DataBindingUtil.inflate(getLayoutInflater(), layoutId, null, false);
        setContentView(this.mBinder.getRoot());
    }

    @Override
    public T getBinder() {
        return mBinder;
    }
}
