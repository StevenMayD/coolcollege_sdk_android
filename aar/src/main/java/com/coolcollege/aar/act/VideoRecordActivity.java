package com.coolcollege.aar.act;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.coolcollege.aar.R;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.dialog.AppNotifyDialog;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.helper.VideoRecorderHelper;
import com.coolcollege.aar.provider.FilePathProvider;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.aar.utils.ToastUtil;
import com.coolcollege.aar.view.AutoFitTextureView;

import java.io.File;
import java.lang.ref.WeakReference;

public class VideoRecordActivity extends SimpleActivity {
    AutoFitTextureView tvTexture;
    ImageView ivShape;
    RelativeLayout rlRecord;
    TextView tvCountDown;
    private VideoRecorderHelper recorder;
    private int dp8;
    private int dp12;
    private static final int MSG_RECORD = 1;
    private static final int MSG_STOP = 2;
    private static final int DEFAULT_COUNT_DOWN = 60;
    private static final int MIN_RECORD_TIME = 2;
    private int countDown = DEFAULT_COUNT_DOWN;
    private int duration;
    private boolean isStopRecord;
    private boolean isStartRecord;
    private boolean clickStopRecord;
    private MyHandler myHandler;
    private AppNotifyDialog saveDialog;
    private int maxTime;

    static class MyHandler extends Handler {

        WeakReference<VideoRecordActivity> reference;

        public MyHandler(VideoRecordActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            int what = msg.what;
            VideoRecordActivity activity = reference.get();

            switch (what) {
                case MSG_STOP:
                    if (AppUtils.isAppForeground()) {
                        activity.stopCountDown();
                    } else {
                        removeCallbacksAndMessages(null);
                    }
                    break;
                case MSG_RECORD:
                    activity.setCountDown();
                    break;
            }
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.k_activity_video_record;
    }

    @Override
    protected void initView() {
        tvTexture = getChildRootView().findViewById(R.id.tv_texture);
        ivShape = getChildRootView().findViewById(R.id.iv_shape);
        rlRecord = getChildRootView().findViewById(R.id.rl_record);
        tvCountDown = getChildRootView().findViewById(R.id.tv_count_down);

        tvTexture.setDeviceDimension(getResources().getDisplayMetrics());
        dp8 = getResources().getDimensionPixelOffset(R.dimen.k_dp_8);
        dp12 = getResources().getDimensionPixelOffset(R.dimen.k_dp_12);
        saveDialog = new AppNotifyDialog(this);
        saveDialog.setContentStyle("确定使用当前录制的视频？");
        saveDialog.setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData(Bundle bundle) {
        if (bundle == null) {
            Intent intent = getIntent();
            maxTime = intent.getIntExtra(GlobalKey.DURATION_KEY, 0);
        } else {
            maxTime = bundle.getInt(GlobalKey.DURATION_KEY, 0);
        }
        if (maxTime < 0) {
            maxTime = 0;
        }

        if (maxTime != 0) {
            tvCountDown.setText(maxTime + "s");
        } else {
            tvCountDown.setText(DEFAULT_COUNT_DOWN + "s");
        }

        myHandler = new MyHandler(this);
        recorder = new VideoRecorderHelper();
        recorder.setBitRate(VideoRecorderHelper.BITRATE_LOW);
        recorder.init(this);
        recorder.setTextureView(tvTexture);
    }

    @Override
    protected void onSavedData(Bundle bundle) {
        bundle.putInt(GlobalKey.DURATION_KEY, maxTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recorder.open();
        clickStopRecord = false;
        String data = saveDialog.getData();
        if (data != null && !TextUtils.isEmpty(data)) {
            if (!saveDialog.isShowing()) saveDialog.show();
        }
    }

    @Override
    protected void initListener() {

        rlRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecordView();
            }
        });

