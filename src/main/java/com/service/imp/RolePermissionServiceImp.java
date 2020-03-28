package com.service.imp;

import com.mapper.RoleMapper;
import com.mapper.RolePermissionMapper;
import com.mapper.UserRoleMapper;
import com.pojo.Role;
import com.pojo.RolePermission;
import com.pojo.UserRole;
import com.pojo.example.RoleExample;
import com.pojo.example.RolePermissionExample;
import com.pojo.example.UserRoleExample;
import com.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Creator Ming
 * @Time 2020/3/21
 * @Other
 */
@Service
public class RolePermissionServiceImp implements RolePermissionService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    //用户角色获取
    @Override
    public List<UserRole> getUserRole(String username) {
        //获取所有未被禁用的角色
        RoleExample roleExample = new RoleExample();
        roleExample.or().andStateEqualTo(true);
        List<Integer> list = roleMapper.selectByExample(roleExample)
                .stream()
                .map(Role::getRoleId)
                .collect(Collectors.toList());
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.or().andUserNameEqualTo(username).andRoleIdIn(list);
        return userRoleMapper.selectByExample(userRoleExample);
    }
    //用户权限获取
    @Override
    public List<RolePermission> getUserPermission(List<Integer> roleIdList) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.or().andRoleIdIn(roleIdList);
        return rolePermissionMapper.selectByExample(rolePermissionExample);
    }
}
