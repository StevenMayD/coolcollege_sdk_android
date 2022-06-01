package com.coolcollege.aar.helper;


import com.blankj.utilcode.util.LogUtils;
import com.coolcollege.aar.bean.RequestLogBean;
import com.coolcollege.aar.utils.ParameterUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * 对所有请求数据做统一添加请求头和其它处理
 *
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HashMap<String, String> params = ParameterUtils.getHeaders();
        Headers headers = buildHeaders(request, params);
        Request newRequest = request.newBuilder().headers(headers).build();
        return chain.proceed(newRequest);
    }

    private Headers buildHeaders(Request request, HashMap<String, String> header) {
        Headers.Builder builder = request.headers().newBuilder();
        for (String key : header.keySet()) {
            String value = header.get(key);
            if (value != null) {
                builder.add(key, value);
            }
        }
        return builder.build();
    }

    private String buildRequestInfo2Json(String url, HashMap<String, String> params) {
        RequestLogBean bean = new RequestLogBean(url, params);
        return bean.buildInfo2Json();
    }

    private void printRequestLog(Request request, HashMap<String, String> params) {
        HttpUrl url = request.url();
        String stUrl = url.toString();
        String info = buildRequestInfo2Json(stUrl, params);
        LogUtils.e("interceptor", "request : " + info);
    }
}
