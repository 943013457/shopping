package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.pojo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.nimbus.State;


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

    @RequestMapping(value = "/selectUserPwd", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String selectUserPwd(@RequestBody LoginUser loginUser) {
        String username = loginUser.getUser();
        String password = loginUser.getPassword();
        if (username.isEmpty()) {
            return JsonLink.Error(StateCode.ERR_NOT_USERNAME);
        }
        if (password.isEmpty()) {
            return JsonLink.Error(StateCode.ERR_NOT_PASSWORD);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(password, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            //账号被封禁
            if (e instanceof DisabledAccountException) {
                return JsonLink.Error(StateCode.ERR_NOT_BANNED);
            }
            //验证用户/密码错误
            else if (e instanceof CredentialsException) {
                return JsonLink.Error(StateCode.ERR_NOT_VALIDATION);
            }
        }
        return JsonLink.Success("登录成功");
    }
}
