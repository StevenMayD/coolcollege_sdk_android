package com.coolcollege.aar.helper;


import com.coolcollege.aar.utils.GsonConfig;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 简单的数据解析提供帮助类
 */
public class DataParseHelper {

    private static DataParseHelper instance = new DataParseHelper();

    private DataParseHelper() {
    }

    public static DataParseHelper get() {
        return instance;
    }

    public ResponseModelType getResponseType(Class raw, Type[] types) {
        return new ResponseModelType(raw, types);
    }

    // 获取泛型类型
    public Type getGenericType(Class clazz) {
        Type genType = clazz.getGenericSuperclass();
        if (genType instanceof Class) {
            return clazz;
        } else {
            ParameterizedType outGenType = (ParameterizedType) genType;
            if (outGenType != null) {
                Type[] types = outGenType.getActualTypeArguments();
                return types[0];
            }
        }
        return null;
    }

    public Gson getGsonParser() {
        return GsonConfig.get().getGson();
    }
}
