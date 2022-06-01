package com.coolcollege.aar.impl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.coolcollege.aar.callback.IFragmentLifeCycleListener;

/**
 * Created by Evan_for on 2020/8/17
 */
public class AppFragmentLifeCycleImpl extends FragmentManager.FragmentLifecycleCallbacks {

    private static AppFragmentLifeCycleImpl instance = new AppFragmentLifeCycleImpl();
    private IFragmentLifeCycleListener listener;

    private AppFragmentLifeCycleImpl() {
    }

    public static AppFragmentLifeCycleImpl get() {
        return instance;
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
        if (listener != null) listener.onDestroy(fm, f);
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        if (listener != null) listener.onResume(fm, f);
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        if (listener != null) listener.onCreate(fm, f, savedInstanceState);
    }

    public void setFragmentLifeCycleListener(IFragmentLifeCycleListener listener) {
        this.listener = listener;
    }
}
