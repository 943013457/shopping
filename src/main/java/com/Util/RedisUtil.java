package com.Util;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/11/17
 * @Other 操作Redis
 */
public class RedisUtil {

    private static JedisPool jedisPool;

    //访问记录
    public static void setAccessRecord(HttpServletRequest httpServletRequest) {
        //在Filter中注入JedisPool
        ServletContext sc = httpServletRequest.getSession().getServletContext();
        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
        if (cxt != null && cxt.getBean("redisClient") != null) {
            jedisPool = (JedisPool)cxt.getBean("redisClient");

            Jedis jedis = jedisPool.getResource();
            jedis.auth("943013457");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String nowDate = df.format(new Date());
            String access = httpServletRequest.getRequestURI();
            String address = UserUtil.getIP(httpServletRequest);
            jedis.hset("UserAccessPage:" + address, nowDate, access);
            jedis.close();
        }
        else{
            jedisPool = null;
        }
    }
}
