package com.coolcollege.aar.bean;

public class SystemInfo {
    private String model;
    private int windowWidth;
    private int windowHeight;
    private String system;
    private String platform;
    private int screenWidth;
    private int screenHeight;
    private String brand;

    public SystemInfo(String model, int windowWidth, int windowHeight, String system, String platform, int screenWidth, int screenHeight, String brand) {
        this.model = model;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.system = system;
        this.platform = platform;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.brand = brand;
    }
}
