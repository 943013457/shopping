package com.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

    //请求成功
    public static <T> String Success(T Json) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", BeanToString(Json));
        jsonObject.put("code", StateCode.REQUEST_SUCCESS);
        return jsonObject.toJSONString();
    }

    //请求失败
    public static <T> String Error(T Json) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", BeanToString(Json));
        jsonObject.put("code", StateCode.REQUEST_ERROR);
        return jsonObject.toJSONString();
    }

    //字符串转换
    private static <T> String BeanToString(T obj) {
        Class<?> clazz = obj.getClass();
        if (int.class == clazz || Integer.class == clazz ||
                long.class == clazz || Long.class == clazz ||
                float.class == clazz || Float.class == clazz) {
            return "" + obj;
        } else if (boolean.class == clazz || Boolean.class == clazz) {
            return String.valueOf(obj);
        } else if (String.class == clazz) {
            return (String) obj;
        }
        return JSON.toJSONString(obj);
    }
}
