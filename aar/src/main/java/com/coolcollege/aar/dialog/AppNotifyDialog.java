package com.coolcollege.aar.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coolcollege.aar.R;
import com.coolcollege.aar.maincolor.TextViewMainColor;

/**
 * 项目名称：cn.usho.sosho.customview.dialog
 * 类描述：
 * 作者：   admin .
 * 日期：   2018/7/23 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public class AppNotifyDialog extends BaseDialog {

    TextView tvTitle;
    TextView tvContent;
    TextView centerLine;
    TextView btnCancel;
    TextViewMainColor btnConfirm;
    private String data;
    private Object obj;
    private int index;
    private View mView;

    public AppNotifyDialog(@NonNull Context context) {
        this(context, R.style.appBaseDialogStyle);
    }

    public AppNotifyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int initLayout() {
        return R.layout.layout_app_notify_dialog;
    }

    @Override
    protected void initView() {
        tvTitle = getBaseView().findViewById(R.id.tv_title);
        tvContent = getBaseView().findViewById(R.id.tv_content);
        centerLine = getBaseView().findViewById(R.id.center_line);
        btnCancel = getBaseView().findViewById(R.id.btn_cancel);
        btnConfirm = getBaseView().findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelClickListener != null) {
                    onCancelClickListener.onCancelClick(v);
                } else {
                    dismiss();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConFirmClickListener != null) {
                    onConFirmClickListener.onConfirmClick(v);
                }
            }
        });
    }

    public void putData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void putObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void putIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void putView(View mView) {
        this.mView = mView;
    }

    public View getView() {
        return mView;
    }

    public void setContentTitle(String title) {
        tvTitle.setText(title);
        tvTitle.setVisibility(View.VISIBLE);
    }

    public void setConfirmBtnText(String text) {
        btnConfirm.setText(text);
    }

    public void setCancelBtnText(String text) {
        btnCancel.setText(text);
    }

    public void setContentGravity(int gravity) {
        tvContent.setGravity(gravity);
    }

    public void setContentStyle(String content) {
        this.setContentStyle(content, true);
    }

    public void setContentStyle(CharSequence content, boolean isShowCancel) {

        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

        if (isShowCancel) {
            btnCancel.setVisibility(View.VISIBLE);
            centerLine.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.GONE);
            centerLine.setVisibility(View.GONE);
        }
    }

    public void setContentText(String text) {
        tvContent.setText(text);
    }

    public interface OnConFirmClickListener {
        void onConfirmClick(View view);
    }

    private OnConFirmClickListener onConFirmClickListener;

    public void setOnConFirmClickListener(OnConFirmClickListener onConFirmClickListener) {
        this.onConFirmClickListener = onConFirmClickListener;
    }

    public void removeConFirmListener(){
        onConFirmClickListener = null;
    }

    public interface OnCancelClickListener {
        void onCancelClick(View view);
    }

    private OnCancelClickListener onCancelClickListener;

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    public void removeCancelListener(){
        onCancelClickListener = null;
    }
}
