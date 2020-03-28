package com.controller.admin;

import com.Util.DateUtil;
import com.Util.JsonLink;
import com.alibaba.fastjson.JSONObject;
import com.pojo.RolePermission;
import com.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/22
 * @Other 后台管理-登录相关
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = {"/doAdmin"})
    @RequiresRoles(value = {"管理员"})
    private String login() {
        return "admin/index";
    }

    //获取菜单
    @RequestMapping(value = {"/getMenu"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String getMenu() {
        try {
            List<RolePermission> rolePermissionList = (List<RolePermission>) SecurityUtils.getSubject().getSession().getAttribute("PermissionList");
            return JsonLink.Success(adminService.getMenu(rolePermissionList));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //获取控制台数据
    @RequestMapping(value = {"/getIndexCount"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresRoles(value = {"管理员"})
    private String getIndexCount() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleCount", adminService.getRoleCount());
        jsonObject.put("userCount", adminService.getUserCount());
        jsonObject.put("productCount", adminService.getProductCount());
        jsonObject.put("reviewCount", adminService.getReviewCount());
        jsonObject.put("unFinishCount", adminService.getUnfinishedOrder());
        jsonObject.put("finishCount", adminService.getFinishedOrder());
        return JsonLink.Success(jsonObject);
    }

    //交易统计,从支付表记录获取
    @RequestMapping(value = {"/getPayLog"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresRoles(value = {"管理员"})
    private String getPayLog(@RequestParam(value = "time", defaultValue = "-7") int time) {
        return JsonLink.Success(adminService.getPayLog(time));
    }

}
