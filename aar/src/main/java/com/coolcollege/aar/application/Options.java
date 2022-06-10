package com.coolcollege.aar.application;


import android.app.Application;

import com.coolcollege.aar.helper.RetrofitHelper;

public class Options {

    private static Application instance;
//    private static AppNotification appNotification;

    public static void init (Application application) {
        instance = application;

//        if (SpUtils.getString("regionServe") != null && SpUtils.getString("regionServe") != "") {
//            MethodUtils.regionServe = SpUtils.getString("regionServe");
//        }
//        PlatformApplication.get().init(application);
//        application.registerActivityLifecycleCallbacks(AppActivityLifeCycleImpl.get());
//        Utils.init(application);
        RetrofitHelper.get().init("https://coolapi.coolcollege.cn/platform-api/");
//        appNotification = new AppNotification(application.getApplicationContext());
//        CrashHandlerUtils crashHandler = CrashHandlerUtils.getInstance();
//        crashHandler.init(application.getApplicationContext());
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(base);
//    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        aCache = ACache.get(this, BuildConfig.APPLICATION_ID);
//        config.setLogSwitch(BuildConfig.DEBUG);
//        GlobalMsgHelper.get().register(GlobalKey.ACTION_LOGIN_SUCCESS, this);
//        GlobalMsgHelper.get().register(GlobalKey.ACTION_LOGIN_OUT, this);
//    }

    public static Application get() {
        return instance;
    }

//    public static AppNotification getAppNotification() {
//        return appNotification;
//    }

//    public ACache getACache(){
//        return aCache;
//    }

//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        if (res.getConfiguration().fontScale != 1) {
//            Configuration newConfig = new Configuration();
//            newConfig.setToDefaults();
//            res.updateConfiguration(newConfig, res.getDisplayMetrics());
//        }
//
//        return res;
//    }
}
