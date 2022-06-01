package com.coolcollege.aar.selector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan_for on 2020/7/8
 */
public class OptionModel implements Parcelable {

    public int count;
    public String type;
    public int primaryColor;
    public String colorSt;
    public boolean hideCamera;
    public boolean compressed;
    public int percent;
    public int duration;

    public OptionModel() {
    }

    public OptionModel(int count, String type) {
        this.count = count;
        this.type = type;
    }

    protected OptionModel(Parcel in) {
        count = in.readInt();
        type = in.readString();
        primaryColor = in.readInt();
        percent = in.readInt();
        duration = in.readInt();
        colorSt = in.readString();
        hideCamera = in.readByte() != 0;
        compressed = in.readByte() != 0;
    }

    public static final Creator<OptionModel> CREATOR = new Creator<OptionModel>() {
        @Override
        public OptionModel createFromParcel(Parcel in) {
            return new OptionModel(in);
        }

        @Override
        public OptionModel[] newArray(int size) {
            return new OptionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(type);
        dest.writeInt(primaryColor);
        dest.writeInt(percent);
        dest.writeInt(duration);
        dest.writeString(colorSt);
        dest.writeByte((byte) (hideCamera ? 1 : 0));
        dest.writeByte((byte) (compressed ? 1 : 0));
    }
}
