package com.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other
 */
public interface ProductService {
    public JSONObject getProductJson(int id);

    public boolean hasProduct(int id);

    public JSONObject getNameAndPrice(int id);
}
