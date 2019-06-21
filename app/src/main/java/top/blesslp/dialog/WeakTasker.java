package top.blesslp.dialog;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.lang.ref.WeakReference;

public final class WeakTasker implements Runnable {

    private WeakReference<QMUITipDialog> ref;

    public WeakTasker(WeakReference<QMUITipDialog> ref) {
        this.ref = ref;
    }

    @Override
    public void run() {
        if (ref != null) {
            QMUITipDialog dialog = ref.get();
            if (dialog != null) {
                if (dialog.getWindow() != null) {
                    dialog.cancel();
                }
            }
            ref.clear();
            ref = null;
        }
    }
}