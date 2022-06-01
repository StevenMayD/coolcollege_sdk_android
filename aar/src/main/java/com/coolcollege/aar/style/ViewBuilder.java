package com.coolcollege.aar.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/TitleBar
 *    time   : 2018/08/20
 *    desc   : 标题栏子View构建器
 */
final class ViewBuilder {

    static LinearLayout newMainLayout(Context context) {
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return mainLayout;
    }

    static LinearLayout newTitleLayout(Context context) {
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return mainLayout;
    }

    static View newLineView(Context context) {
        View lineView = new View(context);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        return lineView;
    }

    static TextView newLeftView(Context context) {
        TextView leftView = new TextView(context);
        leftView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        leftView.setGravity(Gravity.CENTER_VERTICAL);
        leftView.setFocusable(true);
        leftView.setClickable(true);
        leftView.setSingleLine();
        leftView.setEllipsize(TextUtils.TruncateAt.END);
        return leftView;
    }

    static TextView newTitleView(Context context) {
        TextView titleView = new TextView(context);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        titleView.setGravity(Gravity.CENTER);
        titleView.setFocusable(true);
        titleView.setSingleLine();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE_1_1) {
            // 给标题设置跑马灯效果（仅在标题过长的时候才会显示）
            titleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            // 设置跑马灯之后需要设置选中才能有效果
            titleView.setSelected(true);
        } else {
            titleView.setEllipsize(TextUtils.TruncateAt.END);
        }
        return titleView;
    }

    static TextView newRightView(Context context) {
        TextView rightView = new TextView(context);
        rightView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rightView.setGravity(Gravity.CENTER_VERTICAL);
        rightView.setFocusable(true);
        rightView.setClickable(true);
        rightView.setSingleLine();
        rightView.setEllipsize(TextUtils.TruncateAt.END);
        return rightView;
    }

    static View newStatusBarView(Context context,int height){
        View lineView = new View(context);
        lineView.setBackgroundColor(Color.WHITE);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height ));
        return lineView;
    }

    /**
     * 检查 TextView 的内容是否为空
     */
    static boolean hasTextViewContent(TextView view) {
        if (!"".equals(view.getText().toString())) {
            return true;
        }
        Drawable[] drawables = view.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 Drawable 对象
     */
    static Drawable getDrawable(Context context, @DrawableRes int id)  {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * 设置 View 的背景
     */
    static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        }else {
            view.setBackgroundDrawable(background);
        }
    }
}