package com.coolcollege.aar.bean;

public class NativeEventParams {

    public String methodName;
    public String methodData;

    public NativeEventParams() {
    }

    public NativeEventParams(String methodName, String methodData) {
        this.methodName = methodName;
        this.methodData = methodData;
    }
}
