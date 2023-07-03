package com.coolcollege.aar.utils;

import android.app.Application;
import android.content.Intent;

import com.coolcollege.aar.act.OtherWebActivity;
import com.coolcollege.aar.global.GlobalKey;

/**
 * Created by Evan_for on 2020/8/5
 */
public class WebPageUtils {

    public static void startWebPage(String host, String url,String title, int titleBarLeftPic, Application application) {
        Intent intent = new Intent(application, OtherWebActivity.class);
        intent.putExtra(GlobalKey.OTHER_WEB_PATH_KEY, url);
        intent.putExtra(GlobalKey.OTHER_WEB_HOST_KEY, host);
        intent.putExtra(GlobalKey.OTHER_WEB_TITLE_KEY, title);
        intent.putExtra(GlobalKey.OTHER_WEB_TITLE_BAR_LEFT_PIC_KEY, titleBarLeftPic);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }

}
