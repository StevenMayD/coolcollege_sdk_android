package com.coolcollege.aar.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.coolcollege.aar.bean.ReqFileParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

public class ParameterUtils {

    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
//        headers.put(Global.USER_AGENT_KEY, Global.CLIENT_VERSION);
//        headers.put(Global.TOKEN_KEY, MemCacheUtils.get().getToken());
//        headers.put(Global.TOKEN2_KEY, MemCacheUtils.get().getToken());
//        headers.put(Global.LANGUAGE_KEY, LocalUtils.getSystemLanguage());
        return headers;
    }

//    /**
//     * 获取请求参数
//     * 将请求参数包装并返回
//     *
//     * @param request
//     * @return
//     */
//    public static HashMap<String, String> getParams(Request request) {
//        HashMap<String, String> params = new HashMap<>();
//        String method = request.method();
//        if (Global.REQUEST_GET.equalsIgnoreCase(method)) {
//            putHeaderFromGet(request, params);
//        } else {
//            putHeaderFromPostBody(request, params);
//        }
//        return params;
//    }

    /**
     * 装入post请求的body参数
     *
     * @param request
     * @param params
     */
    private static void putHeaderFromPostBody(Request request, HashMap<String, String> params) {
        try {
            Buffer buffer = new Buffer();
            Charset UTF8 = StandardCharsets.UTF_8;
            RequestBody body = request.body();
            if (body != null) {
                body.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (charset != null) {
                    String string = buffer.readString(charset);
                    Gson gson = GsonConfig.get().getGson();
                    HashMap<String, String> map = gson.fromJson(string, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    for (String key : map.keySet()) {
                        String value = map.get(key);
                        params.put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 装入get请求参数
     *
     * @param request
     * @param params
     */
    private static void putHeaderFromGet(Request request, HashMap<String, String> params) {
        HttpUrl url = request.url().newBuilder().build();
        Set<String> names = url.queryParameterNames();
        for (String key : names) {
            String value = url.queryParameter(key);
            params.put(key, value);
        }
    }

    /**
     * 对token字段做转码
     *
     * @param key
     * @param value
     * @return
     */
    private static String checkTokenEncode(String key, String value) {
        if ("token".equalsIgnoreCase(key)) {
            value = encodeURI(value);
        }
        return value;
    }

    private static String encodeURI(String st) {
        String allowedChars = "._-$,;~()";
        return Uri.encode(st, allowedChars);
    }


    /**
     * 顺序存入键值对值返回map
     * <p>
     * 强制要求有key就有对应的value
     * 缺少对应的key或value将抛出错误
     *
     * @param content
     * @return
     */
    public static HashMap<String, String> genericParams(String... content) {
        return genericParams(false, content);
    }

    public static HashMap<String, String> genericParams(boolean putEmpty, String... content) {
        ArrayList<String> allList = new ArrayList<>(Arrays.asList(content));
        if (allList.size() % 2 != 0)
            throw new IllegalArgumentException("make sure key and value is twain");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < allList.size(); i = i + 2) {
            String value = allList.get(i + 1);
            if (putEmpty) {
                map.put(allList.get(i), value);
            } else {
                if (!TextUtils.isEmpty(value)) {
                    map.put(allList.get(i), value);
                }
            }
        }
        return map;
    }


    public static HashMap<String, String> genericParams2(String... content) {
        ArrayList<String> allList = new ArrayList<>(Arrays.asList(content));
        if (allList.size() % 2 != 0)
            throw new IllegalArgumentException("make sure key and value is twain");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < allList.size(); i = i + 2) {
            String value = allList.get(i + 1);
            map.put(allList.get(i), value);
        }
        return map;
    }


    public static HashMap<String, Object> genericJsonParams(Object... content) {
        ArrayList<Object> allList = new ArrayList<>(Arrays.asList(content));
        if (allList.size() % 2 != 0)
            throw new IllegalArgumentException("make sure key and value is complete");
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < allList.size(); i = i + 2) {
            Object value = allList.get(i + 1);
            if (value != null) {
                map.put((String) allList.get(i), value);
            }
        }
        return map;
    }

    private static final String MULTIPART_TYPE = "multipart/form-data";
    private static final String IMG_TYPE = "image/";
    private static final String VIDEO_TYPE = "video/";
    private static final String AUDIO_TYPE = "audio/";
    private static final String PNG_TYPE = "png";
    private static final String JPG_TYPE = "jpg";
    private static final String JPEG_TYPE = "jpeg";
    private static final String GIF_TYPE = "gif";
    private static final String BPM_TYPE = "bpm";
    private static final String WEBP_TYPE = "webP";
    private static final String MP4_TYPE = "mp4";
    private static final String MP3_TYPE = "mp3";

    public static MultipartBody.Part genericTextParams(String key, String value) {
        return MultipartBody.Part.createFormData(key, value);
    }

    public static String getFileType(File file) {
        String fileName = file.getName();
        int endIndex = fileName.lastIndexOf(".");
        String fileSuffix = fileName.substring(endIndex + 1);
        if (matchImgType(fileSuffix)) {
            return IMG_TYPE + fileSuffix;
        } else if (fileSuffix.contains(MP3_TYPE)) {
            return AUDIO_TYPE + fileSuffix;
        } else if (fileSuffix.contains(MP4_TYPE)) {
            return VIDEO_TYPE + fileSuffix;
        } else {
            return MULTIPART_TYPE;
        }
    }

    private static boolean matchImgType(String fileSuffix) {
        String[] imgType = {PNG_TYPE, JPG_TYPE, JPEG_TYPE,
                GIF_TYPE, BPM_TYPE, WEBP_TYPE};

        for (String type : imgType) {
            if (fileSuffix.contains(type)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<MultipartBody.Part> genericFileFormParams(ReqFileParams... params) {
        ArrayList<MultipartBody.Part> list = new ArrayList<>();
        for (ReqFileParams param : params) {
            MultipartBody.Part part;
            String key = param.key;
            Object value = param.value;
            if (value instanceof File) {
                File file = (File) value;
                String mediaType = getFileType(file);
                RequestBody body = RequestBody.create(MediaType.parse(mediaType), file);
                part = MultipartBody.Part.createFormData(key, file.getName(), body);
            } else {
                part = MultipartBody.Part.createFormData(key, (String) value);
            }
            list.add(part);
        }
        return list;
    }
}
