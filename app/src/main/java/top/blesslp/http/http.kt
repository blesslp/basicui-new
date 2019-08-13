package com.chaychan.news.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

val FILE_MEDIA: MediaType = "multipart/form-data".toMediaType()
val TEXT_MEDIA: MediaType = "text/plain".toMediaType()
inline fun File.partOf() = MultipartBody.Part.createFormData("file", name, asRequestBody(FILE_MEDIA))

inline fun String.partOf() = toRequestBody(TEXT_MEDIA)

inline fun <T> wrap(block: () -> T): T? {
    try {
        return block.invoke()
    } catch (e: Exception) {
        return null
    }
}
