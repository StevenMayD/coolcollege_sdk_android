package com.coolcollege.aar.utils;

import android.app.Application;
import android.content.Intent;

import com.coolcollege.aar.act.OtherWebActivity;

/**
 * Created by Evan_for on 2020/8/5
 */
public class WebPageUtils {

    public static void startWebPage(String host, String url,String title, int titleBarLeftPic, Application application) {
        Intent intent = new Intent(application, OtherWebActivity.class);
        intent.putExtra("web_path", url);
        intent.putExtra("web_host", host);
        intent.putExtra("web_title", title);
        intent.putExtra("web_title_bar_left_pic", titleBarLeftPic);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

}
