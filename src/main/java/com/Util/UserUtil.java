package com.Util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Creator Ming
 * @Time 2020/3/2
 * @Other
 */
public class UserUtil {
    //用户是否登录校验
    public static String getUserName() {
        Object name = SecurityUtils.getSubject().getPrincipal();
        return name != null ? name.toString() : null;
    }

    //获取加密盐
    public static String getRandomSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    //MD5加密
    public static String getPasswordCiph(String password, String salt) {
        return new Md5Hash(password, salt, 3).toString();
    }

    //IP获取
    public static String getIP(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    //用户名称匿名
    public static String ToAnonymity(String username) {
        if (username.isEmpty()) {
            return null;
        }
        int len = username.length();
        return username.substring(0, 1) + "***" + username.substring(len - 1, len);
    }
}
