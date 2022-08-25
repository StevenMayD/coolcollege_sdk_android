package com.coolcollege.aar.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.coolcollege.aar.R;

/**
 * Created by Evan_for on 2020/7/1
 */
public abstract class BaseDialog extends AlertDialog {

    private int default_width;
    protected static final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;
    protected static final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;

    private View view;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.k_appBaseDialogStyle);
    }

    protected BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        default_width = context.getResources().getDimensionPixelOffset(R.dimen.k_dp_260);
        initSelf();
        initView();
        initListener();
    }

    protected void initSelf() {
        view = View.inflate(getContext(), initLayout(), null);
        setView(view);
    }

    public View getBaseView(){
        return view;
    }

    protected abstract @LayoutRes int initLayout();

    protected abstract void initView();

    protected abstract void initListener();

    protected int defaultWidth() {
        return default_width;
    }

    protected int defaultGravity() {
        return Gravity.CENTER;
    }

    protected int defaultHeight() {
        return WRAP_CONTENT;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    protected Drawable windowBgDrawable() {
        return getContext().getResources().getDrawable(R.drawable.k_diaglog_bg);
    }

    protected int windowAnim() {
        return -1;
    }

    @Override
    public void show() {
        super.show();
        resetShow();
    }


    protected void resetShow() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = defaultGravity();
            layoutParams.width = defaultWidth();
            layoutParams.height = defaultHeight();
            if (windowAnim() != -1) {
                layoutParams.windowAnimations = R.style.k_dialogBtm2TopAnim;
            }
            window.setBackgroundDrawable(windowBgDrawable());

            window.setAttributes(layoutParams);
        }
    }

}
