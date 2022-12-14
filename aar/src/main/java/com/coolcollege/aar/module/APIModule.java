package com.coolcollege.aar.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.coolcollege.aar.R;
import com.coolcollege.aar.act.QrCodeScanActivity;
import com.coolcollege.aar.act.VideoRecordActivity;
import com.coolcollege.aar.bean.AudioRecordBean;
import com.coolcollege.aar.bean.NativeEventParams;
import com.coolcollege.aar.bean.OKGOBean;
import com.coolcollege.aar.bean.OSSConfigBean;
import com.coolcollege.aar.bean.OSSUploadFileBean;
import com.coolcollege.aar.bean.PickImgBean;
import com.coolcollege.aar.bean.PickVideoBean;
import com.coolcollege.aar.bean.RawResponseBean;
import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.bean.UploadFileBean;
import com.coolcollege.aar.bean.VideoRecordBean;
import com.coolcollege.aar.bean.VoucherBean;
import com.coolcollege.aar.callback.ILocationCallback;
import com.coolcollege.aar.callback.KXYCallback;
import com.coolcollege.aar.callback.RawResponseCallback;
import com.coolcollege.aar.callback.ShareMenuListener;
import com.coolcollege.aar.callback.VODUploadListener;
import com.coolcollege.aar.component.NativeDataProvider;
import com.coolcollege.aar.dialog.AppShareDialog;
import com.coolcollege.aar.dialog.AudioRecordDialog;
import com.coolcollege.aar.location.LocationManager;
import com.coolcollege.aar.manager.VODUploadManager;
import com.coolcollege.aar.model.ErrorModel;
import com.coolcollege.aar.model.OSSConfigModel;
import com.coolcollege.aar.model.OSSResItem;
import com.coolcollege.aar.model.ShareMenuModel;
import com.coolcollege.aar.model.VoucherModel;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.aar.utils.GPSUtils;
import com.coolcollege.aar.utils.GsonConfig;
import com.coolcollege.aar.utils.PermissionManager;
import com.coolcollege.aar.utils.PermissionStateListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class APIModule {

    /** OSS??????????????????????????????????????? */
    private OSSUploadFileBean ossUp = null;
    /** OSS???????????????????????? */
    private OSSConfigBean ossRes = null;
    /** ?????????????????????VideoID */
    private VoucherBean voucherBean = null;

    private static APIModule apiModule = new APIModule();
    private static Activity act;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /** ???????????? */
    private AudioRecordDialog audioDialog;
    private Dialog dialog;

    /** ???????????? */
    private AppShareDialog shareDialog;

    private String acToken;
    private String entId;
    private KXYCallback kxyCallback;
    private int reqCode;

    public static APIModule getAPIModule (Activity activity) {
        act = activity;
        return apiModule;
    }

    /** ????????? */
    public void moduleManage (NativeEventParams params, String enterpriseId, int requestCode, KXYCallback callback) {
        entId = enterpriseId;
        kxyCallback = callback;
        reqCode = requestCode;
        if ("startAudioRecord".equals(params.methodName)) { // ????????????
            AudioRecordBean audio = NativeDataProvider.parseData(params.methodData, AudioRecordBean.class);
            startAudioRecord(audio);
        } else if ("startVideoRecord".equals(params.methodName)) { // ????????????
            VideoRecordBean video = NativeDataProvider.parseData(params.methodData, VideoRecordBean.class);
            startVideoRecord(video);
        } else if ("chooseImage".equals(params.methodName)) { // ?????????????????????/?????????
            PickImgBean imgData = NativeDataProvider.parseData(params.methodData, PickImgBean.class);
            chooseImage(imgData);
        } else if ("chooseVideo".equals(params.methodName)) { // ?????????????????????/?????????
            PickVideoBean videoData = NativeDataProvider.parseData(params.methodData, PickVideoBean.class);
            chooseVideo(videoData);
        } else if ("uploadFile".equals(params.methodName)) { // ??????????????????
            UploadFileBean upload = NativeDataProvider.parseData(params.methodData, UploadFileBean.class);
            uploadFile(upload);
        } else if ("OSSUploadFile".equals(params.methodName)) { // ????????????
            ossUp = GsonConfig.get().getGson().fromJson(params.methodData, OSSUploadFileBean.class);
            acToken = ossUp.accessToken;
            if (acToken == null) {
                acToken = " ";
            }
            if ("video".equals(ossUp.type)) {
                voucher();
            } else {
                ossConfig();
            }
        } else if ("shareMenu".equals(params.methodName)) { // ??????
            ArrayList<ShareParams> data = NativeDataProvider.genericShareMenuData(params.methodData);
            buildShareDialog(data);
        } else if ("scan".equals(params.methodName)) { // ??????
            scanView();
        } else if ("getLocation".equals(params.methodName)) { // ??????
            getLocation();
        }
    }

    /** ???????????? */
    private void startAudioRecord (AudioRecordBean audio) {
        PermissionManager.checkPermissions(act, new PermissionStateListener() {
            @Override
            public void onGranted() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (audioDialog == null) audioDialog = new AudioRecordDialog(act);
                        audioDialog.setMaxDuration(audio.maxDuration);
                        audioDialog.show();

                        audioDialog.setOnRecordCompleteListener(new AudioRecordDialog.OnRecordCompleteListener() {
                            @Override
                            public void onComplete(TempFileBean tempFile) {
                                kxyCallback.onOKCallback(tempFile);
                            }
                        });
                    }
                }, 0);
            }

            @Override
            public void onDenied() {
                Toast.makeText(act, "????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
            }
        }, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);
    }

    /** ???????????? */
    private void startVideoRecord (VideoRecordBean video) {
        PermissionManager.checkPermissions(act,new PermissionStateListener() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(act, VideoRecordActivity.class);
                intent.putExtra("duration", video.maxDuration);
                act.startActivityForResult(intent, reqCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(act, "????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
            }
        },Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);
    }

    /** ??????????????????????????? */
    private void chooseImage (PickImgBean imgData) {
        PermissionManager.checkPermissions(act, new PermissionStateListener() {
            @Override
            public void onGranted() {
                MediaSelector.from(act)
                        .withType(MediaSelector.TYPE_IMG)
                        .maxSelectCount(imgData.count)
                        .compressed(imgData.compressed)
                        .percent(imgData.percent)
                        .forResult(reqCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(act, "????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
            }
        },Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
    }

    /** ??????????????????????????? */
    private void chooseVideo (PickVideoBean videoData) {
        PermissionManager.checkPermissions(act,new PermissionStateListener() {
            @Override
            public void onGranted() {
                MediaSelector.from(act)
                        .withType(MediaSelector.TYPE_VIDEO)
                        .maxDuration(videoData.maxDuration)
                        .maxSelectCount(videoData.count)
                        .forResult(reqCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(act, "????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
            }
        },Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);
    }

    /** ?????? */
    private void scanView() {
        PermissionManager.checkPermissions(act, new PermissionStateListener() {
            @Override
            public void onGranted() {
                String callbackId = UUID.randomUUID().toString();
                Intent intent = new Intent(act, QrCodeScanActivity.class);
                intent.putExtra("CALLBACK_ID", callbackId);
                act.startActivityForResult(intent, reqCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(act, "??????????????????????????????????????????", Toast.LENGTH_LONG).show();
            }
        }, Permission.CAMERA);
    }
    /** ?????? */
    private void getLocation() {
        // ????????????GPS??????
        if (GPSUtils.isOPen(act.getApplicationContext())) {
            // ??????app??????????????????
            PermissionManager.checkPermissions(act, new PermissionStateListener() {
                        @Override
                        public void onGranted() {
                            startLocation();
                        }

                        @Override
                        public void onDenied() {
                            HashMap locationInfo = new HashMap();
                            locationInfo.put("error", "?????????????????????");
                            kxyCallback.onErrorCallback(locationInfo);
                            Toast.makeText(act, "???????????????????????????????????????", Toast.LENGTH_LONG).show();
                        }
                    }, Permission.ACCESS_FINE_LOCATION , Permission.ACCESS_COARSE_LOCATION);
        } else {
            HashMap locationInfo = new HashMap();
            locationInfo.put("error", "?????????????????????");
            kxyCallback.onErrorCallback(locationInfo);
            GPSUtils.openGPS(act);
        }
    }

    private void startLocation() {
        // ???????????????????????????
        LocationManager locationManager = LocationManager.shareLocationManager(act);
        // ??????????????????????????????
        locationManager.setLocationCallback(new ILocationCallback() {
            @Override
            public void onSuccessCallback(HashMap callback) {
                // ???????????????????????????????????????
                kxyCallback.onOKCallback(callback);
            }

            @Override
            public void onErrorCallback(HashMap callback) {
                kxyCallback.onErrorCallback(callback);
            }
        });
        // ?????????????????????????????????
        locationManager.getLocationInfo();
    }

    /**
     * ????????????
     * @param params
     */
    private void uploadFile (UploadFileBean params) {
        PermissionManager.checkPermissions(act, new PermissionStateListener() {
            @Override
            public void onGranted() {
                upFile(params);
            }

            @Override
            public void onDenied() {
                Toast.makeText(act, "????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
            }
        }, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
    }

    private void upFile(UploadFileBean params) {
        showLoading(act);
        File file = new File(params.filePath);
        if (!file.exists()) {
            kxyCallback.onErrorCallback(new ErrorModel(true, "???????????????").toJson());
            Toast.makeText(act, "file not exists", Toast.LENGTH_LONG).show();
            dialog.dismiss();
            return;
        }

        PostRequest<String> postRequest = OkGo.<String>post(params.url);

        if (params.formData != null) {
            for (String key : params.formData.keySet()) {
                postRequest.params(key, params.formData.get(key));
            }
        }
        if (params.header != null) {
            for (String key : params.header.keySet()) {
                postRequest.headers(key, params.header.get(key));
            }
        }
        postRequest.isMultipart(true);
        postRequest.params(params.name, file);

        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                OKGOBean okgoBean = NativeDataProvider.parseData(response.body(), OKGOBean.class);
                if (okgoBean != null && okgoBean.getCode() == 0) {
                    kxyCallback.onOKCallback(response.body());
                } else {
                    kxyCallback.onErrorCallback(new ErrorModel(true, okgoBean.getMsg()).toJson());
                    Toast.makeText(act, response.code() + "=" + response.message(), Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                kxyCallback.onErrorCallback(new ErrorModel(true, response.message()).toJson());
                Toast.makeText(act, response.code() + "=" + response.message(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);
                //LogUtils.e("progress.totalSize : " + progress.totalSize +"\nprogress.currentSize : " + progress.currentSize);
            }
        });
    }

    /** ?????? */
    private void buildShareDialog(ArrayList<ShareParams> data) {
        if (shareDialog == null) shareDialog = new AppShareDialog(act);
        shareDialog.initList(data, new ShareMenuListener() {
            @Override
            public void onComplete(ShareMenuModel shareMenuModel) {
                kxyCallback.onOKCallback(new Gson().toJson(shareMenuModel));
                releaseChild();
            }

            @Override
            public void onError(ShareMenuModel shareMenuModel) {
                releaseChild();
            }

            @Override
            public void onCancel(ShareMenuModel shareMenuModel) {
                releaseChild();
            }
        });
        shareDialog.show();
    }

    protected void releaseChild() {
        if(shareDialog != null){
            shareDialog.dismiss();
            shareDialog = null;
        }
    }

    private void ossConfig(){
        OSSConfigModel ossConfigModel = new OSSConfigModel();
        ossConfigModel.OSSConfigModel(acToken ,new RawResponseCallback<OSSConfigBean>() {
            @Override
            public void onSuccess(RawResponseBean<OSSConfigBean> responseBean, String stResponse) {
                ossRes = responseBean.data;
                if (ossRes.getAccessKeyId() != null) {
                    vodUpload();
                } else {
                    try {
                        JSONObject result = new JSONObject(stResponse);
                        JSONObject data = new JSONObject(result.get("data") + "");
                        kxyCallback.onErrorCallback(new ErrorModel(true, "??????????????????").toJson());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
                kxyCallback.onErrorCallback(new ErrorModel(true, msg).toJson());
            }
        });
    }

    private void voucher () {
        if (0 == ossUp.files.size()) {
            kxyCallback.onErrorCallback(new ErrorModel(true, "??????????????????????????????").toJson());
            return;
        }
        VoucherModel voucherModel = new VoucherModel();
        voucherModel.VoucherModel(acToken, entId, ossUp.files.get(0).objectKey + ".mp4", new RawResponseCallback<VoucherBean>() {
            @Override
            public void onSuccess(RawResponseBean<VoucherBean> responseBean, String stResponse) {
                voucherBean = responseBean.data;
                if (voucherBean.getVideoId() != null) {
                    vodUpload();
                } else {
                    kxyCallback.onErrorCallback(new ErrorModel(true, "??????????????????").toJson());
                }
            }

            @Override
            public void onError(String msg, int code) {
                kxyCallback.onErrorCallback(new ErrorModel(true, msg).toJson());
            }
        });
    }

    private void vodUpload () {
        VODUploadManager.getInstance().VODUpload(act, ossRes, voucherBean, ossUp, new VODUploadListener() {
            @Override
            public void onUploadSucceed(List<OSSResItem> items) {
                kxyCallback.onOKCallback(items);
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                kxyCallback.onErrorCallback(new ErrorModel(true, message).toJson());
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
            }
        });
    }

    private void showLoading (Activity activity) {
        //????????????
        LayoutInflater inflater = activity.getLayoutInflater();// ???XMl???????????????????????????
        View view1 = inflater.inflate(R.layout.k_dialog_loading, null);
        dialog = new Dialog(activity);
        dialog.setContentView(view1);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);// ??????????????????Dialog?????????
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
