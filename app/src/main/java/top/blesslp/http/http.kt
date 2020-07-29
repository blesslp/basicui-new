package top.blesslp.http

import com.blankj.utilcode.util.GsonUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


const val CONTENT_TYPE_JSON = "application/json; charset=utf-8"
const val MULTIPART_TYPE = "multipart/form-data"
const val TEXT_MEDIA: String = "text/plain"

inline fun String.toBody(type: String): RequestBody {
    return this.toRequestBody(type.toMediaType())
}

val EMPTY_JSONBODY = "".toJsonBody()

inline fun String.toJsonBody(): RequestBody = toBody(CONTENT_TYPE_JSON)

inline fun Any.toJsonBody() = GsonUtils.toJson(this).toJsonBody()
inline fun Any.toJsonStr() = GsonUtils.toJson(this)
inline fun Any.toText() = this.toString().toBody(TEXT_MEDIA)
inline fun File.toPart(key: String): MultipartBody.Part {
    val asRequestBody = this.asRequestBody(MULTIPART_TYPE.toMediaType())
    return MultipartBody.Part.createFormData(key, this.name, asRequestBody)
}

inline fun <K, V> maps(block: HashMap<K, V>.() -> Unit): HashMap<K, V> {
    return hashMapOf<K, V>().apply(block)
}
