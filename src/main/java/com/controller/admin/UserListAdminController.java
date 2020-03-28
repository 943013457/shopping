package com.controller.admin;

import com.Util.DateUtil;
import com.Util.JsonLink;
import com.alibaba.fastjson.JSONObject;
import com.pojo.example.LoginUserExample;
import com.service.LoginService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/24
 * @Other 后台管理-用户列表
 */
@Controller
public class UserListAdminController {
    @Autowired
    private LoginService loginService;

    //后台管理-用户列表数据
    @RequestMapping(value = {"/adminGetUser"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "用户列表")
    private String adminGetUser(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "startTime", required = false) String startTime,
                                @RequestParam(value = "endTime", required = false) String endTime,
                                @RequestParam(value = "username", required = false) String userName) {
        LoginUserExample loginUserExample = new LoginUserExample();
        LoginUserExample.Criteria criteria = loginUserExample.createCriteria();
        if (startTime != null && !"".equals(startTime)) {
            criteria.andRegistertimeGreaterThanOrEqualTo(DateUtil.StringToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            criteria.andRegistertimeLessThanOrEqualTo(DateUtil.StringToDate(endTime));
        }
        if (userName != null && !"".equals(userName)) {
            criteria.andUserLike("%" + userName + "%");
        }
        return JsonLink.LayUISuccess(loginService.getUserList(loginUserExample, criteria, page, limit));
    }

    //后台管理-用户状态修改
    @RequestMapping(value = {"/adminUserState"}, method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "用户列表")
    private String adminUserState(@RequestBody JSONObject jsonObject) {
        String idList = jsonObject.getString("idList");
        Boolean state = jsonObject.getBoolean("state");
        String[] username = idList.split(",");
        List<String> list = Arrays.asList(username);
        return JsonLink.Success(loginService.UpdateUserState(list, state));
    }
}
