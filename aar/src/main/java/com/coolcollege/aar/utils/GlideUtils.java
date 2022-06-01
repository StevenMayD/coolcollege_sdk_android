package com.coolcollege.aar.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.coolcollege.aar.R;
import com.coolcollege.aar.application.Options;


/**
 * 项目名称：
 * 类描述：
 * 作者：
 * 日期：   2016/9/2 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public class GlideUtils {

    static RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.mipmap.ic_placeholder)
            .error(R.mipmap.ic_placeholder)
            .centerCrop();

//    public static void displayImageAsBitmap(String url, ImageView view, int placeholder) {
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(url)
//                .placeholder(placeholder)
//                .error(placeholder)
//                .centerCrop()
//                .into(view);
//    }
//
//
//    public static void displayImage(String url, ImageView view, int placeholder) {
//
//        Glide.with(Options.get())
//                .asGif()
//                .load(url)
//                .placeholder(placeholder)
//                .error(placeholder)
//                .centerCrop()
//                .into(view);
//    }
//
//    public static void displayImage(int picId, ImageView view) {
//
//        Glide.with(Options.get())
//                .asGif()
//                .load(picId)
//                .centerCrop()
//                .into(view);
//    }
//
//    public static void displayPic(String url, ImageView view) {
//
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(url)
//                .centerCrop()
//                .into(view);
//    }
//
//    public static void displayImage(int picId, ImageView view, int placeholder) {
//
//        Glide.with(Options.get())
//                .asGif()
//                .load(picId)
//                .placeholder(placeholder)
//                .error(placeholder)
//                .centerCrop()
//                .into(view);
//    }
//
//    public static void displayAvatarPicture(String url, ImageView view) {
//
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(url)
//                .placeholder(R.mipmap.ic_avatar_placeholder)
//                .error(R.mipmap.ic_avatar_placeholder)
//                .transform(new CircleCrop())
//                .centerCrop()
//                .into(view);
//    }
//
//    public static void displayCirclePicture(String url, ImageView view) {
//
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(url)
//                .placeholder(R.mipmap.ic_avatar_placeholder)
//                .error(R.mipmap.ic_avatar_placeholder)
//                .transform(new CircleCrop())
//                .centerCrop()
//                .into(view);
//    }

    public static void displayNormalPic(String url, ImageView view) {
        Glide.with(Options.get())
                .asBitmap()
                .load(url)
                .apply(requestOptions)
//                .placeholder(R.mipmap.ic_placeholder)
//                .error(R.mipmap.ic_placeholder)
//                .centerCrop()
                .into(view);
    }

//    public static void displayRoundPic(String url, ImageView view) {
//        RoundedCorners roundedCorners = new RoundedCorners(Options.get().getResources().getDimensionPixelOffset(R.dimen.dp_5));
//        RequestOptions transform = new RequestOptions().transform(new CenterCrop(), roundedCorners);
//
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(url)
//                .apply(transform)
//                .placeholder(R.mipmap.ic_placeholder)
//                .error(R.mipmap.ic_placeholder)
//                .into(view);
//    }
//
//    public static void displayRoundPic(int resId, ImageView view) {
//        RoundedCorners roundedCorners = new RoundedCorners(Options.get().getResources().getDimensionPixelOffset(R.dimen.dp_5));
//        RequestOptions transform = new RequestOptions().transform(new CenterCrop(), roundedCorners);
//
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(resId)
//                .apply(transform)
//                .placeholder(R.mipmap.ic_placeholder)
//                .error(R.mipmap.ic_placeholder)
//                .into(view);
//    }
//
//    public static void displayRoundPic(String url, int radius, ImageView view) {
//        RoundedCorners roundedCorners = new RoundedCorners(radius);
//        RequestOptions transform = new RequestOptions().transform(new CenterCrop(), roundedCorners);
//
//        Glide.with(Options.get())
//                .asBitmap()
//                .load(url)
//                .apply(transform)
//                .placeholder(R.mipmap.ic_placeholder)
//                .error(R.mipmap.ic_placeholder)
//                .into(view);
//    }

    public static void justGetBitmapFromPic(String url, SimpleTarget<Bitmap> target) {
        Glide.with(Options.get())
                .asBitmap()
                .load(url)
                .into(target);
    }

    public static void justGetDrawableFromPic(String url, SimpleTarget<Drawable> target){
        Glide.with(Options.get())
                .asDrawable()
                .load(url)
                .into(target);
    }

    public static void cleanImageMemoryCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(Options.get()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
