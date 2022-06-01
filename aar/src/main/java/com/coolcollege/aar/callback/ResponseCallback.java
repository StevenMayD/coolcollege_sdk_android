package com.coolcollege.aar.callback;


import android.text.TextUtils;

import com.coolcollege.aar.bean.ErrorResponseBean;
import com.coolcollege.aar.bean.ResponseBean;
import com.coolcollege.aar.global.ErrorCode;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.helper.DataParseHelper;
import com.coolcollege.aar.helper.ResponseModelType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名称：
 * 类描述：  搭配retrofitHelper类使用的核心callback
 * 对网络请求回调做相应的抽取,解析数据结果为javaBean
 * <p>
 * 作者：   Even_for .
 * 日期：   2018/4/17 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public abstract class ResponseCallback<T> implements Callback<Object> {

    private Gson gson;
    private Type[] clazzTypes = new Type[1];
    private DataParseHelper parseHelper;
    private int handleResultMode = MODE_AUTO;
    public static final int MODE_AUTO = 1;
    public static final int MODE_CUSTOM = 2;

    public ResponseCallback() {
        initData(getClass(), MODE_AUTO);
    }

    public ResponseCallback(int handleMode) {
        initData(getClass(), handleMode);
    }

    public ResponseCallback(Class<?> clazz) {
        initData(clazz, MODE_AUTO);
    }

    public ResponseCallback(Type type) {
        initData(type, MODE_AUTO);
    }

    public ResponseCallback(Class<?> clazz, int handleMode) {
        initData(clazz, handleMode);
    }

    private void initData(Class<?> clazz, int handleMode) {
        parseHelper = DataParseHelper.get();
        gson = parseHelper.getGsonParser();
        clazzTypes[0] = parseHelper.getGenericType(clazz);
        handleResultMode = handleMode;
    }

    private void initData(Type type, int handleMode) {
        parseHelper = DataParseHelper.get();
        gson = parseHelper.getGsonParser();
        clazzTypes[0] = type;
        handleResultMode = handleMode;
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        if (response.isSuccessful()) {
            parseStringResult(call, response);
        } else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    String result = errorBody.string();
                    ErrorResponseBean error = getErrorResponseBean(result);
                    if (error.lang == null || TextUtils.isEmpty(error.lang)) {
                        onError(ErrorCode.getErrorMessage(error.code), ErrorCode.getErrorCode(error.code));
                    } else {
                        onError(error.message, ErrorCode.getErrorCode(error.code));
                    }
                } catch (JsonSyntaxException | IOException e) {
                    onError(ErrorCode.getErrorMessage(""), -1);
                }
            } else {
                onError(ErrorCode.getErrorMessage(""), -1);
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {
        if (!GlobalKey.REQUEST_CANCELED.equalsIgnoreCase(t.getMessage())) {
            onFail(t);
            onError(ErrorCode.getErrorMessage(""), -1);
        }
    }


    private ErrorResponseBean getErrorResponseBean(String result) {
        ErrorResponseBean errorResponseBean = gson.fromJson(result, ErrorResponseBean.class);
        if (errorResponseBean == null) errorResponseBean = new ErrorResponseBean();
        if (errorResponseBean.code == null) errorResponseBean.code = "";
        return errorResponseBean;
    }

    /**
     * 解析数据转换为与服务器约定的格式
     *
     * @param call
     * @param response
     */
    private void parseStringResult(Call<Object> call, Response<Object> response) {
        Object objResult = response.body();
        if (objResult != null) {
            String jsonResult = gson.toJson(objResult);
            try {
                //获取数据模型类型
                ResponseModelType responseModelType = parseHelper.getResponseType(ResponseBean.class, clazzTypes);
                ResponseBean<T> data = gson.fromJson(jsonResult, responseModelType);
                checkData(call, data, jsonResult);
            } catch (JsonSyntaxException e) {
                onParseException(jsonResult);
            }
        }
    }

    /**
     * 检查数据
     * 可以用来对服务器返回的状态码做处理
     *
     * @param call
     * @param data
     * @param stringResult
     */
    private void checkData(Call<Object> call, ResponseBean<T> data, String stringResult) {
        if (data != null) {
            if (handleResultMode == MODE_AUTO) {
                autoHandleResult(call, data, stringResult);
            } else if (handleResultMode == MODE_CUSTOM) {
                customHandleResult(call, data, stringResult);
            }
        } else {
            onParseException(stringResult);
        }
    }

    private void customHandleResult(Call<Object> call, ResponseBean<T> data, String stringResult) {
        customResult(data, stringResult);
    }

    private void autoHandleResult(Call<Object> call, ResponseBean<T> data, String stringResult) {
        int code = data.code;
        if (code == 0) {
            onSuccess(data, stringResult);
        } else {
            onError(data.msg, code);
        }
    }

    /**
     * gson解析数据异常
     */
    private void onParseException(String stringResult) {
        onError("数据异常", -1);
    }

    /**
     * 请求成功
     *
     * @param responseBean javaBean类型response.body
     * @param stResponse   string类型response.body
     */
    public abstract void onSuccess(ResponseBean<T> responseBean, String stResponse);

    /**
     * 请求失败
     *
     * @param t 失败异常
     */
    public void onFail(Throwable t) {

    }

    /**
     * 服务器返回错误状态
     *
     * @param msg 错误提示
     */
    public abstract void onError(String msg, int code);

    /**
     * 自定义请求结果回调
     * handleMode = MODE_CUSTOM 时才会回调
     *
     * @param responseBean
     * @param stResponse
     */
    public void customResult(ResponseBean<T> responseBean, String stResponse) {

    }

}
