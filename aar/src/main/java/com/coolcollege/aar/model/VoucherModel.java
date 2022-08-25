package com.coolcollege.aar.model;

import com.coolcollege.aar.callback.RawResponseCallback;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.helper.RetrofitHelper;
import com.coolcollege.aar.utils.ParameterUtils;

import java.util.HashMap;

/**
 * Created by Evan_for on 2020/8/13
 */
public class VoucherModel {

    public <T> void VoucherModel(String accessToken, String enterprise, String file_name, RawResponseCallback<T> callback){
        HashMap<String, String> map = ParameterUtils.genericParams("file_name", file_name);
        String url = "https://coolapi.coolcollege.cn/knowledge-api/v2/"+ enterprise +"/ticket?access_token=" + accessToken;
        RetrofitHelper.get().doRawPostByJson(url, map, callback);
    }

}
