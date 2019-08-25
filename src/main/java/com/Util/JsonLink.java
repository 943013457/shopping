package com.Util;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 合并Json字符串，使用递归合并多个
 */
public class JsonLink {
    public static String put(String left, String right) {
        if (left.isEmpty()) {
            return right;
        }
        if (right.isEmpty()) {
            return left;
        }
        return left.replace("}", ",") + right.replace("{", "");
    }

    public static String putAll(List<String> list) {
        if (list.size() == 1) {
            return list.get(0);
        }
        String str = list.get(0);
        list.remove(0);
        return put(str, putAll(list));
    }

}
