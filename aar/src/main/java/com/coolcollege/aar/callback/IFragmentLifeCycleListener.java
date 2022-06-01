package com.coolcollege.aar.callback;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by Evan_for on 2020/8/17
 */
public interface IFragmentLifeCycleListener {

    void onResume(FragmentManager fm, Fragment fragment);

    void onCreate(FragmentManager fm, Fragment fragment, Bundle bundle);

    void onDestroy(FragmentManager fm, Fragment fragment);

}
