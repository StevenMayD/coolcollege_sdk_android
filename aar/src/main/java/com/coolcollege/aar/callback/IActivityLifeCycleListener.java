package com.coolcollege.aar.callback;

import android.app.Activity;

/**
 * Created by Evan_for on 2020/8/17
 */
public interface IActivityLifeCycleListener {

    void onCreate(Activity activity);

    void onResume(Activity activity);

    void onDestroy(Activity activity);

}
