package com.Util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/8/18
 * @Other //时间转换
 */
public class ToDate {

    public static String getTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }
}
