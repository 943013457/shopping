package com.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.RolePermission;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/23
 * @Other
 */
public interface AdminService {
    JSONArray getMenu(List<RolePermission> rolePermissionList);

    int getRoleCount();

    int getUserCount();

    int getProductCount();

    int getReviewCount();

    int getUnfinishedOrder();

    int getFinishedOrder();

    JSONObject getPayLog(int time);
}
