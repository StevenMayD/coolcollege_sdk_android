package com.coolcollege.aar.bean;



import com.coolcollege.aar.utils.GsonConfig;

import java.util.HashMap;

public class RequestLogBean {

    public String url;
    public HashMap<String, String> params;

    public RequestLogBean() {
    }

    public RequestLogBean(String url, HashMap<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public String buildInfo2Json(){
        return GsonConfig.get().getGson().toJson(this);
    }
}
