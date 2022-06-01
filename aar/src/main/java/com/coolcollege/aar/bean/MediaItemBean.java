package com.coolcollege.aar.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan_for on 2020/7/8
 */
public class MediaItemBean implements Parcelable {

    public String type;
    public String duration;
    public String path;
    public String size;

    public boolean isChecked;
    public boolean isShowCheck;

    public MediaItemBean(){}

    public MediaItemBean(String type, String duration, String path, String size) {
        this.type = type;
        this.duration = duration;
        this.path = path;
        this.size = size;
    }

    protected MediaItemBean(Parcel in) {
        type = in.readString();
        duration = in.readString();
        path = in.readString();
        size = in.readString();
        isChecked = in.readByte() != 0;
        isShowCheck = in.readByte() != 0;
    }

    public static final Creator<MediaItemBean> CREATOR = new Creator<MediaItemBean>() {
        @Override
        public MediaItemBean createFromParcel(Parcel in) {
            return new MediaItemBean(in);
        }

        @Override
        public MediaItemBean[] newArray(int size) {
            return new MediaItemBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(duration);
        dest.writeString(path);
        dest.writeString(size);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isShowCheck ? 1 : 0));
    }
}
