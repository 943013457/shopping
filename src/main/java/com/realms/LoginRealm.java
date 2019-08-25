package com.realms;

import com.Util.UserLoginAndRegister;
import com.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other Realm的授权和验证
 */
public class LoginRealm extends AuthorizingRealm {
    @Autowired
    private LoginService service;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //TODO
        return null;
    }

    //密码验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials());
        //获取Md5
        String encrypt_pwd = UserLoginAndRegister.getPasswordCiph(password, service.SelectUserSalt(username));
        String db_password = service.SelectUserPassword(username);
        boolean state = service.SelectUserState(username);
        //TODO 抛出异常信息
        if (!encrypt_pwd.equals(db_password)) {
            throw new CredentialsException();
        }
        if (!state) {
            throw new DisabledAccountException();
        }
        SimpleAuthenticationInfo simple = new SimpleAuthenticationInfo(username, password, this.getName());
        return simple;
    }

    @Override
    public String getName() {
        return "LoginRealm";
    }
}
