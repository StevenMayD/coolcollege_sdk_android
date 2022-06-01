package com.coolcollege.aar.act;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.coolcollege.aar.R;
import com.coolcollege.aar.callback.IBaseView;
import com.coolcollege.aar.callback.LocalMsg;
import com.coolcollege.aar.callback.OnMsgReceiveListener;
import com.coolcollege.aar.dialog.AppNotifyDialog;
import com.coolcollege.aar.dialog.AppProgressDialog;
import com.coolcollege.aar.maincolor.TitleBar;
import com.coolcollege.aar.utils.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;

import me.yokeyword.fragmentation.SupportActivity;


public abstract class SimpleActivity extends SupportActivity implements IBaseView, OnMsgReceiveListener {

    private FrameLayout flContent;
    private View childView;
    private int statusBarHeight;
    protected View statusBar;
    protected TitleBar titleBar;
//    private Unbinder bind;
    protected ImmersionBar immersionBar;
    public static final int DARK_STYLE = 1;
    public static final int LIGHT_STYLE = 2;
    private LinearLayout llRoot;
    protected AppNotifyDialog notifyDialog;
    private AppProgressDialog progressDialog;
    private RelativeLayout rlLoading;
    private LinearLayout llEmptyLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initStatusBar();
        initParentView();
        initParentListener();
        updateTitle();
        initData(savedInstanceState);
        initListener();
    }

    private void initParentListener() {
        titleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseData();
                onBackPressedSupport();
            }
        });
    }

    private void initParentView() {
        llRoot = findViewById(R.id.ll_root);
        statusBar = findViewById(R.id.status_bar);
        titleBar = findViewById(R.id.title_bar);
        rlLoading = findViewById(R.id.rl_loading);
        flContent = findViewById(R.id.fl_content);
        llEmptyLoading = findViewById(R.id.ll_empty_loading);
        childView = View.inflate(this, initLayout(), null);
//        bind = ButterKnife.bind(this, childView);
        flContent.addView(childView);

        titleBar.setTitleColor(titleTextColor());
        if (useMainColor()) {
            titleBar.setBackgroundMainColor();
            setStatusBarMainColor();
        }

        if (isNeedNotifyDialog()) notifyDialog = new AppNotifyDialog(this);
        progressDialog = new AppProgressDialog(this);

        if (isShowTitleBar()) {
            titleBar.setVisibility(View.VISIBLE);
        } else {
            titleBar.setVisibility(View.GONE);
        }


        if (isShowStatusBar()) {
            statusBar.setVisibility(View.VISIBLE);
        } else {
            statusBar.setVisibility(View.GONE);
        }

        initView();
        initTransparentView();
    }

    protected final void setStatusBarMainColor() {
        int mainColor = getResources().getColor(R.color.main_color);
        if (mainColor != 0) {
            statusBar.setBackgroundColor(mainColor);
        }
    }

    protected final void setStatusBarMainColor(int color) {
        if (color != 0) {
            statusBar.setBackgroundColor(color);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        releaseData();

        ToastUtils.cancel();

//        bind.unbind();
//        bind = null;

        if (notifyDialog != null) {
            if (notifyDialog.isShowing()) notifyDialog.dismiss();
            notifyDialog = null;
        }

        if (progressDialog.isShowing()) progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            onActivityForResult(requestCode, data);
        } else if (resultCode == RESULT_CANCELED) {
            onActivityResultCancelled(requestCode);
        }
    }

    protected abstract @LayoutRes
    int initLayout();

    protected abstract void initView();

    protected abstract void initData(Bundle bundle);

    protected abstract void initListener();

    protected abstract void updateTitle();

    protected void releaseData() {

    }

    @Override
    public void onStartLoad() {
        rlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartLoadNoAnim() {
        llEmptyLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartLoadProgress() {
        progressDialog.show();
    }

    @Override
    public void onComplete() {
        rlLoading.setVisibility(View.GONE);
        llEmptyLoading.setVisibility(View.GONE);
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void onErrorMsg(String msg, int code) {
        if (!TextUtils.isEmpty(msg)) ToastUtil.showToast(msg);
        rlLoading.setVisibility(View.GONE);
        llEmptyLoading.setVisibility(View.GONE);
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void onErrorMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) ToastUtil.showToast(msg);
        rlLoading.setVisibility(View.GONE);
        llEmptyLoading.setVisibility(View.GONE);
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    protected final void setTitleBar(String title, boolean isShowLeft, boolean isShowRight) {
        titleBar.setTitle(title);
        titleBar.isShowLeft(isShowLeft);
        titleBar.isShowRightPic(isShowRight);
    }

    protected final void setTitleBar(String title, boolean isShowLeft) {
        setTitleBar(title, isShowLeft, isShowRightPic());
    }

    protected final void setTitleBar(String title) {
        setTitleBar(title, isShowLeft(), isShowRightPic());
    }

    protected final void changeTitleStyle(String title, String right) {
        titleBar.setTitle(title);
        if (styleMode() == DARK_STYLE) {
            titleBar.setLeftPic(R.mipmap.ic_back);
            titleBar.setTitleColor(R.color.white_ff);
            titleBar.setRightTextColor(R.color.white_ff);
        } else if (styleMode() == LIGHT_STYLE) {
            titleBar.setLeftPic(R.mipmap.ic_back_black);
            titleBar.setRightPic(R.mipmap.ic_close_black);
            titleBar.setTitleColor(R.color.black_26);
            titleBar.setRightTextColor(R.color.black_26);
        }
        if (useMainColor()) {
            titleBar.setBackgroundMainColor();
            setStatusBarMainColor();
        } else {
            titleBar.setRootViewBg(R.color.main_blue);
        }
        titleBar.isShowLeft(isShowLeft());
        titleBar.isShowRightPic(isShowRightPic());
        if (!TextUtils.isEmpty(right)) {
            titleBar.setRightText(right);
            titleBar.isShowRightText(isShowRightText());
        }
        initStatusBar();
    }

    protected final void changeTitleStyle(String title) {
        this.changeTitleStyle(title, "");
    }

    protected boolean isShowLeft() {
        return true;
    }

    protected boolean isShowRightPic() {
        return false;
    }

    protected boolean isShowRightText() {
        return false;
    }

    protected boolean isShowTitleBar() {
        return true;
    }

    protected boolean isNeedNotifyDialog() {
        return false;
    }

    protected boolean useMainColor() {
        return true;
    }

    protected boolean releaseBackPress() {
        return false;
    }

    protected int statusBarColor() {
        return R.color.white;
    }

    protected int titleTextColor() {
        return R.color.white;
    }

    protected int styleMode() {
        return DARK_STYLE;
    }

    protected boolean isShowStatusBar() {
        return true;
    }

    public final View getChildRootView() {
        return childView;
    }

    protected final void isShowTitleAndStatusBar(boolean isShow) {
        if (isShow) {
            titleBar.setVisibility(View.VISIBLE);
            statusBar.setVisibility(View.VISIBLE);
        } else {
            titleBar.setVisibility(View.GONE);
            statusBar.setVisibility(View.GONE);
        }
    }

    protected void onActivityForResult(int requestCode, Intent intent) {

    }


    protected void onActivityResultCancelled(int requestCode) {

    }


    /**
     * 设置父类中根布局fitsSystemWindow属性
     * <p>
     * ** 因为使用了沉浸式状态栏所以当某个子类页面
     * 有EditText在底部时，弹出软键盘会遮挡输
     * 入框，即使设置了android:windowSoftInputMode="adjustResize"
     * 也会失效，所以如果想让adjustResize起
     * 作用必须给根布局设置fitsSystemWindow=true属性
     *
     * @param isFitsSystemWindows
     */
    public void isRootViewFitsSystemWindow(boolean isFitsSystemWindows) {

        if (isFitsSystemWindows) {
            statusBar.setVisibility(View.GONE);
        }

        llRoot.setFitsSystemWindows(isFitsSystemWindows);
    }

    /**
     * 获取系统状态栏高度
     */
    private void initTransparentView() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelOffset(resourceId);
        } else {
            statusBarHeight = (int) getResources().getDimension(R.dimen.status_bar_h25);
        }

        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = statusBarHeight;
        statusBar.setLayoutParams(layoutParams);
    }


    private void initStatusBar() {
        immersionBar = ImmersionBar.with(this);
        if (styleMode() == DARK_STYLE) {
            immersionBar.statusBarDarkFont(false);
        } else {
            immersionBar.statusBarDarkFont(true);
        }
        immersionBar.flymeOSStatusBarFontColor(R.color.white_ff);
        if (useMainColor()) {
            immersionBar.statusBarColor(android.R.color.transparent);
        } else {
            immersionBar.statusBarColor(R.color.main_color);
        }
        immersionBar.navigationBarColor(statusBarColor());
        immersionBar.init();
    }

    @Override
    public void onBackPressedSupport() {
        if (releaseBackPress()) {
            super.onBackPressedSupport();
        } else {
            if (rlLoading.getVisibility() != View.VISIBLE
                    && llEmptyLoading.getVisibility() != View.VISIBLE
                    && !progressDialog.isShowing()) {
                super.onBackPressedSupport();
            }
        }
    }

    protected void onSavedData(Bundle bundle){

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        onSavedData(outState);
    }

    @Override
    public void onReceive(LocalMsg msg) {

    }
}
