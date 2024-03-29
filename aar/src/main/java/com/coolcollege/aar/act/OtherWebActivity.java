package com.coolcollege.aar.act;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.coolcollege.aar.R;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.webdsbridge.ApiWebView;

public class OtherWebActivity extends SimpleActivity {

//    @BindView(R.id.wb_View)
    ApiWebView wbView;
//    @BindView(R.id.pb_top)
    ProgressBar pbTop;
    private String title;
    private String path;
    private String host;
    private int leftPic = 999;

    @Override
    protected int initLayout() {
        return R.layout.activity_other_web;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        wbView = findViewById(R.id.wb_View);
        pbTop = findViewById(R.id.pb_top);

        WebSettings settings = wbView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initData(Bundle bundle) {
        if (bundle == null) {
            Intent intent = getIntent();
            title = intent.getStringExtra(GlobalKey.OTHER_WEB_TITLE_KEY);
            path = intent.getStringExtra(GlobalKey.OTHER_WEB_PATH_KEY);
            host = intent.getStringExtra(GlobalKey.OTHER_WEB_HOST_KEY);
            leftPic = intent.getIntExtra(GlobalKey.OTHER_WEB_TITLE_BAR_LEFT_PIC_KEY, 999);
        } else {
            title = bundle.getString(GlobalKey.OTHER_WEB_TITLE_KEY);
            path = bundle.getString(GlobalKey.OTHER_WEB_PATH_KEY);
            host = bundle.getString(GlobalKey.OTHER_WEB_HOST_KEY);
        }

        if (title != null) {
            setTitleBar(title);
        } else {
            isShowTitleAndStatusBar(false);
        }

        if (leftPic != 999) {
            titleBar.setLeftPic(leftPic);
        }
        String url = path;
        wbView.loadUrl(url);
    }

    @Override
    protected void onSavedData(Bundle bundle) {
        bundle.putString(GlobalKey.OTHER_WEB_TITLE_KEY, title);
        bundle.putString(GlobalKey.OTHER_WEB_PATH_KEY, path);
        bundle.putString(GlobalKey.OTHER_WEB_HOST_KEY, host);
    }

    @Override
    protected void initListener() {

        wbView.setOnLoadProcessListener(new ApiWebView.OnLoadProcessListener() {
            @Override
            public void onProcessChanged(int process) {
                if (process == 100) {
                    onComplete();
                    pbTop.setVisibility(View.GONE);
                } else {
                    onStartLoad();
                    pbTop.setProgress(process);
                    pbTop.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void updateTitle() {
    }

    @Override
    protected void releaseData() {
        if (wbView != null) {
            wbView.removeOnLoadProcessListener();
            ViewParent parent = wbView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(wbView);
            }
            wbView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wbView.getSettings().setJavaScriptEnabled(false);
            wbView.clearHistory();
            wbView.clearView();
            wbView.removeAllViews();
            wbView.destroy();
            wbView = null;
        }
    }
}