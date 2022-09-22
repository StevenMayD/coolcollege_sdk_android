package com.coolcollege.aar.callback;

import java.util.HashMap;
import java.util.Map;

// 接口实现LocationManager类的回调
public interface ILocationCallback {
    void onSuccessCallback(HashMap callback); // 定位成功 回调
    void onErrorCallback(HashMap callback);  // 定位失败 回调
}
