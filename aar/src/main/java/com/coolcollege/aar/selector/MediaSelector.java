package com.coolcollege.aar.selector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.coolcollege.aar.bean.MediaItemBean;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Evan_for on 2020/7/8
 */
public final class MediaSelector {

    private WeakReference<Activity> activity;
    private WeakReference<Fragment> fragment;
    public static final String TYPE_IMG = "type_img";
    public static final String TYPE_VIDEO = "type_video";
    public static final String SELECTOR_OPTIONS_KEY = "selector_options";
    public static final String RESULT_DATA = "result_data";
    public static final int MAX_COUNT = 9;
    public static final int MIN_COUNT = 1;

    private MediaSelector() {
    }

    private MediaSelector(Activity activity) {
        this(activity, null);
    }

    private MediaSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private MediaSelector(Activity activity, Fragment fragment) {
        this.activity = new WeakReference<>(activity);
        this.fragment = new WeakReference<>(fragment);
    }


    public static MediaSelector from(Activity activity) {
        return new MediaSelector(activity);
    }

    public static MediaSelector from(Fragment fragment) {
        return new MediaSelector(fragment);
    }

    public SelectorOptions withType(String type) {
        return new SelectorOptions(this, type);
    }

    public static ArrayList<MediaItemBean> obtainResult(Intent intent) {
        ArrayList<MediaItemBean> data;
        if (intent == null) {
            data = new ArrayList<>();
        } else {
            data = intent.getParcelableArrayListExtra(MediaSelector.RESULT_DATA);
            if (data == null) data = new ArrayList<>();
        }
        return data;
    }

    public static ArrayList<String> obtainPathResult(Intent intent) {
        ArrayList<String> data = new ArrayList<>();
        if (intent != null) {
            ArrayList<MediaItemBean> list = intent.getParcelableArrayListExtra(MediaSelector.RESULT_DATA);
            if (list != null) {
                for (MediaItemBean bean : list) {
                    data.add(bean.path);
                }
            }
        }

        return data;
    }

    public static Uri[] obtainUriResult(Intent intent) {
        ArrayList<MediaItemBean> data = intent.getParcelableArrayListExtra(MediaSelector.RESULT_DATA);
        if (data == null) data = new ArrayList<>();
        Uri[] uri = new Uri[data.size()];
        for (int i = 0; i < data.size(); i++) {
            MediaItemBean bean = data.get(i);
            uri[i] = Uri.fromFile(new File(bean.path));
        }
        return uri;
    }

    public Activity getActivity() {
        return activity.get();
    }

    public Fragment getFragment() {
        return fragment.get();
    }
}
