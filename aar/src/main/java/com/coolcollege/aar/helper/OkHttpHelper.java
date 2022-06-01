package com.coolcollege.aar.helper;


import com.blankj.utilcode.util.LogUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * 项目名称：
 * 类描述：  Retrofit所需的okHttpClient帮助类
 * 作者：   Even_for .
 * 日期：   2018/4/17 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public class OkHttpHelper {

    private static OkHttpHelper instance = new OkHttpHelper();
    private static final int READ_TIME = 8;
    private static final int WRITE_TIME = 8;
    private static final int CONNECT_TIME = 8;

    private OkHttpHelper() {
    }

    public static OkHttpHelper get() {
        return instance;
    }

    public OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new RequestInterceptor());
        builder.readTimeout(READ_TIME, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME, TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        /*builder.hostnameVerifier(new MyHostVerifier());
        try {
            SSLContext tls = SSLContext.getInstance("TLS");
            tls.init(null,new TrustManager[]{new MyTrustManager()},new SecureRandom());
            builder.sslSocketFactory(tls.getSocketFactory(),new MyTrustManager());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }*/
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                @Override
//                public void log(String message) {
//                    //printData(message);
//                }
//            }).setLevel(HttpLoggingInterceptor.Level.BODY));
//        }
        return builder.build();
    }

    private void printData(String message) {
        String fileDesc = "Content-Disposition: form-data; name=\"file\"";
        int fileStartIndex = message.indexOf(fileDesc) + fileDesc.length();
        if (message.contains(fileDesc)) {
            LogUtils.e("log === : " + message.length());
            String startSub = message.substring(0, fileStartIndex);
            String fileContentSub = message.substring(fileStartIndex);
            String endSub = fileContentSub.substring(fileContentSub.indexOf("Content-Disposition"));
            LogUtils.e("log === : startSub : " + startSub + "\n\n" + endSub);
        } else {
            if (message.contains("Content-Length")) {
                String[] split = message.split(":");
                LogUtils.e("log === : split : " + Arrays.toString(split));
                if (split.length > 1) {
                    String size = split[1].replaceAll(" ", "");
                    try {
                        int iSize = Integer.parseInt(size);
                        if (iSize < 50000000) {
                            LogUtils.e("log === : " + message);
                        }
                    } catch (NumberFormatException e) {
                        try {
                            long lSize = Long.parseLong(size);
                            if (lSize < 60000000) {
                                LogUtils.e("log === : " + message);
                            }
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                LogUtils.e("log === : " + message);
            }
        }
    }
}
