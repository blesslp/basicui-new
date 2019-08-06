package top.blesslp.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private String tag;
    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private StringBuilder mMessage = new StringBuilder();
    public HttpLogger(String tag) {
        this.tag = tag;
    }

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0);
            mMessage.append(message.replaceAll("--> ", "").concat("\n"));
            return;
        }

        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = GSON.toJson(new JsonParser().parse(message).getAsJsonObject());
        }

        if (message.startsWith("--> END")) {
            Log.w(tag,mMessage.toString());
            mMessage.setLength(0);
            return;
        }

        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Log.w(tag,mMessage.toString());
            return;
        }

        mMessage.append(message.concat("\n"));

    }
}
