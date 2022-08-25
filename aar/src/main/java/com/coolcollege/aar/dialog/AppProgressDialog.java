package com.coolcollege.aar.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.coolcollege.aar.R;


/**
 * Created by Evan_for on 2020/7/14
 */
public class AppProgressDialog extends BaseDialog {

    public AppProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.k_dialog_app_progress;
    }

    @Override
    protected void initView() {
        setCancelable(false);
//        setCancelable(BuildConfig.DEBUG);
    }

    @Override
    protected void initListener() {

    }
}
