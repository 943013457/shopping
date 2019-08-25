package com.service.imp;

import com.mapper.LoginUserMapper;
import com.pojo.LoginUser;
import com.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other
 */
@Service
public class RegisterServiceImp implements RegisterService {
    @Autowired
    private LoginUserMapper mapper;

    @Override
    public boolean InsertUser(LoginUser user) {
        return 1 == mapper.insertSelective(user);
    }

    @Override
    public boolean selectUser(String username) {
        return null == mapper.selectByPrimaryKey(username);
    }
}
