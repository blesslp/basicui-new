package top.blesslp.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.lang.ref.WeakReference;

import top.blesslp.dialog.DialogType;
import top.blesslp.dialog.WeakTasker;

import static top.blesslp.dialog.DialogType.DIALOG_TYPE_FAIL;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_INFO;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_LOADING;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_SUCCESS;

public final class DialogProxy implements LifecycleObserver {

    private Handler mHandler;
    private Context context;
    private Lifecycle mLifecycle;
    private Handler message = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0xff) {
                if (msg.obj != null) {
                    ((Runnable) msg.obj).run();
                }
            }
        }
    };
    private WeakReference<QMUITipDialog> dialogSoftRefrence = null;

    public DialogProxy(Handler mUIHandler, Context context, LifecycleOwner lifecycleOwner) {
        this.mHandler = mUIHandler;
        this.context = context;
        this.mLifecycle = lifecycleOwner.getLifecycle();
        this.mLifecycle.addObserver(this);
    }

    private void showDialog(@DialogType int type, String msg, long delay, boolean cancelable, Runnable cancleTask) {
        //如果有, 则先取消掉
        cancelDialog();
        //没有则新创建一个, 并加入到引用中
        final QMUITipDialog instance = new QMUITipDialog.Builder(context)
                .setIconType(type)
                .setTipWord(msg)
                .create();
        instance.setCancelMessage(Message.obtain(message, 0xff, cancleTask));
        instance.setCancelable(cancelable);
        instance.setCanceledOnTouchOutside(cancelable);
        //加入到弱引用内
        dialogSoftRefrence = new WeakReference(instance);
        instance.show();
        if (delay <= 0) return;
        mHandler.postDelayed(new WeakTasker(dialogSoftRefrence), delay);
    }


    void cancelDialog() {
        //如果已有提示框,则取消掉
        WeakReference<QMUITipDialog> _temp = this.dialogSoftRefrence;
        if (_temp != null) {
            QMUITipDialog qmuiTipDialog = _temp.get();
            if (qmuiTipDialog != null) {
                qmuiTipDialog.setCancelMessage(null);
                qmuiTipDialog.cancel();
            }
            _temp.clear();
            _temp = null;
            this.dialogSoftRefrence = null;
        }
    }


    void showSuccess(String msg) {
        this.showSuccess(msg, null);
    }

    void showSuccess(String msg, Runnable task) {
        this.showDialog(DIALOG_TYPE_SUCCESS, msg, 1000, task == null, task);
    }

    void showFailed(String msg) {
        this.showFailed(msg, null);
    }

    void showFailed(String msg, Runnable task) {
        this.showDialog(DIALOG_TYPE_FAIL, msg, 1000, task == null, task);
    }

    void showTips(String msg) {
        this.showTips(msg, null);
    }

    void showTips(String msg, Runnable task) {
        this.showDialog(DIALOG_TYPE_INFO, msg, 1000, task == null, task);
    }

    void showLoading(String message, boolean canCancel) {
        showDialog(DIALOG_TYPE_LOADING, message, 0, canCancel, null);
    }

    void showShort(String message) {
        ToastUtils.showShort(message);
    }

    void showLong(String message) {
        ToastUtils.showLong(message);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(@NonNull LifecycleOwner owner) {
        cancelDialog();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(@NonNull LifecycleOwner owner) {
        this.mLifecycle.removeObserver(this);
    }


}
