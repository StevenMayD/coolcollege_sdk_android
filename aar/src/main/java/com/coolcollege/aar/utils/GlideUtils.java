package com.coolcollege.aar.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.coolcollege.aar.R.mipmap;
import com.coolcollege.aar.application.Options;


/**
 * 项目名称：
 * 类描述：
 * 作者：
 * 日期：   2016/9/2 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public class GlideUtils {
    static RequestOptions requestOptions;

    public GlideUtils() {
    }

    public static void displayNormalPic(String url, ImageView view) {
        Glide.with(Options.get())
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .into(view);
    }

    public static void justGetBitmapFromPic(String url, SimpleTarget<Bitmap> target) {
        Glide.with(Options.get())
                .asBitmap()
                .load(url)
                .into(target);
    }

    public static void justGetDrawableFromPic(String url, SimpleTarget<Drawable> target) {
        Glide.with(Options.get())
                .asDrawable()
                .load(url)
                .into(target);
    }

    public static void cleanImageMemoryCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(Options.get()).clearMemory();
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    static {
        requestOptions = (new RequestOptions()).placeholder(mipmap.k_ic_placeholder).error(mipmap.k_ic_placeholder).centerCrop();
    }
}
