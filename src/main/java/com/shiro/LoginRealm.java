package com.shiro;

import com.pojo.RolePermission;
import com.pojo.UserRole;
import com.service.LoginService;
import com.service.RolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other Realm的授权和验证
 */
public class LoginRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RolePermissionService rolePermissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //用户名
        String username = principalCollection.getPrimaryPrincipal().toString();
        //获取角色
        List<UserRole> userRoleList = rolePermissionService.getUserRole(username);
        List<Integer> RoleIdList = new ArrayList<>();
        Iterator<UserRole> userRoleIterator = userRoleList.iterator();
        while (userRoleIterator.hasNext()) {
            UserRole userRole = userRoleIterator.next();
            RoleIdList.add(userRole.getRoleId());
            simpleAuthorizationInfo.addRole(userRole.getRoleName());
        }
        //获取权限
        if (RoleIdList.size() > 0) {
            List<RolePermission> rolePermissionList = rolePermissionService.getUserPermission(RoleIdList);
            Iterator<RolePermission> rolePermissionIterator = rolePermissionList.iterator();
            while (rolePermissionIterator.hasNext()) {
                RolePermission rolePermission = rolePermissionIterator.next();
                simpleAuthorizationInfo.addStringPermission(rolePermission.getPermissionName());
            }
            //把权限列表存入session,在动态创建菜单时用到
            SecurityUtils.getSubject().getSession().setAttribute("PermissionList",rolePermissionList);
        }

        return simpleAuthorizationInfo;
    }

    //密码验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Object user = authenticationToken.getPrincipal();
        //查询用户状态
        if (!loginService.SelectUserState(user.toString())) {
            throw new DisabledAccountException();
        }
        //获取数据库密码
        String db_password = loginService.SelectUserPassword(user.toString());
        SimpleAuthenticationInfo simple = new SimpleAuthenticationInfo(user, db_password, this.getName());
        return simple;
    }

    @Override
    public String getName() {
        return "LoginRealm";
    }
}
