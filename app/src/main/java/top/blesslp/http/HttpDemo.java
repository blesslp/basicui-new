package top.blesslp.http;

import http.logging.HttpLogger;
import http.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpDemo {
    public void init() {
        RetrofitWrapper instance = RetrofitWrapper.getInstance();
        instance.setOkClient(new OkHttpClient.Builder()
               .addInterceptor(new HttpLoggingInterceptor(new HttpLogger("TAG")))
                .build());

        instance.setRetrofit(new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build());

        instance.init();
    }
}
