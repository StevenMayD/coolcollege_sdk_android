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
import com.coolcollege.aar.act.VideoRecordActivity;
import com.coolcollege.aar.bean.NativeEventParams;
import com.coolcollege.aar.bean.OSSConfigBean;
import com.coolcollege.aar.bean.OSSUploadFileBean;
import com.coolcollege.aar.bean.RawResponseBean;
import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.bean.UploadFileBean;
import com.coolcollege.aar.bean.VoucherBean;
import com.coolcollege.aar.callback.KXYCallback;
import com.coolcollege.aar.callback.RawResponseCallback;
import com.coolcollege.aar.callback.ShareMenuListener;
import com.coolcollege.aar.callback.VODUploadListener;
import com.coolcollege.aar.component.NativeDataProvider;
import com.coolcollege.aar.dialog.AppShareDialog;
import com.coolcollege.aar.dialog.AudioRecordDialog;
import com.coolcollege.aar.manager.VODUploadManager;
import com.coolcollege.aar.model.OSSConfigModel;
import com.coolcollege.aar.model.OSSResItem;
import com.coolcollege.aar.model.ShareMenuModel;
import com.coolcollege.aar.model.VoucherModel;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.aar.utils.GsonConfig;
import com.coolcollege.aar.utils.PermissionManager;
import com.coolcollege.aar.utils.PermissionStateListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.PostRequest;
import com.yanzhenjie.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class APIModule {

    /** OSS需要上传的文件及上传方式等 */
    private OSSUploadFileBean ossUp = null;
    /** OSS上传的地址及空间 */
    private OSSConfigBean ossRes = null;
    /** 视频上传凭证及VideoID */
    private VoucherBean voucherBean = null;

    private static APIModule apiModule = new APIModule();
    private static Activity act;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /** 录音弹窗 */
    private AudioRecordDialog audioDialog;
    private Dialog dialog;

    /** 分享弹窗 */
    private AppShareDialog shareDialog;

    public static APIModule getAPIModule (Activity activity) {
        act = activity;
        return apiModule;
    }

    /** 音频录制 */
    public void startAudioRecord (Activity activity, int maxDuration, KXYCallback callback) {
        PermissionManager.checkPermissions(activity, new PermissionStateListener() {
            @Override
            public void onGranted() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (audioDialog == null) audioDialog = new AudioRecordDialog(activity);
                        audioDialog.setMaxDuration(maxDuration);
                        audioDialog.show();

                        audioDialog.setOnRecordCompleteListener(new AudioRecordDialog.OnRecordCompleteListener() {
                            @Override
                            public void onComplete(TempFileBean tempFile) {
                                callback.onCallback(tempFile);
                            }
                        });
                    }
                }, 0);
            }

            @Override
            public void onDenied() {
                Toast.makeText(activity, "请前往手机设置打开录音相应的权限", Toast.LENGTH_LONG).show();
            }
        }, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);
    }

    /** 视频录制 */
    public void startVideoRecord (Activity activity, int maxDuration, int requestCode) {
        PermissionManager.checkPermissions(activity,new PermissionStateListener() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(activity, VideoRecordActivity.class);
                intent.putExtra("duration", maxDuration);
                activity.startActivityForResult(intent, requestCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(activity, "请前往手机设置打开录像相应的权限", Toast.LENGTH_LONG).show();
            }
        },Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);
    }

    /** 照片选择（带相机） */
    public void chooseImage (Activity activity, int maxCount, int percent, boolean compressed, int requestCode) {
        PermissionManager.checkPermissions(activity, new PermissionStateListener() {
            @Override
            public void onGranted() {
                MediaSelector.from(activity)
                        .withType(MediaSelector.TYPE_IMG)
                        .maxSelectCount(maxCount)
                        .compressed(compressed)
                        .percent(percent)
                        .forResult(requestCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(activity, "请前往手机设置打开相机相应的权限", Toast.LENGTH_LONG).show();
            }
        },Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
    }

    /** 选择视频（带录制） */
    public void chooseVideo (Activity activity, int maxDuration, int maxCount, int requestCode) {
        PermissionManager.checkPermissions(activity,new PermissionStateListener() {
            @Override
            public void onGranted() {
                MediaSelector.from(activity)
                        .withType(MediaSelector.TYPE_VIDEO)
                        .maxDuration(maxDuration)
                        .maxSelectCount(maxCount)
                        .forResult(requestCode);
            }

            @Override
            public void onDenied() {
                Toast.makeText(activity, "请前往手机设置打开录像相应的权限", Toast.LENGTH_LONG).show();
            }
        },Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.RECORD_AUDIO);
    }

    /**
     * 文件上传
     * @param activity
     * @param params
     * @param callback 回调
     */
    public void uploadFile (Activity activity, UploadFileBean params, KXYCallback callback) {
        PermissionManager.checkPermissions(activity,new PermissionStateListener() {
            @Override
            public void onGranted() {
                upFile(activity, params, callback);
            }

            @Override
            public void onDenied() {
                Toast.makeText(activity, "请前往手机设置打开上传相应的权限", Toast.LENGTH_LONG).show();
            }
        }, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
    }

    private void upFile(Activity activity, UploadFileBean params, KXYCallback callback) {
        showLoading(activity);
        File file = new File(params.filePath);
        if (!file.exists()) {
            Toast.makeText(activity, "file not exists", Toast.LENGTH_LONG).show();
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
                if (response != null && response.code() == 200) {
                    callback.onCallback(response.body());
                } else {
                    Toast.makeText(activity, response.code() + "=" + response.message(), Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                Toast.makeText(activity, response.code() + "=" + response.message(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);
                //LogUtils.e("progress.totalSize : " + progress.totalSize +"\nprogress.currentSize : " + progress.currentSize);
            }
        });
    }

    public void moduleManage (NativeEventParams params) {
        if ("OSSUploadFile".equals(params.methodName)) {
            ossUp = GsonConfig.get().getGson().fromJson(params.methodData, OSSUploadFileBean.class);
            if ("video".equals(ossUp.type)) {
                voucher();
            } else {
                ossConfig();
            }
        }

        if ("shareMenu".equals(params.methodName)) {
            ArrayList<ShareParams> data = NativeDataProvider.genericShareMenuData(params.methodData);
            buildShareDialog(data);
        }
    }

    private void buildShareDialog(ArrayList<ShareParams> data) {
        if (shareDialog == null) shareDialog = new AppShareDialog(act);
        shareDialog.initList(data, new ShareMenuListener() {
            @Override
            public void onComplete(ShareMenuModel shareMenuModel) {
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
        dialog.show();
    }

    protected void releaseChild() {
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }

    private void ossConfig(){
        OSSConfigModel ossConfigModel = new OSSConfigModel();
        ossConfigModel.OSSConfigModel(new RawResponseCallback<OSSConfigBean>() {
            @Override
            public void onSuccess(RawResponseBean<OSSConfigBean> responseBean, String stResponse) {
                ossRes = responseBean.data;
                if (ossRes.getAccessKeyId() != null) {
                    vodUpload();
                } else {
                    try {
                        JSONObject result = new JSONObject(stResponse);
                        JSONObject data = new JSONObject(result.get("data") + "");
//                        callBackError(new OSSErrorModel(String.valueOf(data.get("code")), String.valueOf(data.get("msg"))).toJson());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String msg, int code) {
//                callBackError(new OSSErrorModel(String.valueOf(code), msg).toJson());
            }
        });
    }

    private void voucher () {
        if (0 == ossUp.files.size()) {
//            callBackError(new OSSErrorModel("取视频列表错误", "前端传输视频列表为空").toJson());
            return;
        }
        VoucherModel voucherModel = new VoucherModel();
        voucherModel.VoucherModel(ossUp.files.get(0).objectKey + ".mp4", new RawResponseCallback<VoucherBean>() {
            @Override
            public void onSuccess(RawResponseBean<VoucherBean> responseBean, String stResponse) {
                voucherBean = responseBean.data;
                if (voucherBean != null) {
                    vodUpload();
                } else {
//                    callBackError(new OSSErrorModel("取凭证错误", "获取凭证失败").toJson());
                }
            }

            @Override
            public void onError(String msg, int code) {
//                callBackError(new OSSErrorModel(String.valueOf(code), msg).toJson());
            }
        });
    }

    private void vodUpload () {
        VODUploadManager.getInstance().VODUpload(act, ossRes, voucherBean, ossUp, new VODUploadListener() {
            @Override
            public void onUploadSucceed(List<OSSResItem> items) {
//                callBackResult(items);
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
//                callBackError(new OSSErrorModel(code, message).toJson());
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
            }
        });
    }

    private void showLoading (Activity activity) {
        //加载页面
        LayoutInflater inflater = activity.getLayoutInflater();// 将XMl文件内容转换成实例
        View view1 = inflater.inflate(R.layout.dialog_loading, null);
        dialog = new Dialog(activity);
        dialog.setContentView(view1);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