        recorder.setOnRecordListener(new VideoRecorderHelper.OnRecordListener() {
            @Override
            public void onStartRecord() {

            }

            @Override
            public void onCameraOpened() {
            }

            @Override
            public void onStopRecord(String path) {
                saveDialog.putData(path);
                if (clickStopRecord) {
                    saveDialog.show();
                }
            }
        });

        saveDialog.setOnConFirmClickListener(new AppNotifyDialog.OnConFirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                String data = saveDialog.getData();
                saveDialog.dismiss();
                returnVideo(data);
            }
        });

        saveDialog.setOnCancelClickListener(new AppNotifyDialog.OnCancelClickListener() {
            @Override
            public void onCancelClick(View view) {
                saveDialog.dismiss();
                recorder.startPreview();
            }
        });


        saveDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                saveDialog.putData("");
            }
        });
    }

    private void returnVideo(String path) {
        Intent intent = new Intent();
        TempFileBean fileBean = new TempFileBean();
        fileBean.path = path;
        fileBean.file = new File(path);
        fileBean.duration = String.valueOf(duration);
        fileBean.size = FilePathProvider.getFileSize(path);
        intent.putExtra(MediaSelector.RESULT_DATA, fileBean);
//        intent.putExtra(GlobalKey.VIDEO_PATH_KEY, fileBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startCountDown() {
        isStopRecord = false;
        isStartRecord = true;
        recorder.startRecordingVideo();
        countDown = maxTime != 0 ? maxTime : DEFAULT_COUNT_DOWN;
        myHandler.sendEmptyMessage(MSG_RECORD);
    }

    private void setUpRecordView() {
        clickStopRecord = true;
        if (recorder.isRecording()) {
            if ((maxTime != 0 ? maxTime : DEFAULT_COUNT_DOWN) - countDown < MIN_RECORD_TIME) {
                ToastUtil.showToast("录制时间太短");
            } else {
                stopCountDown();
                rlRecord.setPadding(dp8, dp8, dp8, dp8);
                ivShape.setImageResource(R.drawable.k_shape_oval_red_record);
            }
        } else {
            startCountDown();
            rlRecord.setPadding(dp12, dp12, dp12, dp12);
            ivShape.setImageResource(R.drawable.k_shape_rectangle_red_stop);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCountDown() {
        countDown--;

        tvCountDown.setText(countDown + "s");
        if (countDown == 1) {
            myHandler.sendEmptyMessage(MSG_STOP);
        } else {
            myHandler.sendEmptyMessageDelayed(MSG_RECORD, 1000);
        }
    }

    @SuppressLint("SetTextI18n")
    private void stopCountDown() {
        isStopRecord = true;
        isStartRecord = false;
        duration = (maxTime != 0 ? maxTime : DEFAULT_COUNT_DOWN) - countDown;
        countDown = maxTime != 0 ? maxTime : DEFAULT_COUNT_DOWN;
        myHandler.removeCallbacksAndMessages(null);
        recorder.stopRecordingVideo();
        rlRecord.setPadding(dp8, dp8, dp8, dp8);
        ivShape.setImageResource(R.drawable.k_shape_oval_red_record);
        tvCountDown.setText(countDown + "s");
    }


    @Override
    protected void updateTitle() {
        isShowTitleAndStatusBar(false);
    }

    @Override
    protected void onPause() {
        if (isStartRecord && !isStopRecord) {
            //若在录制状态下
            //用户手动按下home键，物理返回键等
            //任何情况导致当前activity不可见时
            //则主动保存录制的视频并返回数据
            stopCountDown();
        }
        recorder.close();
        super.onPause();
    }

    @Override
    protected void releaseData() {
        if (recorder != null) {
            if (recorder.isRecording()) recorder.close();
            recorder = null;
        }
        if (saveDialog != null) {
            if (saveDialog.isShowing()) saveDialog.dismiss();
            saveDialog.removeCancelListener();
            saveDialog.removeConFirmListener();
            saveDialog = null;
        }

        myHandler.removeCallbacksAndMessages(null);
    }
}