package http.logging

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class HttpLogger(private val tag: String) : HttpLoggingInterceptor.Logger {
    private val GSON = GsonBuilder().setPrettyPrinting().create()
    private val mMessage = StringBuffer()

    init {
        Logger.clearLogAdapters()
        val strategy = PrettyFormatStrategy.newBuilder()
                .tag(tag)
                .methodOffset(0)
                .methodCount(0)
                .showThreadInfo(false)
                .logStrategy(LogcatLogStrategy())
                .build()
        val logAdapter = AndroidLogAdapter(strategy)
        Logger.addLogAdapter(logAdapter)
    }

    override fun log(message: String) {
        var message = message
        // 请求或者响应开始
        if (message.startsWith("--> POST") || message.startsWith("--> GET")) {
            mMessage.setLength(0)
            mMessage.append(message.replace("--> ".toRegex(), "") + "\n")
            return
        }

        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if (message.startsWith("{") && message.endsWith("}") || message.startsWith("[") && message.endsWith("]")) {
            var parse = JsonParser().parse(message)
            if(parse.isJsonObject) {
                message = GSON.toJson(parse.asJsonObject)
            }else{
                message = GSON.toJson(parse.asJsonArray)
            }
        }

        if (message.startsWith("--> END")) {
            Logger.w(mMessage.toString())
            mMessage.setLength(0)
            return
        }

        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Logger.w(mMessage.toString())
            return
        }

        mMessage.append(message + "\n")

    }
}
