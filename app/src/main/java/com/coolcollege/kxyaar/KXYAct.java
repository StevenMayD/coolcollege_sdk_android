package com.coolcollege.kxyaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.bean.UploadFileBean;
import com.coolcollege.aar.callback.KXYCallback;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.module.APIModule;
import com.coolcollege.aar.selector.MediaSelector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KXYAct extends Activity {

    private Button btn_startAudioRecord;
    private Button btn_startVideoRecord;
    private Button btn_chooseImage;
    private Button btn_chooseVideo;
    private Button btn_uploadFile;
    private TextView textView;

    private Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Utils.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            super.onActivityResumed(activity);
            Log.e("onActiveChange", "foreground-");
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            super.onActivityPaused(activity);
            Log.e("onActiveChange", "background-");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        ActivityUtils.addActivityLifecycleCallbacks(this, activityLifecycleCallbacks);

        initView();
    }

    private void initView() {
        textView = findViewById(R.id.textView);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        btn_startAudioRecord = findViewById(R.id.btn_startAudioRecord);
        btn_startAudioRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new APIModule().startAudioRecord(KXYAct.this, 10, new KXYCallback() {
                    @Override
                    public void onCallback(Object o) {
                        TempFileBean tempFile = (TempFileBean) o;
                        StringBuffer sb = new StringBuffer();
                        sb.append("path: ").append(tempFile.path).append("\n");
                        sb.append("size: ").append(tempFile.size).append("\n");
                        sb.append("duration: ").append(tempFile.duration).append("\n");
                        textView.setText(sb.toString());
                    }
                });
            }
        });

        btn_startVideoRecord = findViewById(R.id.btn_startVideoRecord);
        btn_startVideoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new APIModule().startVideoRecord(KXYAct.this, 10, 123);
            }
        });

        btn_chooseImage = findViewById(R.id.btn_chooseImage);
        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new APIModule().chooseImage(KXYAct.this, 9, 80, true, 234);
            }
        });

        btn_chooseVideo = findViewById(R.id.btn_chooseVideo);
        btn_chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new APIModule().chooseVideo(KXYAct.this, 100, 3, 345);
            }
        });

        btn_uploadFile = findViewById(R.id.btn_uploadFile);
        btn_uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadFileBean uploadFileBean = new UploadFileBean();
                uploadFileBean.name = "file";
                uploadFileBean.url = "https://coolapi.coolcollege.cn/enterprise-api/common/upload";
                uploadFileBean.filePath = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20220506_172917_com.ikongjian.worker7741c888-7c30-48e2-9c3a-cf5f34c0bc46.jpg";
                Map<String, String> formMap = new HashMap<>();
                formMap.put("access_token", "23b2b840e0e444119706842817678a2d");
                uploadFileBean.formData = formMap;
//                Map<String, String> headerMap = new HashMap<>();
//                headerMap.put("access_token", "23b2b840e0e444119706842817678a2d");
//                uploadFileBean.header = headerMap;
                new APIModule().uploadFile(KXYAct.this, uploadFileBean, new KXYCallback() {
                    @Override
                    public void onCallback(Object o) {
                        textView.setText((String) o);
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        switch (requestCode) {
            case 123:
                TempFileBean videoTemp = data.getParcelableExtra(GlobalKey.VIDEO_PATH_KEY);
                StringBuffer sb = new StringBuffer();
                sb.append("path: ").append(videoTemp.path).append("\n");
                sb.append("size: ").append(videoTemp.size).append("\n");
                sb.append("duration: ").append(videoTemp.duration).append("\n");
                textView.setText(sb.toString());
                break;
            case 234:
            case 345:
                ArrayList<MediaItemBean> mediaItem = data.getParcelableArrayListExtra(MediaSelector.RESULT_DATA);
                StringBuffer sbMedia = new StringBuffer();
                for (MediaItemBean item : mediaItem) {
                    sbMedia.append("{");
                    sbMedia.append("path: ").append(item.path).append("\n");
                    sbMedia.append("size: ").append(item.size).append("\n");
                    sbMedia.append("duration: ").append(item.duration);
                    sbMedia.append("}").append("\n");
                }
                textView.setText(sbMedia.toString());
                break;
        }
    }
}
