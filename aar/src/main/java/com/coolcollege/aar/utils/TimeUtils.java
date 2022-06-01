package com.coolcollege.aar.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Evan_for on 2020/6/16
 */
public class TimeUtils {


    public static String formatMills(String mills) {
        return formatMills(mills, true);
    }

    public static String formatMills(String mills, boolean showHours) {
        StringBuilder sb = new StringBuilder();

        //long days = Long.parseLong(mills) / (60 * 60 * 24);
        long hours = (Long.parseLong(mills) % (60 * 60 * 24)) / (60 * 60);
        long minutes = (Long.parseLong(mills) % (60 * 60)) / 60;
        long seconds = Long.parseLong(mills) % 60;

        if (showHours) {
            if (hours < 10) {
                sb.append("0");
            }
            sb.append(hours);
            sb.append(":");
        }

        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes);
        sb.append(":");
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds);

        return sb.toString();
    }


    public static String getSimpleTime(long time, String endString) {

        if (time <= 0) {
            return "";
        }

        long seconds = getSeconds(time);
        long minutes = getMinutes(time);
        long hours = getHours(time);
        long days = getDays(time);
        long mouth = -1;

        if (days >= 30) {
            mouth = days / 30;
        }

        // 处理时间转换
        if (seconds > 0 && seconds < 60) {
            return seconds + "秒前" + endString;
        } else if (minutes > 0 && minutes < 60) {
            return minutes + "分钟前" + endString;
        } else if (hours > 0 && hours < 24) {
            return hours + "小时前" + endString;
        } else if (days > 0 && days < 7) {
            return days + "天前" + endString;
        } else if (mouth > 0 && mouth < 6) {
            return mouth + "个月前" + endString;
        }

        return "";
    }

    public static long getSeconds(long timeStamp) {

        long seconds = timeStamp / 1000;

        long currentTime = getTime();

        return currentTime - seconds;

    }

    public static long getMinutes(long timeStamp) {

        long seconds = timeStamp / 1000;

        long currentTime = getTime();

        return (currentTime - seconds) / 60;
    }

    public static long getHours(long timeStamp) {
        long seconds = timeStamp / 1000;

        long currentTime = getTime();

        return (currentTime - seconds) / (60 * 60);
    }

    public static long getDays(long timeStamp) {

        long seconds = timeStamp / 1000;

        long currentTime = getTime();

        return (currentTime - seconds) / (60 * 60 * 24);
    }

    /**
     * 获取当前系统10位时间戳
     */
    public static long getTime() {

        return System.currentTimeMillis() / 1000;
    }


    public static String timeStamp2Date(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ENGLISH);
        DateFormat dateFormat = DateFormat.getDateInstance();
        return format.format(new Date(timeStamp));
    }

}
