package com.coolcollege.aar.helper;

import android.media.MediaRecorder;

import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.provider.FilePathProvider;

import java.io.IOException;

/**
 * Created by Evan_for on 2020/7/13
 */
public class AudioRecorderHelper {

    private static AudioRecorderHelper instance = new AudioRecorderHelper();
    private MediaRecorder recorder;
    private TempFileBean tempFileBean;

    private AudioRecorderHelper() {
    }

    public static AudioRecorderHelper get() {
        return instance;
    }

    public AudioRecorderHelper init() {
        recorder = new MediaRecorder();
        return instance;
    }

    public void prepare() {
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风

            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            tempFileBean = FilePathProvider.getOutputMediaFileUri(FilePathProvider.TYPE_AUDIO);

            recorder.setOutputFile(tempFileBean.path);
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TempFileBean getTempFile() {
        return tempFileBean;
    }

    public void stopRecord() {
        try {
            recorder.stop();
            recorder.reset();
        } catch (RuntimeException e) {
            recorder.reset();
        }
    }

    public void release(){
        recorder.release();
        recorder = null;
    }
}
