package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.Util.UserUtil;
import com.pojo.LoginUser;
import com.service.RegisterService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 注册用户
 */
@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = {"/register"})
    private String register() {
        return "register";
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String registerUser(@RequestBody LoginUser user, HttpServletRequest request) {
        if (user.getUser().isEmpty()) {
            return JsonLink.Error(StateCode.ERR_NOT_USERNAME);
        }
        if (user.getPassword().isEmpty()) {
            return JsonLink.Error(StateCode.ERR_NOT_PASSWORD);
        }
        //获取注册IP
        user.setRegisterIp(UserUtil.getIP(request));
        //注册时间
        user.setRegistertime(new Date());
        //账号状态
        user.setState(true);
        //获取盐
        user.setSalt(UserUtil.getRandomSalt());
        //保存原密码
        String password = user.getPassword();
        //加密
        user.setPassword(UserUtil.getPasswordCiph(user.getPassword(), user.getSalt()));
        //注册成功自动登录
        if (!registerService.InsertUser(user)) {
            return JsonLink.Error(StateCode.ERR_NOT_REGISTER);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUser(), password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return JsonLink.Error(true);
    }

    //查询用户名是否存在
    @RequestMapping(value = "/selectUser/{username}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String selectUser(@PathVariable(name = "username") String username) {
        return registerService.selectUser(username) ? JsonLink.Success(true) : JsonLink.Error(false);
    }
}
