package com.coolcollege.aar.module;


public class NotchInfoModel {

    private boolean hasNotch;
    private int width;
    private int height;

    public NotchInfoModel() {
    }

    public NotchInfoModel(boolean hasNotch, int width, int height) {
        this.hasNotch = hasNotch;
        this.width = width;
        this.height = height;
    }

    public boolean isHasNotch() {
        return hasNotch;
    }

    public void setHasNotch(boolean hasNotch) {
        this.hasNotch = hasNotch;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "NotchInfoModel{" +
                "hasNotch=" + hasNotch +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
