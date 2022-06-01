package com.coolcollege.aar.bean;

import androidx.collection.ArraySet;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by Evan_for on 2020/7/8
 */
public enum MimeType {

    IMG("image"),
    VIDEO("video"),

    // ============== images ==============
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    BMP("image/x-ms-bmp"),
    WEBP("image/webp"),

    // ============== videos ==============
    MPEG("video/mpeg"),
    MP4("video/mp4"),
    QUICKTIME("video/quicktime"),
    THREEGPP("video/3gpp"),
    THREEGPP2("video/3gpp2"),
    MKV("video/mkv"),
    WEBM("video/webm"),
    TS("video/mp2ts"),
    AVI("video/avi"),
    RMVB("video/rmvb"),
    FLV("video/flv");

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private String typeName;

    MimeType(String typeName) {
        this.typeName = typeName;
    }

    private static Set<String> arraySetOf(String... suffixes) {
        return new ArraySet<>(Arrays.asList(suffixes));
    }
}
