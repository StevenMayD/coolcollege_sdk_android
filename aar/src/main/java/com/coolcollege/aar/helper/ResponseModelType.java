package com.coolcollege.aar.helper;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 构造response的自定义bean
 * 可以解决ResponseBean<T> 和 ResponseBean<List<T>> 类型的数据
 *
 */
public class ResponseModelType implements ParameterizedType {
    //response数据最外层对象 -> DataResultBean
    private Class raw;
    //自定义bean中明确的范型类型
    private Type[] args;

    public ResponseModelType(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    //返回最外层response的bean类型
    @Override
    public Type getRawType() {
        return raw;
    }

    public Type getOwnerType() {
        return null;
    }
}
