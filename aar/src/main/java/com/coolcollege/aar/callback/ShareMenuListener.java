package com.coolcollege.aar.callback;


import com.coolcollege.aar.model.ShareMenuModel;

public interface ShareMenuListener {
    void onComplete(ShareMenuModel shareMenuModel);
    void onError(ShareMenuModel shareMenuModel);
    void onCancel(ShareMenuModel shareMenuModel);
}
