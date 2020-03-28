package com.service;

import com.alibaba.fastjson.JSONObject;
import com.pojo.example.RoleExample;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/24
 * @Other
 */
public interface RoleService {
    JSONObject getRoleList(RoleExample roleExample, int page, int limit);

    int updateRoleState(List<Integer> list, boolean state);
}
