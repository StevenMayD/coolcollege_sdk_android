package com.coolcollege.aar.helper;



import com.coolcollege.aar.callback.RawResponseCallback;
import com.coolcollege.aar.callback.ResponseCallback;
import com.coolcollege.aar.callback.StringResultCallback;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.service.ApiService;
import com.coolcollege.aar.utils.ParameterUtils;
import com.coolcollege.aar.utils.ReleaseManager;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * 项目名称：
 * 类描述：  retrofit核心调用类
 * 初始化retrofit相关逻辑
 * 已在此做好了简单请求包装
 * <p>
 * <p>
 * 作者：   Even_for .
 * 日期：   2018/4/17 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public class RetrofitHelper {

    private static final String TAG = "RetrofitHelper";
    private static RetrofitHelper instance;
    private ApiService api;
    private boolean isInit;
    private OkHttpHelper okHttpHelper;
    private Retrofit.Builder retrofitBuilder;
    private ReleaseManager releaseManager;

    private RetrofitHelper() {
        okHttpHelper = OkHttpHelper.get();
        retrofitBuilder = new Retrofit.Builder();
    }

    public static RetrofitHelper get() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    public void init(String baseUrl) {
        if (isInit) {
            throw new RuntimeException("Retrofit was already initialized");
        } else {
            retrofitBuilder.client(okHttpHelper.initOkHttpClient());
            retrofitBuilder.baseUrl(baseUrl);
            //retrofitBuilder.addConverterFactory(GsonConverterFactory.create(GsonConfig.get().getGson()));

            //使用jackSon转换器解析数据
            //放弃使用gson的原因是gson在解析数据时,对于number类型的数据没有
            //细分整数类型还是浮点类型,统一返回double导致接收的数据本身为整数时
            //会自动转换成小数,造成不必要的麻烦
            retrofitBuilder.addConverterFactory(JacksonConverterFactory.create());
            Retrofit retrofit = retrofitBuilder.build();
            api = retrofit.create(ApiService.class);
            releaseManager = new ReleaseManager();
            isInit = true;
        }
    }

    public ReleaseManager getReleaseManager() {
        return releaseManager;
    }

    public <T> Call<Object> doGet(String url, HashMap<String, String> params, ResponseCallback<T> callback) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<Object> call = api.doGet(url, params);
        call.enqueue(callback);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doRawGet(String url, HashMap<String, String> params, RawResponseCallback<T> callback) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<Object> call = api.doGet(url, params);
        call.enqueue(callback);
        releaseManager.addCalls(call);
        return call;
    }


//    private void addGlobalParams(HashMap<String, String> params) {
//        if (!params.containsKey(Global.TOKEN_KEY)) {
//            params.put(Global.TOKEN_KEY, MemCacheUtils.get().getToken());
//        }
//        if (!params.containsKey(GlobalKey.USER_ID_KEY)) {
//            params.put(GlobalKey.USER_ID_KEY, MemCacheUtils.get().getUserId());
//        }
//        if (!params.containsKey(GlobalKey.ENTERPRISE_ID_KEY)) {
//            params.put(GlobalKey.ENTERPRISE_ID_KEY, MemCacheUtils.get().getEnterpriseId());
//        }
//    }

//    private void addGlobalParamsBody(HashMap<String, Object> params) {
//        if (!params.containsKey(Global.TOKEN_KEY)) {
//            params.put(Global.TOKEN_KEY, MemCacheUtils.get().getToken());
//        }
//        if (!params.containsKey(GlobalKey.USER_ID_KEY)) {
//            params.put(GlobalKey.USER_ID_KEY, MemCacheUtils.get().getUserId());
//        }
//        if (!params.containsKey(GlobalKey.ENTERPRISE_ID_KEY)) {
//            params.put(GlobalKey.ENTERPRISE_ID_KEY, MemCacheUtils.get().getEnterpriseId());
//        }
//    }

//    private void addFormReqGlobalParams(ArrayList<MultipartBody.Part> params) {
//        params.add(ParameterUtils.genericTextParams(Global.TOKEN_KEY, MemCacheUtils.get().getToken()));
//        params.add(ParameterUtils.genericTextParams(GlobalKey.USER_ID_KEY, MemCacheUtils.get().getUserId()));
//        params.add(ParameterUtils.genericTextParams(GlobalKey.ENTERPRISE_ID_KEY, MemCacheUtils.get().getEnterpriseId()));
//    }

    public void doStringGet(String url, HashMap<String, String> params, StringResultCallback callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<ResponseBody> call = api.doStringGet(url, params);
        call.enqueue(callBack);
    }

    /**
     * post 请求
     *
     * @param url      请求的资源路径
     *                 ** 使用@path时,url只能传递路径名称
     *                 不能是携带[/]的全路径地址,不然会将
     *                 [/]转义为16进制,如果一定要使用资源
     *                 路径,只能替换使用@url
     *                 **
     * @param params   请求参数
     * @param callBack 回调接口
     * @param <T>      response.body解析后对应的javaBean
     */
    public <T> Call<Object> doPostByJson(String url, HashMap<String, String> params, ResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<Object> call = api.doPostByJson(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doPostByJsonBody(String url, HashMap<String, Object> params, ResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParamsBody(params);
        Call<Object> call = api.doPostByJsonBody(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doRawPostByJson(String url, HashMap<String, String> params, RawResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<Object> call = api.doPostByJson(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doRawPostByJsonBody(String url, HashMap<String, Object> params, RawResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParamsBody(params);
        Call<Object> call = api.doPostByJsonBody(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }


    public <T> Call<Object> doPostByForm(String url, HashMap<String, String> params, ResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<Object> call = api.doPostByForm(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doPostReqByForm(String url, ArrayList<MultipartBody.Part> params, ResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addFormReqGlobalParams(params);
        Call<Object> call = api.doPostReqByForm(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doRawPostByForm(String url, HashMap<String, String> params, RawResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<Object> call = api.doPostByForm(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public <T> Call<Object> doRawPostReqByForm(String url, ArrayList<MultipartBody.Part> params, RawResponseCallback<T> callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addFormReqGlobalParams(params);
        Call<Object> call = api.doPostReqByForm(url, params);
        call.enqueue(callBack);
        releaseManager.addCalls(call);
        return call;
    }

    public void doStringPost(String url, HashMap<String, String> params, StringResultCallback callBack) {
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
//        addGlobalParams(params);
        Call<ResponseBody> call = api.doStringPost(url, params);
        call.enqueue(callBack);
    }

    public <T> Call<ResponseBody> downloadFile(String url, Callback<ResponseBody> callback){
        if (!isInit) {
            throw new RuntimeException("call init() first to initial RetrofitHelper");
        }
        Call<ResponseBody> call = api.downloadFile(url);
        call.enqueue(callback);
        releaseManager.addCalls(call);
        return call;
    }
}
