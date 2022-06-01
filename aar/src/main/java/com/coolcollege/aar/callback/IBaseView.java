package com.coolcollege.aar.callback;

public interface IBaseView {

    void onStartLoad();

    void onStartLoadNoAnim();

    void onStartLoadProgress();

    void onComplete();

    void onErrorMsg(String msg, int code);

    void onErrorMsg(String msg);

}
