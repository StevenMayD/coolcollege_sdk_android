package com.coolcollege.aar.utils;

import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.callback.ShareMenuListener;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.model.ShareMenuModel;

import java.util.HashMap;

import okhttp3.internal.platform.Platform;


/**
 * Created by Evan_for on 2020/8/18
 */
public class ShareUtils {
    private static ShareMenuListener mShareMenuListener;


    public static void appShare(ShareParams params, ShareMenuListener shareMenuListener) {
        mShareMenuListener = shareMenuListener;
        int type = params.type;
    }
}
