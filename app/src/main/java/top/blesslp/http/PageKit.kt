package top.blesslp.http

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import top.blesslp.ui.BaseViewModel
import top.blesslp.ui.BasicActivity
import top.blesslp.ui.BasicFragment
import top.blesslp.ui.BasicView
import java.lang.ref.WeakReference

inline fun BasicActivity.start(cls: Class<out Activity>, requestCode: Int = -1) {
    start(Intent(this, cls), requestCode)
}


inline fun BasicActivity.start(cls: Class<out Activity>, data: Bundle, requestCode: Int = -1) {
    val intent = Intent(this, cls)
    intent.putExtras(data)
    start(intent, requestCode)
}


inline fun BasicActivity.start(intent: Intent, requestCode: Int = -1) {
    startActivityForResult(intent, requestCode)
}

inline fun BasicActivity.intentOf(cls: Class<out Activity>) = Intent(this, cls)


inline fun BasicActivity.startAndFinish(cls: Class<out Activity>, requestCode: Int = -1) {
    startAndFinish(Intent(this, cls), requestCode)
}

/**
 * 断言
 */
inline fun ASSERT(isTrue: Boolean, notTrueErrorMessage: String) {
    if (!isTrue) {
        throw RuntimeException(notTrueErrorMessage)
    }
}

inline fun ASSERT(isTrue: Boolean) = ASSERT(isTrue, "Assert Is False")


inline fun BasicActivity.startAndFinish(
        cls: Class<out Activity>,
        data: Bundle,
        requestCode: Int = -1
) {
    val intent = Intent(this, cls)
    intent.putExtras(data)
    startAndFinish(intent, requestCode)
}

inline fun BasicActivity.startAndFinish(intent: Intent, requestCode: Int = -1) {
    startActivityForResult(intent, requestCode)
    finish()
}

inline fun <T : BaseViewModel> BasicActivity.getVM(cls: Class<T>): T {
    val get = ViewModelProvider(this).get(cls)
    get.setView(WeakReference(this))
    return get
}

inline fun <T : BaseViewModel> BasicFragment.getVM(cls: Class<T>): T {
    val get = ViewModelProvider(this).get(cls)
    get.setView(WeakReference(this))
    return get
}

inline fun <T : BaseViewModel> BasicView.getVM(cls: Class<T>): T {
    val target = getTarget() as Any

    ASSERT(target != null, "Class : ${this.javaClass.canonicalName} target为null")

    if (target is BasicActivity) {
        return target.getVM(cls)
    } else if (target is BasicFragment) {
        return target.getVM(cls)
    }
    throw RuntimeException("Class : ${this.javaClass.canonicalName} target既不是BasicActivity也不是BasicFragment")
}

suspend fun <T> ui(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.Main, block)
suspend fun <T> io(block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)


fun go(block: suspend CoroutineScope.() -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        block()
    }
}

fun delayUI(delayTime: Long = 500, block: suspend CoroutineScope.() -> Unit) = go {
    delay(delayTime)
    ui {
        block()
    }
}

fun <T> BasicView.observe(data: LiveData<T>, call: (T) -> Unit) {
    val target = getTarget() as Any

    ASSERT(target != null, "Class : ${this.javaClass.canonicalName} target为null")

    if (target is BasicActivity) {
        return target.observe(data, call)
    } else if (target is BasicFragment) {
        return target.observe(data, call)
    }
    throw RuntimeException("Class : ${this.javaClass.canonicalName} target既不是BasicActivity也不是BasicFragment")
}


fun <T> BasicActivity.observe(data: LiveData<T>, call: (T) -> Unit) {
    data.observe(this, Observer {
        call(it)
    })
}

fun <T> BasicFragment.observe(data: LiveData<T>, call: (T) -> Unit) {
    data.observe(this, Observer {
        call(it)
    })
}

inline fun Context.color(@ColorRes resColor: Int) = ContextCompat.getColor(this, resColor)

inline fun Context.colorStateList(@ColorRes resColor: Int) =
        ContextCompat.getColorStateList(this, resColor)

inline fun Context.drawable(@DrawableRes resDrawable: Int) =
        ContextCompat.getDrawable(this, resDrawable)