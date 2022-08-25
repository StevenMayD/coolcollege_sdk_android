package com.coolcollege.aar.model;

import com.coolcollege.aar.callback.RawResponseCallback;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.helper.RetrofitHelper;
import com.coolcollege.aar.utils.ParameterUtils;

import java.util.HashMap;

/**
 * Created by Evan_for on 2020/8/13
 */
public class OSSConfigModel {

    public <T> void OSSConfigModel(String accessToken, RawResponseCallback<T> callback){
        HashMap<String, String> map = ParameterUtils.genericParams("access_token", accessToken);
        String url = "https://coolapi.coolcollege.cn/knowledge-api/oss/config";
        RetrofitHelper.get().doRawGet(url, map, callback);
    }

}
