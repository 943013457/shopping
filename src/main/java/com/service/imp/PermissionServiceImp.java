package com.service.imp;

import com.mapper.PermissionMapper;
import com.pojo.Permission;
import com.pojo.example.PermissionExample;
import com.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/25
 * @Other
 */
@Service
public class PermissionServiceImp implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionList(PermissionExample permissionExample) {
        return permissionMapper.selectByExample(permissionExample);
    }
}
