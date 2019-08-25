package com.controller;

import com.pojo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 处理登录信息
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/login"})
    private String login() {
        return "login";
    }

    @RequestMapping(value = "/selectUserPwd", method = RequestMethod.POST)
    @ResponseBody
    private String selectUserPwd(@RequestBody LoginUser user) {
        String username = user.getUser();
        String password = user.getPassword();
        if (username.isEmpty()) {
            return "UserNameNull";
        }
        if (password.isEmpty()) {
            return "PasswordNull";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //账号被封禁
            if(e instanceof DisabledAccountException){
                return "banned";
            }
            //验证用户/密码错误
            else if(e instanceof CredentialsException){
                return "error";
            }
        }
        return "ok";
    }
}
