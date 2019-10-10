package top.blesslp.http;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetrofitWrapper {

    private static RetrofitWrapper _INSTANCE;
    private OkHttpClient client;
    private Retrofit retrofit;
    private boolean isInit =false;

    public static RetrofitWrapper getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new RetrofitWrapper();
        }
        return _INSTANCE;
    }

    public void setOkClient(OkHttpClient client) {
        this.client = client;
    }

    public void init() {
        this.isInit = true;
        this.retrofit = retrofit.newBuilder().client(getClient()).build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public final void injectApi(Object me) throws Exception {
        Class<?> meClass = me.getClass();
        Field[] declaredFields = meClass.getDeclaredFields();
        Field[] var4 = declaredFields;
        int var5 = declaredFields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field declaredField = var4[var6];
            boolean hasAnnotation = declaredField.isAnnotationPresent(InjectApi.class);
            if (hasAnnotation) {
                handleInject(me, declaredField);
            }
        }

    }

    private void handleInject(Object me, Field declaredField) throws Exception {
        InjectApi injectApi = (InjectApi)declaredField.getAnnotation(InjectApi.class);
        Class<?> apiInterface = declaredField.getType();
        if (!apiInterface.isInterface()) {
            throw new IllegalArgumentException(String.format("@InjectApi期望一个接口类型,但是接受的却是:%s", apiInterface.getName()));
        } else {
            Object api = retrofit.create(apiInterface);
            declaredField.setAccessible(true);
            declaredField.set(me, api);
        }
    }

}
