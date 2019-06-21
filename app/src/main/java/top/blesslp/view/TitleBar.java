package top.blesslp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import top.blesslp.R;
import top.blesslp.ui.BasicActivity;
import top.blesslp.ui.BasicFragmentActivity;

public class TitleBar extends QMUITopBarLayout {

    private Drawable mTopBarBgWithSeparatorDrawableCache;
    private QMUIAlphaImageButton leftButton;
    private int titleColor;
    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    private void initAttrs(final Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        if(array == null) return;

        //标题文件
        String titleText = array.getString(R.styleable.TitleBar_titleBar_title);
        titleColor = array.getColor(R.styleable.TitleBar_titleBar_titleColor,Color.WHITE);
        //返回键图标
        int leftButtonIcon = array.getResourceId(R.styleable.TitleBar_titlebar_leftButtonIcon,-1);
        //返回键行为
        boolean leftButtonAsGoback = array.getBoolean(R.styleable.TitleBar_titlebar_leftAsGoBack, false);

        //背景颜色
        int backgroundColor = array.getColor(R.styleable.TitleBar_titlebar_backgroundColor, Color.WHITE);
        //背景颜色透明度
        int backgroundColorAplha = (backgroundColor >> 24) & 0xFF;
        //标题栏分割线
        int backgroundDivideColor = array.getColor(R.styleable.TitleBar_titlebar_backgroundDivideColor, 0);

        array.recycle();

        //设置背景和分割线
        setBackgroundDividerEnabled2(backgroundDivideColor != 0, backgroundDivideColor, backgroundColor, 1);

        //设置标题,颜色, 大小
        if (!TextUtils.isEmpty(titleText)) {
           setTitle(titleText);
        }
        setTitleGravity(Gravity.CENTER);

        if (leftButtonAsGoback) {
            if(leftButtonIcon == -1) {
                leftButton = addLeftBackImageButton();
            }else{
                leftButton = addLeftImageButton(leftButtonIcon, QMUIViewHelper.generateViewId());
            }
            leftButton.setChangeAlphaWhenPress(true);
            leftButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof BasicActivity) {
                        ((BasicActivity)context).finish();
                    } else if (context instanceof BasicFragmentActivity) {
                        ((BasicFragmentActivity)context).popBackStack();
                    }
                }
            });
        }else if(leftButtonIcon != -1){
            leftButton = addLeftImageButton(leftButtonIcon, QMUIViewHelper.generateViewId());
            leftButton.setChangeAlphaWhenPress(true);
        }
    }

    public QMUIAlphaImageButton getLeftButton() {
        return leftButton;
    }

    @Override
    public TextView setTitle(String title) {
        TextView textView = super.setTitle(title);
        textView.setTextColor(titleColor);
        return textView;
    }

    /**
     * 设置是否要 Topbar 底部的分割线
     *
     * @param enabled true 为显示底部分割线，false 则不显示
     */
    @Override
    public void setBackgroundDividerEnabled(boolean enabled) {
    }


    /**
     * 设置是否要 Topbar 底部的分割线
     *
     * @param enabled true 为显示底部分割线，false 则不显示
     */
    public void setBackgroundDividerEnabled2(boolean enabled,int mTopBarSeparatorColor,int mTopBarBgColor,int mTopBarSeparatorHeight) {
        if (enabled) {
            if (mTopBarBgWithSeparatorDrawableCache == null) {
                mTopBarBgWithSeparatorDrawableCache = QMUIDrawableHelper.
                        createItemSeparatorBg(mTopBarSeparatorColor, mTopBarBgColor, mTopBarSeparatorHeight, false);
            }
            QMUIViewHelper.setBackgroundKeepingPadding(this, mTopBarBgWithSeparatorDrawableCache);
        } else {
            QMUIViewHelper.setBackgroundColorKeepPadding(this, mTopBarBgColor);
        }
    }
}
