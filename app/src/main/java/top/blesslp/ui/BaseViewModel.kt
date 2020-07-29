package top.blesslp.ui

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import top.blesslp.http.io
import top.blesslp.http.ui
import top.blesslp.intf.IBaseView
import java.lang.ref.WeakReference

open class BaseViewModel : ViewModel() {

    private lateinit var __view: WeakReference<IBaseView>

    fun setView(view: WeakReference<IBaseView>) {
        this.__view = view
    }

    override fun onCleared() {
        __view.clear()
    }

    protected val view: IBaseView?
        get() = __view.get()


    protected fun go(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }

    protected fun <T> async(block: suspend CoroutineScope.() -> T) = viewModelScope.async {
        block()
    }

    protected suspend fun <T> request(loading: String = "", block: suspend () -> T) = io {
        val hasTips = !TextUtils.isEmpty(loading)
        if (hasTips) {
            ui {
                view?.showLoading(loading, true)
            }
        }
        try {
            val retVal = block()
            if (hasTips) {
                ui {
                    view?.cancelLoading()
                }
            }
            return@io retVal
        } catch (e: Exception) {
            if (hasTips) {
                ui {
                    view?.cancelLoading()
                }
            }
            return@io null
        }
    }
}