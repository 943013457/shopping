package com.service;

import com.pojo.LoginUser;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other 注册
 */
public interface RegisterService {

    public boolean InsertUser(LoginUser user);

    public boolean selectUser(String username);

}
