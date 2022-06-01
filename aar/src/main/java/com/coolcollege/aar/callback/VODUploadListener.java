package com.coolcollege.aar.callback;


import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.coolcollege.aar.model.OSSResItem;

import java.util.List;

public interface VODUploadListener {
    void onUploadSucceed(List<OSSResItem> items);
    void onUploadFailed(UploadFileInfo info, String code, String message);
    void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize);
}
