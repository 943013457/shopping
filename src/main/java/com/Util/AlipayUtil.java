package com.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Creator Ming
 * @Time 2019/8/25
 * @Other
 */
public class AlipayUtil {
    public static Map<String,String> getParams(HttpServletRequest request){
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> params = new HashMap<>();

        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            String values[] = map.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(key, valueStr);
        }

        return params;
    }
}
