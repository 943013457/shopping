package com.Util;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/17
 * @Other
 */
public class UrlLink {
    private static String Separator = "";

    private static String put(String left, String right) {
        if (left.isEmpty()) {
            return right;
        }
        if (right.isEmpty()) {
            return left;
        }
        return left + Separator + right;
    }

    public static String putAll(List<String> list) {
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        String str = list.get(0);
        list.remove(0);
        return put(str, putAll(list));
    }

    public static void setSeparator(String separator) {
        Separator = separator;
    }
}
