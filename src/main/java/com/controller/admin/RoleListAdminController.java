package com.controller.admin;

import com.Util.JsonLink;
import com.alibaba.fastjson.JSONObject;
import com.pojo.example.RoleExample;
import com.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Creator Ming
 * @Time 2020/3/24
 * @Other 用户管理-角色列表
 */
@Controller
public class RoleListAdminController {
    @Autowired
    private RoleService roleService;

    //后台管理-获取角色列表
    @RequestMapping(value = {"/getRoles"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "角色列表")
    private String getRoles(@RequestParam(value = "limit", defaultValue = "20") int limit,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "roleName", required = false) String roleName) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        if (roleName != null && !"".equals(roleName)) {
            criteria.andRoleNameLike("%" + roleName + "%");
        }
        return JsonLink.LayUISuccess(roleService.getRoleList(roleExample, page, limit));
    }

    //后台管理-角色状态修改
    @RequestMapping(value = {"/adminRoleState"}, method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "用户列表")
    private String adminUserState(@RequestBody JSONObject jsonObject) {
        String idList = jsonObject.getString("roleIdList");
        Boolean state = jsonObject.getBoolean("state");
        List<Integer> list = Arrays.stream(idList.split(","))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
        return JsonLink.Success(roleService.updateRoleState(list, state));
    }
}
