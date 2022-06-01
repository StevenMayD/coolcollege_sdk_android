package com.coolcollege.aar.utils;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.LogUtils;
import com.coolcollege.aar.callback.IActivityLifeCycleListener;
import com.coolcollege.aar.callback.IFragmentLifeCycleListener;
import com.coolcollege.aar.impl.AppActivityLifeCycleImpl;
import com.coolcollege.aar.impl.AppFragmentLifeCycleImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Evan_for on 2020/8/17
 */
public class ReleaseManager {

    private ArrayList<String> pageList;
    private HashMap<String, ArrayList<Call>> callMap;

    public ReleaseManager() {
        pageList = new ArrayList<>();
        callMap = new HashMap<>();
        setActivityLifeCycle();
        setFragmentLifeCycle();
    }

    private void setFragmentLifeCycle() {
        AppFragmentLifeCycleImpl fragmentLifeCycle = AppFragmentLifeCycleImpl.get();
        fragmentLifeCycle.setFragmentLifeCycleListener(new IFragmentLifeCycleListener() {

            @Override
            public void onCreate(FragmentManager fm, Fragment fragment, Bundle bundle) {

            }

            @Override
            public void onResume(FragmentManager fm, Fragment fragment) {
                String fragmentName = fragment.getClass().getName();
                pageList.add(fragmentName);
            }

            @Override
            public void onDestroy(FragmentManager fm, Fragment fragment) {
                String fragmentName = fragment.getClass().getName();
                releaseCalls(fragmentName);
            }
        });
    }

    private void setActivityLifeCycle() {
        AppActivityLifeCycleImpl activityLifeCycle = AppActivityLifeCycleImpl.get();
        activityLifeCycle.setAppLifeCycleListener(new IActivityLifeCycleListener() {
            @Override
            public void onCreate(Activity activity) {

            }

            @Override
            public void onResume(Activity activity) {
                String activityName = activity.getClass().getName();
                pageList.add(activityName);
            }

            @Override
            public void onDestroy(Activity activity) {
                String activityName = activity.getClass().getName();
                releaseCalls(activityName);
            }
        });
    }

    private void releaseCalls(String className) {
        ArrayList<Call> calls = callMap.get(className);
        if (calls != null) {
            for (Call call : calls) {
                call.cancel();
            }
            calls.clear();
            callMap.remove(className);
        }

        ArrayList<String> removeList = new ArrayList<>();

        for (String name : pageList) {
            if (className.equals(name)) {
                removeList.add(name);
                break;
            }
        }

        pageList.removeAll(removeList);
        LogUtils.e("releaseCalls");
//        if (BuildConfig.DEBUG) printReleaseLog();
    }

    private void printReleaseLog() {
        if (!pageList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, ArrayList<Call>> entry : callMap.entrySet()) {
                String key = entry.getKey();
                ArrayList<Call> value = entry.getValue();
                sb.append("call from class : ")
                        .append(key)
                        .append("\n");

                for (Call call : value) {
                    sb.append("call : ").append(call.toString()).append("\n");
                }
                LogUtils.e("release call : \n" + sb.toString() + "\n");
            }
        }
    }

    public void addCalls(Call call) {
        if (!pageList.isEmpty()) {
            String clazzName = pageList.get(pageList.size() - 1);
            ArrayList<Call> calls = callMap.get(clazzName);
            if (calls == null) {
                calls = new ArrayList<>();
                calls.add(call);
                callMap.put(clazzName, calls);
            } else {
                calls.add(call);
            }
        }
        //if (BuildConfig.DEBUG) printReleaseLog();
    }
}
