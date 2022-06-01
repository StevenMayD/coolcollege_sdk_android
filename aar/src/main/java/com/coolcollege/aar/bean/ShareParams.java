package com.coolcollege.aar.bean;

import java.util.ArrayList;

/**
 * Created by Evan_for on 2020/8/18
 */
public class ShareParams {

    public int type;
    public String url;
    public String title;
    public String content;
    public String logo;
    public ArrayList<Integer> share_list;

    public ShareParams() {
    }

    public ShareParams(int type, String url, String title, String content, String logo) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.content = content;
        this.logo = logo;
    }
}
