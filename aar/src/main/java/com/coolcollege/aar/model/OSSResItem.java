package com.coolcollege.aar.model;

import com.google.gson.Gson;

public class OSSResItem {
    private String path;
    private String videoId;
    private String name;
    private String size;

    public OSSResItem(String path, String videoId, String name, String size) {
        this.path = path;
        this.videoId = videoId;
        this.name = name;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
}
