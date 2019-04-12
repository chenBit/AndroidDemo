package com.cw.androidbase.sdk.utils;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Description: 日期相关转换的工具类
 * Author:Created by Rambo on 2017/1/20.
 * Email:1988chenwei@gmail.com
 */
public class DateUtil {
    public static final String DATE_TIME_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_SHORT = "yyyy-MM-dd";

    public static String getDateTimeStr(String timeFormat) {
        Date date = new Date();
        return DateFormat.format(timeFormat, date.getTime()).toString();
    }
}