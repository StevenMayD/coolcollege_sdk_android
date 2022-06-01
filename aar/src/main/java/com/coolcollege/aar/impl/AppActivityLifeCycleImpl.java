package com.coolcollege.aar.impl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coolcollege.aar.callback.IActivityLifeCycleListener;

/**
 * Created by Evan_for on 2020/8/17
 */
public class AppActivityLifeCycleImpl implements Application.ActivityLifecycleCallbacks {

    private static AppActivityLifeCycleImpl instance = new AppActivityLifeCycleImpl();
    private IActivityLifeCycleListener listener;

    private AppActivityLifeCycleImpl() {
    }

    public static AppActivityLifeCycleImpl get() {
        return instance;
    }

    public void setAppLifeCycleListener(IActivityLifeCycleListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (listener != null) listener.onCreate(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (listener != null) listener.onResume(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (listener != null) listener.onDestroy(activity);
    }
}
