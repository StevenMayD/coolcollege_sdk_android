package com.coolcollege.aar.component;

import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.utils.GsonConfig;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

public class NativeDataProvider {

    public static ArrayList<ShareParams> genericShareMenuData(String data) {
        ShareParams shareParams = GsonConfig.get().getGson().fromJson(data, ShareParams.class);
        ArrayList<ShareParams> list = new ArrayList<>();

        if (shareParams.share_list == null) {
            list.add(new ShareParams(2, shareParams.url, shareParams.title, shareParams.content, shareParams.logo));
            list.add(new ShareParams(1, shareParams.url, shareParams.title, shareParams.content, shareParams.logo));
            list.add(new ShareParams(3, shareParams.url, shareParams.title, shareParams.content, shareParams.logo));
        } else {
            for (Integer type : shareParams.share_list) {
                list.add(new ShareParams(type, shareParams.url, shareParams.title, shareParams.content, shareParams.logo));
            }
        }

        return list;
    }

    public static <T> T parseData(String data, Class<T> clazz) {
        return GsonConfig.get().getGson().fromJson(data, clazz);
    }
}
