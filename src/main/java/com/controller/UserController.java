package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.Util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import com.pojo.LoginUser;
import com.shiro.PageDistribute;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.nimbus.State;


/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 处理登录信息
 */
@Controller
public class UserController {

    @RequestMapping(value = {"/login"})
    private String login() {
        return "login";
    }

    @RequestMapping(value = {"/getUser"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String getUser() {
        String name = UserUtil.getUserName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name != null ? name : "");
        return JsonLink.Success(jsonObject.toJSONString());
    }

    @RequestMapping(value = "/selectUserPwd", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"管理员"})
    private String selectUserPwd(@RequestBody LoginUser loginUser, HttpServletRequest request) {
        String username = loginUser.getUser();
        String password = loginUser.getPassword();
        if (username.isEmpty()) {
            return JsonLink.Error(StateCode.ERR_NOT_USERNAME);
        }
        if (password.isEmpty()) {
            return JsonLink.Error(StateCode.ERR_NOT_PASSWORD);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
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
        //登录成功后进行页面分发
        return JsonLink.Success(PageDistribute.getRoleIndex(request));
    }
}
