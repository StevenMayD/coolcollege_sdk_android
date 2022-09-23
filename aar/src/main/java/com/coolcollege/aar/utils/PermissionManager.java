package com.coolcollege.aar.utils;


import android.app.Activity;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.PermissionDef;

public class PermissionManager {

    public static void checkPermissions(Activity activity, PermissionStateListener listener, @PermissionDef String... permissions) {
        boolean has = AndPermission.hasPermissions(activity, permissions);
        if (has) {
            listener.onGranted();
        } else {
            AndPermission.with(activity)
                    .runtime()
                    .permission(permissions)
                    .onGranted(pms -> {
                        listener.onGranted();
                    })
                    .onDenied(pms -> {
                        listener.onDenied();
                    })
                    .start();
        }
    }
}
