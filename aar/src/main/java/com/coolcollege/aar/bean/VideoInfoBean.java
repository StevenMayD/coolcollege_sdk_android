package com.coolcollege.aar.bean;

/**
 * Created by Evan_for on 2020/7/23
 */
public class VideoInfoBean {

    public int width;
    public int height;
    public String widthAndHeight;
    public int bitrate;

    public VideoInfoBean() {
    }

    public VideoInfoBean(int width, int height, int bitrate, String widthAndHeight) {
        this.width = width;
        this.height = height;
        this.bitrate = bitrate;
        this.widthAndHeight = widthAndHeight;
    }
}
