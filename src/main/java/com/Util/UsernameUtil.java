package com.Util;

/**
 * @Creator Ming
 * @Time 2019/8/18
 * @Other
 */
public class UsernameUtil {
    public static String ToAnonymity(String username) {
        if (username.isEmpty()) {
            return null;
        }
        int len = username.length();
        return username.substring(0, 1) + "***" + username.substring(len - 1, len);
    }
}
