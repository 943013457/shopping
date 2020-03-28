package com.service;

import com.pojo.Permission;
import com.pojo.example.PermissionExample;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/25
 * @Other
 */
public interface PermissionService {
    List<Permission> getPermissionList(PermissionExample permissionExample);
}
