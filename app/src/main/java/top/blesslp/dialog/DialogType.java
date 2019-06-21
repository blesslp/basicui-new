package top.blesslp.dialog;

import android.support.annotation.IntDef;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static top.blesslp.dialog.DialogType.DIALOG_TYPE_FAIL;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_INFO;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_LOADING;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_NOTHING;
import static top.blesslp.dialog.DialogType.DIALOG_TYPE_SUCCESS;

@IntDef({DIALOG_TYPE_LOADING, DIALOG_TYPE_INFO, DIALOG_TYPE_SUCCESS, DIALOG_TYPE_FAIL, DIALOG_TYPE_NOTHING})
@Target(
        ElementType.PARAMETER
) //表示注解作用范围，参数注解，成员注解，方法注解
@Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在 .class 文件中
public @interface DialogType {
    final int DIALOG_TYPE_LOADING = QMUITipDialog.Builder.ICON_TYPE_LOADING;
    final int DIALOG_TYPE_INFO = QMUITipDialog.Builder.ICON_TYPE_INFO;
    final int DIALOG_TYPE_SUCCESS = QMUITipDialog.Builder.ICON_TYPE_SUCCESS;
    final int DIALOG_TYPE_FAIL = QMUITipDialog.Builder.ICON_TYPE_FAIL;
    final int DIALOG_TYPE_NOTHING = QMUITipDialog.Builder.ICON_TYPE_NOTHING;

}
