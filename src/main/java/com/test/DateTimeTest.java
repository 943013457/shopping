package com.test;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other
 */
public class DateTimeTest {
    @Test
    public void test() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()));
    }
}
