package com.coolcollege.aar.model;

public class ShareMenuModel {
    public String platformType;
    public String shareState;

    /**
     *
     * @param platformType 平台类型
     *          wechatSession： 微信好友
     *          wechatTimeline：微信朋友圈
     *          dingTalk ：钉钉
     * @param shareState 分享状态
     * success：成功
     * fail：失败
     * cancel：取消
     */
    public ShareMenuModel(String platformType, String shareState) {
        this.platformType = platformType;
        this.shareState = shareState;
    }
}
