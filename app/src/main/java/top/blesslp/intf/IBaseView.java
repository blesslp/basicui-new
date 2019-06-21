package top.blesslp.intf;

/**
 * Administrator on 2017/6/30 0030.
 * 类描述：
 */

public interface IBaseView {

    void showLoading(String message, boolean canCancel);

    void cancelLoading();

    void showToastLong(String message);

    void showToastShort(String message);

    void showTips(String message);

    void showSuccess(String message);

    void showFailed(String message);

    public void showFailed(String message, Runnable task);

    public void showSuccess(String message, Runnable task);

    public void showTips(String message, Runnable task);

    void loginOut();

    void serviceError();

    void serviceNoData();

    void addPresenter(BasePresenter presenter);

}
