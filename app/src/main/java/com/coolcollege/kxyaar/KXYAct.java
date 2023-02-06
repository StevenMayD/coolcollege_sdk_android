package com.coolcollege.kxyaar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.coolcollege.aar.bean.NativeEventParams;
import com.coolcollege.aar.bean.PickImgBean;
import com.coolcollege.aar.bean.PickVideoBean;
import com.coolcollege.aar.bean.UploadFileBean;
import com.coolcollege.aar.bean.VideoRecordBean;
import com.coolcollege.aar.callback.KXYCallback;
import com.coolcollege.aar.module.APIModule;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.application.MyApplication;
import com.google.gson.Gson;

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
    private Button btn_scan;
    private Button btn_getLocation;
    private Button btn_vibration;
    private Button btn_sendMessage;
    private Button btn_copyMessage;
    private Button btn_saveImage;
    private Button btn_getSystemInfo;

    private TextView textView;

    private String entId = "1324923316665978965"; // 酷帮手企业id 非真实值的话，上传报错：{"error":"请求失败，请稍后重试", "isError":true}

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
                formMap.put("access_token", "728babac3b2448abb1eed222e3dfbf1b");
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
//                params.methodData = "{\"accessToken\":\"728babac3b2448abb1eed222e3dfbf1b\",\"type\":\"image\",\"files\":[{\"filePath\": \"/storage/emulated/0/Pictures/AppGallery/4_46ed8ff772db4368b10b8150da299df8e96cafbe-e72d-42cb-a964-489de1a903ececc09acb-5e64-44f7-abf6-eb19865c6f6f.jpg\", \"objectKey\":\"lu001\"}]}";
                params.methodData = "{\"accessToken\":\"728babac3b2448abb1eed222e3dfbf1b\",\"type\":\"video\",\"files\":[{\"filePath\": \"/storage/emulated/0/Pictures/Screenshots/SVID_20220909_143707_1.mp4\", \"objectKey\":\"lu002\"}]}";
                // image和video均验证通过：filePath: 上传文件不存在的话，上传进度视图的进度条始终0% 无法上传(后续优化，检测文件是否存在、实际调用文件都会存在)；     accessToken不正确的话：上传报错：{"error":"获取凭证失败，请稍后重试", "isError":true}
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
        /** 扫码 */
        btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "scan";
                params.methodData = "{}";

                callModule(params);
            }
        });
        /** 定位 */
        btn_getLocation = findViewById(R.id.btn_getLocation);
        btn_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "getLocation";
                params.methodData = "{}";

                callModule(params);
            }
        });
        /** 震动 */
        btn_vibration = findViewById(R.id.btn_vibration);
        btn_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "vibration";
                params.methodData = "{\"duration\":1000}";

                callModule(params);
            }
        });
        /** 复制信息并跳转微信 */
        btn_sendMessage = findViewById(R.id.btn_sendMessage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "sendMessage";
                params.methodData = "{\"content\":\"人来人往\"}";

                callModule(params);
            }
        });
        /** 复制信息只粘贴板 */
        btn_copyMessage = findViewById(R.id.btn_copyMessage);
        btn_copyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "copyMessage";
                params.methodData = "{\"content\":\"202302031833\",\"alert\":\"已复制信息至粘贴板\"}";

                callModule(params);
            }
        });
        /** 保存图片收相册 */
        btn_saveImage = findViewById(R.id.btn_saveImage);
        btn_saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "saveImage";
                params.methodData = "{\"url\":\"https://tenfei04.cfp.cn/creative/vcg/veer/612/veer-310941708.jpg\"}";

                callModule(params);
            }
        });
        /** 获取手机系统信息 */
        btn_getSystemInfo = findViewById(R.id.btn_getSystemInfo);
        btn_getSystemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NativeEventParams params = new NativeEventParams();
                params.methodName = "getSystemInfo";
                params.methodData = "{}";

                callModule(params);
            }
        });
    }

    private void callModule (NativeEventParams params) {
        // 无页面跳转的回调
        APIModule.getAPIModule(KXYAct.this, MyApplication.get()).moduleManage(params, entId, 123, new KXYCallback() {
            @Override
            public void onOKCallback(Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(new Gson().toJson(o)); // uploadFile ok
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
    // 页面跳转的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        Object obj1 = data.getParcelableArrayListExtra(MediaSelector.RESULT_DATA); // chooseImage 返回ArrayList 否则null
        Object obj2 = data.getStringExtra(MediaSelector.RESULT_DATA); // scan 返回String:https://mobile.coolcollege.cn/assets-share.html?short_link=https%3A%2F%2Fct12coolapi.coolcollege.cn%2Fenterprise-manage-api%2Fr%2F5520&eid=951057547274620933  否则null
        Object obj3 = data.getParcelableExtra(MediaSelector.RESULT_DATA); // null

        String text = null;
        if (obj1 != null) {
            text = new Gson().toJson(obj1); // chooseImage ok
        } else if (obj2 != null) {
            text = new Gson().toJson(obj2); // scan ok
        } else if (obj3 != null) {
            text = new Gson().toJson(obj3);
        }
        // 页面显示
        if (text != null) { textView.setText(text); }
    }
}
