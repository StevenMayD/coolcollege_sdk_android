package com.coolcollege.aar.service;


import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * 项目名称：
 * 类描述：  Retrofit的公共请求API
 * 作者：   Even_for .
 * 日期：   2018/4/17 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public interface ApiService {

    @GET()
    Call<Object> doGet(@Url String url, @QueryMap HashMap<String, String> params);

    @POST()
    Call<Object> doPostByJson(@Url String url, @Body HashMap<String, String> params);

    @POST()
    Call<Object> doPostByJsonBody(@Url String url, @Body HashMap<String, Object> params);

    @FormUrlEncoded
    @POST()
    Call<Object> doPostByForm(@Url String url, @FieldMap HashMap<String, String> params);

    @GET()
    Call<ResponseBody> doStringGet(@Url String url, @QueryMap HashMap<String, String> params);

    @POST()
    Call<ResponseBody> doStringPost(@Url String url, @Body HashMap<String, String> params);

    @Multipart
    @POST()
    Call<Object> doPostReqByForm(@Url String url, @Part ArrayList<MultipartBody.Part> params);

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
