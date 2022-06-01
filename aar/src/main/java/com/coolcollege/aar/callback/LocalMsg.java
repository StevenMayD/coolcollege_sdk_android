package com.coolcollege.aar.callback;

import java.util.HashMap;

public class LocalMsg {

    private String action;

    private HashMap<String, Object> msg = new HashMap<>();

    public LocalMsg(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public LocalMsg put(String key, Object value) {
        msg.put(key, value);
        return this;
    }

    public Object get(String key) {
        return msg.get(key);
    }

}
