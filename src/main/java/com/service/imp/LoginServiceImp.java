package com.service.imp;


import com.mapper.LoginUserMapper;
import com.pojo.LoginUser;
import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private LoginUserMapper loginUserMapper;

    private LoginUser getUser(String user) {
        return loginUserMapper.selectByPrimaryKey(user);
    }

    @Override
    public String SelectUserPassword(String Username) {
        LoginUser user = getUser(Username);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }

    @Override
    public boolean SelectUserState(String Username) {
        LoginUser user = getUser(Username);
        if (user == null || !user.getState()) {
            return false;
        }
        return true;
    }

    @Override
    public String SelectUserSalt(String Username) {
        LoginUser user = getUser(Username);
        if (user == null) {
            return null;
        }
        return user.getSalt();
    }
}
