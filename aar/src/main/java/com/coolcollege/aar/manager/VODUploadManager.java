package com.coolcollege.aar.manager;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.coolcollege.aar.R;
import com.coolcollege.aar.bean.OSSConfigBean;
import com.coolcollege.aar.bean.OSSFileBean;
import com.coolcollege.aar.bean.OSSUploadFileBean;
import com.coolcollege.aar.bean.VoucherBean;
import com.coolcollege.aar.callback.VODUploadListener;
import com.coolcollege.aar.model.OSSResItem;

import java.util.ArrayList;
import java.util.List;

public class VODUploadManager {
    private static final VODUploadManager instance = new VODUploadManager();
    /** 弹框*/
    private Dialog dialog;
    /** OSS上传文件大小 */
    private String ossSize = "-1";
    /** OSS上传数量 */
    private int ossUpLoadNum = 0;
    private List<OSSResItem> items;
    private String co;
    private String mage;

    private VODUploadClient uploader = null;

    private ProgressBar tv_dl_pBar;
    private TextView tv_dl_num;

    public static VODUploadManager getInstance() {
        return instance;
    }

    public void VODUpload (Activity act, OSSConfigBean ossRes, VoucherBean voucherBean, OSSUploadFileBean uploadFile, VODUploadListener listener) {
        items = new ArrayList<>();
        ossUpLoadNum = 0;
        showProgressDialog(act);
        uploader = new VODUploadClientImpl(act.getApplicationContext());
        VODUploadCallback callback = new VODUploadCallback() {
            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                super.onUploadSucceed(info);

                if ("video".equals(uploadFile.type)) {
                    items.add(new OSSResItem("", voucherBean.getVideoId(), info.getVodInfo().getTitle(), ossSize));
                } else {
                    StringBuilder sb = new StringBuilder(ossRes.getEndpoint());
                    sb.insert(8, ossRes.getBucket() + ".");
                    sb.append("/" + info.getObject());
                    items.add(new OSSResItem(sb.toString(), "", info.getObject(), ossSize));
                }
                ossSize = "-1";
                if (ossUpLoadNum == uploadFile.files.size() - 1) {
                    if (listener != null) listener.onUploadSucceed(items);
                    dialog.dismiss();
                    return;
                }

                if (ossUpLoadNum < uploadFile.files.size()) {
                    ++ossUpLoadNum;
                }
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                super.onUploadFailed(info, code, message);
                ossSize = "-1";
                co = code;
                mage = message;
                if (ossUpLoadNum == uploadFile.files.size() - 1) {
                    dialog.dismiss();
                    return;
                }
                if (0 == items.size()) {
                    if (listener != null) listener.onUploadFailed(info, co, mage);
                }
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
                super.onUploadProgress(info, uploadedSize, totalSize);
                if (listener != null) listener.onUploadProgress(info, uploadedSize, totalSize);

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (1 < uploadFile.files.size()) {
                            if ("-1".equals(ossSize)){
                                int pro = (int) ((double)(ossUpLoadNum + 1) / (double)uploadFile.files.size() * 100);
                                tv_dl_pBar.setProgress(pro);
                                tv_dl_num.setText(pro + "%");
                            }
                        } else {
                            int pro = (int) ((double)uploadedSize / (double)totalSize * 100);
                            tv_dl_pBar.setProgress(pro);
                            tv_dl_num.setText(pro + "%");
                        }
                    }
                });

                if ("-1".equals(ossSize)){
                    ossSize = String.valueOf(totalSize);
                }
            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                super.onUploadStarted(uploadFileInfo);
                if ("video".equals(uploadFile.type)) {
                    uploader.setUploadAuthAndAddress(uploadFileInfo, voucherBean.getUploadAuth(), voucherBean.getUploadAddress());
                }
            }
        };
        if ("video".equals(uploadFile.type)) {
            uploader.init(callback);
        } else {
            uploader.init(ossRes.getAccessKeyId(), ossRes.getAccessKeySecret(), callback);
        }

        VodInfo vodInfo = new VodInfo();

        for (OSSFileBean file: uploadFile.files) {
            if ("video".equals(uploadFile.type)) {
                vodInfo.setTitle(file.objectKey);
                uploader.addFile(file.filePath, vodInfo);
            } else {
                uploader.addFile(file.filePath, ossRes.getEndpoint(), ossRes.getBucket(), file.objectKey, vodInfo);
            }
        }

        uploader.start();
    }

    private void showProgressDialog(Activity act){
        //加载页面
        LayoutInflater inflater = act.getLayoutInflater();// 将XMl文件内容转换成实例
        View view1 = inflater.inflate(R.layout.act_dialog, null);

        tv_dl_pBar = view1.findViewById(R.id.tv_dl_pBar);
        tv_dl_pBar.setMax(100);

        tv_dl_num = view1.findViewById(R.id.tv_dl_num);
        tv_dl_num.setText("0%");

        TextView tv_dl_cancel = view1.findViewById(R.id.tv_dl_cancel);
        tv_dl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploader.stop();
                uploader.clearFiles();
                dialog.dismiss();
            }
        });

        dialog = new Dialog(act);
        dialog.setContentView(view1);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Window dialogWindow = dialog.getWindow();
        WindowManager m = act.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
    }
}
