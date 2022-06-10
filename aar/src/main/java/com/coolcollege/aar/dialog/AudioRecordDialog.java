package com.coolcollege.aar.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.coolcollege.aar.R;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.helper.AudioRecorderHelper;
import com.coolcollege.aar.provider.FilePathProvider;

import java.lang.ref.WeakReference;


/**
 * Created by Evan_for on 2020/7/13
 */
public class AudioRecordDialog extends BaseDialog {

    private TextView tvTime;
    private RelativeLayout rlStop;
    private MyHandler myHandler;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private int countDown;
    private int maxDuration;
    private static final int DEFAULT_COUNT_DOWN = 59;
    private static final int MSG_DELAYED = 1000;
    private AudioRecorderHelper recorderHelper;

    static class MyHandler extends Handler {

        WeakReference<AudioRecordDialog> reference;

        MyHandler(AudioRecordDialog dialog) {
            reference = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            AudioRecordDialog dialog = reference.get();
            int what = msg.what;

            switch (what) {
                case MSG_START:
                    dialog.countDown--;
                    if (dialog.countDown == 0) {
                        dialog.stop();
                    } else {
                        dialog.setCountDown();
                    }
                    break;
                case MSG_STOP:
                    dialog.recordComplete();
                    removeCallbacksAndMessages(null);
                    break;
            }
        }
    }

    public  AudioRecordDialog(@NonNull Context context) {
        this(context, R.style.appBaseDialogStyle);
    }

    protected AudioRecordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_audio_record;
    }

    @Override
    protected void initView() {
        tvTime = getBaseView().findViewById(R.id.tv_time);
        rlStop = getBaseView().findViewById(R.id.rl_stop);

        myHandler = new MyHandler(this);
        recorderHelper = AudioRecorderHelper.get().init();
        setCancelable(false);
    }

    private void prepare() {
        countDown = maxDuration != 0 ? maxDuration : DEFAULT_COUNT_DOWN;
        recorderHelper.prepare();
        myHandler.sendEmptyMessageDelayed(MSG_START, MSG_DELAYED);
    }

    private void setCountDown() {
        tvTime.setText(String.valueOf(countDown));
        myHandler.sendEmptyMessageDelayed(MSG_START, MSG_DELAYED);
    }

    private void stop() {
        recorderHelper.stopRecord();
        myHandler.sendEmptyMessage(MSG_STOP);
    }

    @Override
    protected int defaultGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int defaultWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int windowAnim() {
        return R.style.dialogBtm2TopAnim;
    }

    @Override
    protected Drawable windowBgDrawable() {
        return null;
    }

    @Override
    public void show() {
        super.show();
        tvTime.setText(String.valueOf(maxDuration != 0 ? maxDuration : DEFAULT_COUNT_DOWN));
        prepare();
    }

    @Override
    protected void initListener() {
        rlStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((maxDuration != 0 ? maxDuration : DEFAULT_COUNT_DOWN) - countDown > 3) {
                    stop();
                } else {
                    Toast.makeText(getContext(), "录音时长过短", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void recordComplete() {
        tvTime.setText(String.valueOf(DEFAULT_COUNT_DOWN));
        if (onRecordCompleteListener != null) {
            TempFileBean tempFile = recorderHelper.getTempFile();
            tempFile.duration = String.valueOf((maxDuration != 0 ? maxDuration : DEFAULT_COUNT_DOWN) - countDown);
            tempFile.size = FilePathProvider.getFileSize(tempFile.path);
            onRecordCompleteListener.onComplete(tempFile);
        }
        dismiss();
    }

    public interface OnRecordCompleteListener {
        void onComplete(TempFileBean tempFile);
    }

    private OnRecordCompleteListener onRecordCompleteListener;

    public void setOnRecordCompleteListener(OnRecordCompleteListener onRecordCompleteListener) {
        this.onRecordCompleteListener = onRecordCompleteListener;
    }

    public void releaseRecorder() {
        recorderHelper.release();
    }

}
