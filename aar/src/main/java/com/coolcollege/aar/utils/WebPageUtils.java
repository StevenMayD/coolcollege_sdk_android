package com.coolcollege.kxy.utils;

import android.content.Intent;

import com.coolcollege.kxy.application.MyApplication;
import com.coolcollege.kxy.global.GlobalKey;
import com.coolcollege.kxy.view.activity.OtherWebActivity;

/**
 * Created by Evan_for on 2020/8/5
 */
public class WebPageUtils {

    public static void startWebPage(String host, String url,String title, int titleBarLeftPic) {
        Intent intent = new Intent(MyApplication.get(), OtherWebActivity.class);
        intent.putExtra(GlobalKey.OTHER_WEB_PATH_KEY, url);
        intent.putExtra(GlobalKey.OTHER_WEB_HOST_KEY, host);
        intent.putExtra(GlobalKey.OTHER_WEB_TITLE_KEY, title);
        intent.putExtra(GlobalKey.OTHER_WEB_TITLE_BAR_LEFT_PIC_KEY, titleBarLeftPic);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.get().startActivity(intent);
    }

    public static void startWebPage(String host, String path,String title) {
        Intent intent = new Intent(MyApplication.get(), OtherWebActivity.class);
        intent.putExtra(GlobalKey.OTHER_WEB_PATH_KEY, path);
        intent.putExtra(GlobalKey.OTHER_WEB_HOST_KEY, host);
        intent.putExtra(GlobalKey.OTHER_WEB_TITLE_KEY, title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.get().startActivity(intent);
    }

}
