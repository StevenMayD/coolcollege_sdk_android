package com.coolcollege.aar.utils;


import com.coolcollege.aar.adapter.DataTypeAdapter;
import com.coolcollege.aar.adapter.StringNullAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonConfig {

    private static GsonConfig instance = new GsonConfig();

    private GsonConfig() {
    }

    public static GsonConfig get() {
        return instance;
    }

    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(String.class, new StringNullAdapter());
        builder.registerTypeAdapterFactory(DataTypeAdapter.FACTORY);
        builder.disableHtmlEscaping();
        return builder.create();
    }

    public Gson getGsonByExpose() {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.registerTypeAdapter(String.class, new StringNullAdapter());
        builder.excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }

    public Gson getGsonByExpose(int modifiers) {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        builder.registerTypeAdapter(String.class, new StringNullAdapter());
        builder.excludeFieldsWithModifiers(modifiers);
        return builder.create();
    }
}
