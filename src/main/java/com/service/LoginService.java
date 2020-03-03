package com.service;


public interface LoginService {
    //密码验证
    String SelectUserPassword(String Username);

    //账号状态
    boolean SelectUserState(String Username);

    //获取盐
    String SelectUserSalt(String Username);


}