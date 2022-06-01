package com.coolcollege.aar.bean;

import java.util.ArrayList;

/**
 * 项目名称：cn.usho.sosho
 * 类描述：  服务器返回数据公共抽象类
 * 作者：   Even_for .
 * 日期：   2017/9/14 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */

public class ResponseBean<T> {

    public T data;
    public ArrayList<T> list;
    public int code;
    public String msg;
    public boolean deleted;
    public boolean updated;
    public boolean result;
    public boolean is_register;
}
