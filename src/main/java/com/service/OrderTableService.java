package com.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.Map;

/**
 * @Creator Ming
 * @Time 2019/8/23
 * @Other
 */

public interface OrderTableService {


    public float getOrderPrice(String id);

    public int setOrderPayTime(String id, Date date);

    public boolean isEmptyId(String id);

    JSONObject getOrderList(Map paramMap);
}
