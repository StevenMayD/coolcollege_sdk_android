package com.coolcollege.aar.callback;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名称：
 * 类描述：  String类型response
 * 作者：   Even_for .
 * 日期：   2018/4/18 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public abstract class StringResultCallback implements Callback<ResponseBody> {

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if (response.isSuccessful()) {
            try {
                String body = response.body().string();
                onSuccess(body);
            } catch (IOException e) {
                onError(null);
            }
        } else {
            onError(null);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        onError(t);
    }

    public abstract void onError(Throwable t);

    public abstract void onSuccess(String stringResult);

}
