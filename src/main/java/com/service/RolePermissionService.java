package com.service;

import com.pojo.RolePermission;
import com.pojo.UserRole;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/21
 * @Other
 */
public interface RolePermissionService {
    List<UserRole> getUserRole(String username);

    List<RolePermission> getUserPermission(List<Integer> roleIdList);
}
