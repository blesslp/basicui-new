package top.blesslp.com.sample.presenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.blesslp.com.sample.api.DemoApi;
import top.blesslp.http.InjectApi;
import top.blesslp.intf.BasePresenter;
import top.blesslp.intf.IBaseView;

public interface DemoContract {

    public interface IDemoView extends IBaseView {
        void onBannerLoaded(List<Banner> banners);
    }


    public class DemoPresenter extends BasePresenter<IDemoView> {
        @InjectApi private DemoApi api;
        public DemoPresenter(IDemoView view) {
            super(view);
        }

        public void getBannerList(){
            final IDemoView view = getView();
            view.showLoading("正在加载...",false);
            api.getBannerList().enqueue(new Callback<NetResult<List<Banner>>>() {
                @Override
                public void onResponse(Call<NetResult<List<Banner>>> call, Response<NetResult<List<Banner>>> response) {
                    NetResult<List<Banner>> body = response.body();
                    view.onBannerLoaded(body.getData());
                }

                @Override
                public void onFailure(Call<NetResult<List<Banner>>> call, Throwable t) {

                }
            });

        }
    }
}
