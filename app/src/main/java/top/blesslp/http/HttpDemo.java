package top.blesslp.http;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.blesslp.BuildConfig;

public class HttpDemo {
    public void init() {
        RetrofitWrapper instance = RetrofitWrapper.getInstance();

        instance.setOkClient(new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BODY)
                        .log(Platform.WARN)
                        .tag("TAG")
                        .addHeader("version", BuildConfig.VERSION_NAME)
                        .addQueryParam("query", "0")
                        .build())
                .build());

        instance.setRetrofit(new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build());

        instance.init();
    }
}
