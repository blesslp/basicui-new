package top.blesslp.intf;

import top.blesslp.http.RetrofitWrapper;

/**
 * Administrator on 2017/6/30 0030.
 * 类描述：
 */

public class BasePresenter<View extends IBaseView> {

    private View mView;

    public BasePresenter(View view) {
        this.mView = view;
        this.mView.addPresenter(this);
        try {
            RetrofitWrapper.getInstance().injectApi(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View getView() {
        return mView;
    }

    public void onDestory() {

    }
}
