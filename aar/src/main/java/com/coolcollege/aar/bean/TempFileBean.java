package com.coolcollege.aar.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by Evan_for on 2020/7/9
 */
public class TempFileBean implements Parcelable {

    public Uri uri;
    public String path;
    public String size;
    public String duration;
    public File file;

    public TempFileBean() {
    }

    public TempFileBean(Uri uri, String path, File file) {
        this.uri = uri;
        this.path = path;
        this.file = file;
    }

    protected TempFileBean(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        path = in.readString();
        size = in.readString();
        duration = in.readString();
    }

    public static final Creator<TempFileBean> CREATOR = new Creator<TempFileBean>() {
        @Override
        public TempFileBean createFromParcel(Parcel in) {
            return new TempFileBean(in);
        }

        @Override
        public TempFileBean[] newArray(int size) {
            return new TempFileBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeString(path);
        dest.writeString(size);
        dest.writeString(duration);
    }
}
