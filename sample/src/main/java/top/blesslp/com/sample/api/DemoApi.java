package top.blesslp.com.sample.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import top.blesslp.com.sample.presenter.Banner;
import top.blesslp.com.sample.presenter.NetResult;

public interface DemoApi {

    @GET("banner/json")
    Call<NetResult<List<Banner>>> getBannerList();

}
