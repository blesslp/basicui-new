package top.blesslp.ui;

public interface UIEventFun {

    /**
     * 初始化Presenter
     */
    void initPresenter();

    /**
     * 初始化事件
     */
    void initListeners();

    /**
     * 初始化RecyclerView相关
     */
    void initRecyclerView();

    /**
     * 初始化数据
     */
    void initData();

}
