package com.controller;

import com.Util.AddressUtil;
import com.Util.UserLoginAndRegister;
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

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    @ResponseBody
    private boolean registerUser(@RequestBody LoginUser user, HttpServletRequest request) {
        if (user.getUser().isEmpty() || user.getPassword().isEmpty()) {
            return false;
        }
        //获取注册IP
        user.setRegisterIp(AddressUtil.getIP(request));
        //注册时间
        user.setRegistertime(new Date());
        //账号状态
        user.setState(true);
        //获取盐
        user.setSalt(UserLoginAndRegister.getRandomSalt());
        //保存原密码
        String password = user.getPassword();
        //加密
        user.setPassword(UserLoginAndRegister.getPasswordCiph(user.getPassword(), user.getSalt()));
        //注册成功自动登录
        if (!registerService.InsertUser(user)) {
            return false;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUser(), password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return true;
    }

    //查询用户名是否存在
    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    @ResponseBody
    private boolean selectUser(String username) {
        return registerService.selectUser(username);
    }
}
