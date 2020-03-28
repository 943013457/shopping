package com.controller.admin;

import com.Util.JsonLink;
import com.pojo.RolePermission;
import com.pojo.example.PermissionExample;
import com.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Creator Ming
 * @Time 2020/3/25
 * @Other 权限管理相关操作
 */
@Controller
public class PermissionAdminController {
    @Autowired
    private PermissionService permissionService;

    //后台管理-获取权限列表
    @RequestMapping(value = {"/getPermission"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "权限列表")
    private String getPermission(@RequestParam(value = "permissionName", required = false) String permissionName) {
        List<Integer> permissionList = null;
        try {
            List<RolePermission> rolePermissionList = (List<RolePermission>) SecurityUtils.getSubject().getSession().getAttribute("PermissionList");
            permissionList = rolePermissionList
                    .stream()
                    .map(RolePermission::getPermissionId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PermissionExample permissionExample = new PermissionExample();
        PermissionExample.Criteria criteria = permissionExample.createCriteria();
        criteria.andPermissionIdIn(permissionList);
        if (permissionName != null) {
            criteria.andPermissionNameLike("%" + permissionName + "%");
        }
        return JsonLink.Success(permissionService.getPermissionList(permissionExample));
    }
}
