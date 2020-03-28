package com.Util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/8/18
 * @Other //时间转换
 */
public class DateUtil {

    public static String getTime(Date date) {
        if (date == null) {
            return null;
        }
        String ret = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            ret = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Date StringToDate(String time) {
        if (time == null || "".equals(time)) {
            return null;
        }
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return date;
    }
}


