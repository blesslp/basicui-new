package top.blesslp.intf;

import cn.blesslp.plugins.injection_tools.PastryDelegate;

/**
 * Administrator on 2017/6/30 0030.
 * 类描述：
 */

public class BasePresenter<View extends IBaseView> {

    private View mView;
    private PastryDelegate mPastryDelegate;

    public BasePresenter(View view) {
        this.mView = view;
        this.mView.addPresenter(this);
        this.mPastryDelegate = PastryDelegate.create(this);
        try {
            this.mPastryDelegate.autoInject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View getView() {
        return mView;
    }

    public void onDestory() {
        this.mPastryDelegate.cancelAll();
    }
}
