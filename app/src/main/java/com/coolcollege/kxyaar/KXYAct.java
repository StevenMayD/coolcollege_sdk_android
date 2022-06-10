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
import com.coolcollege.aar.bean.AudioRecordBean;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.bean.NativeEventParams;
import com.coolcollege.aar.bean.PickImgBean;
import com.coolcollege.aar.bean.PickVideoBean;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.bean.UploadFileBean;
import com.coolcollege.aar.bean.VideoRecordBean;
import com.coolcollege.aar.callback.KXYCallback;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.module.APIModule;
import com.coolcollege.aar.selector.MediaSelector;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KXYAct extends Activity {

    private Button btn_startAudioRecord;
    private Button btn_startVideoRecord;
    private Button btn_chooseImage;
    private Button btn_chooseVideo;
    private Button btn_uploadFile;
    private Button btn_ossUploadFile;
    private Button btn_shareMenu;
    private TextView textView;

    private String acToken = "af0e91b07e9a4887a9f3d895fc80c732";
    private String entId = "1067985194709028888";

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
                AudioRecordBean audioRecordBean = new AudioRecordBean();
                audioRecordBean.maxDuration = 10;

                NativeEventParams params = new NativeEventParams();
                params.methodName = "startAudioRecord";
                params.methodData = new Gson().toJson(audioRecordBean);

                callModule(params);
            }
        });

        btn_startVideoRecord = findViewById(R.id.btn_startVideoRecord);
        btn_startVideoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoRecordBean video = new VideoRecordBean();
                video.maxDuration = 60;

                NativeEventParams params = new NativeEventParams();
                params.methodName = "startVideoRecord";
                params.methodData = new Gson().toJson(video);

                callModule(params);
            }
        });

        btn_chooseImage = findViewById(R.id.btn_chooseImage);
        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImgBean imgBean = new PickImgBean();
                imgBean.count = 9;
                imgBean.percent = 80;
                imgBean.compressed = true;

                NativeEventParams params = new NativeEventParams();
                params.methodName = "chooseImage";
                params.methodData = new Gson().toJson(imgBean);

                callModule(params);
            }
        });

        btn_chooseVideo = findViewById(R.id.btn_chooseVideo);
        btn_chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickVideoBean videoBean = new PickVideoBean();
                videoBean.count = 9;
                videoBean.maxDuration = 60;

                NativeEventParams params = new NativeEventParams();
                params.methodName = "chooseVideo";
                params.methodData = new Gson().toJson(videoBean);

                callModule(params);
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
                formMap.put("access_token", acToken);
                uploadFileBean.formData = formMap;
//                Map<String, String> headerMap = new HashMap<>();
//                headerMap.put("access_token", "23b2b840e0e444119706842817678a2d");
//                uploadFileBean.header = headerMap;


                NativeEventParams params = new NativeEventParams();
                params.methodName = "uploadFile";
                params.methodData = new Gson().toJson(uploadFileBean);

                callModule(params);
            }
        });

        btn_ossUploadFile = findViewById(R.id.btn_ossUploadFile);
        btn_ossUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "OSSUploadFile";
//                params.methodData = "{\"type\":\"image\",\"files\":[{\"filePath\": \"/storage/emulated/0/Pictures/Screenshots/Screenshot_20220506_172917_com.ikongjian.worker7741c888-7c30-48e2-9c3a-cf5f34c0bc46.jpg\", \"objectKey\":\"lu001\"}]}";
                params.methodData = "{\"type\":\"video\",\"files\":[{\"filePath\": \"/storage/emulated/0/DCIM/Camera/VID_20220316_164604.mp4\", \"objectKey\":\"lu002\"}]}";

                callModule(params);
            }
        });

        btn_shareMenu = findViewById(R.id.btn_shareMenu);
        btn_shareMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "shareMenu";
//                params.methodData = "{\"logo\":\"https://oss.coolcollege.cn/1810536106161213440.png\"}";
                params.methodData = "{\"url\":\"https://sdn.coolcollege.cn/coolcollege-apps-share/hd/index.html?token=87fa67b66479891aa5f25a0ee86d01e6&kid=1810536062049718272&eid=1067985194709028888&aid=cool\",\"title\":\"sgyw图文课测试\",\"content\":\"QQ\",\"logo\":\"https://oss.coolcollege.cn/1810536106161213440.png\"}";

                callModule(params);
            }
        });
    }

    private void callModule (NativeEventParams params) {
        APIModule.getAPIModule(KXYAct.this).moduleManage(params, acToken, entId, 123, new KXYCallback() {
            @Override
            public void onOKCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(new Gson().toJson(o));
                    }
                });
            }

            @Override
            public void onErrorCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(new Gson().toJson(o));
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        textView.setText(new Gson().toJson(data.getParcelableExtra(MediaSelector.RESULT_DATA) != null ? data.getParcelableExtra(MediaSelector.RESULT_DATA) : data.getParcelableArrayListExtra(MediaSelector.RESULT_DATA)));
//        switch (requestCode) {
//            case 123:
////                TempFileBean videoTemp = data.getParcelableExtra(GlobalKey.VIDEO_PATH_KEY);
////                StringBuffer sb = new StringBuffer();
////                sb.append("path: ").append(videoTemp.path).append("\n");
////                sb.append("size: ").append(videoTemp.size).append("\n");
////                sb.append("duration: ").append(videoTemp.duration).append("\n");
////                textView.setText(sb.toString());
//                textView.setText(new Gson().toJson(data));
//                break;
//            case 234:
//            case 345:
//                ArrayList<MediaItemBean> mediaItem = data.getParcelableArrayListExtra(MediaSelector.RESULT_DATA);
//                StringBuffer sbMedia = new StringBuffer();
//                for (MediaItemBean item : mediaItem) {
//                    sbMedia.append("{");
//                    sbMedia.append("path: ").append(item.path).append("\n");
//                    sbMedia.append("size: ").append(item.size).append("\n");
//                    sbMedia.append("duration: ").append(item.duration);
//                    sbMedia.append("}").append("\n");
//                }
//                textView.setText(sbMedia.toString());
//                break;
//        }
    }
}
