package com.coolcollege.aar.utils;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.coolcollege.aar.R;
import com.coolcollege.aar.application.Options;

public class ToastUtil {


    public static void showToast(String text){
        View view = View.inflate(Options.get(), R.layout.k_layout_custom_toast, null);
        TextView tvMsg = view.findViewById(R.id.tv_msg);
        tvMsg.setText(text);
        ToastUtils.showCustomShort(view);
    }

}
