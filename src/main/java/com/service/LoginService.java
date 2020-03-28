package com.service;


import com.alibaba.fastjson.JSONObject;
import com.pojo.example.LoginUserExample;

import java.util.List;

public interface LoginService {
    //密码验证
    String SelectUserPassword(String Username);

    //账号状态
    boolean SelectUserState(String Username);

    //获取盐
    String SelectUserSalt(String Username);

    //获取用户列表
    JSONObject getUserList(LoginUserExample loginUserExample, LoginUserExample.Criteria criteria, int page, int limit);

    //修改状态
    int UpdateUserState(List<String> userList, boolean state);

}