package com.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other
 */
public interface ProductImageService {
    public JSONObject getUrlJson(int id);

    public JSONObject getFirstImgJson(int id);
}
