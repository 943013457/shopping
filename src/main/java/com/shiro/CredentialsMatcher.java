package com.shiro;

import com.Util.UserUtil;
import com.service.LoginService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Creator Ming
 * @Time 2020/3/21
 * @Other   shiro密码比较器
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //token 输入,info 数据库查询
        String username = token.getPrincipal().toString();
        String pwd = new String((char[])token.getCredentials());
        String infoPwd = info.getCredentials().toString();//数据库查询的密码
        //获取Md5
        String encrypt_pwd = UserUtil.getPasswordCiph(pwd, loginService.SelectUserSalt(username));
        if (!encrypt_pwd.equals(infoPwd)) {
            throw new CredentialsException();
        }
        return true;
    }
}
