package com.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.*;
import com.pojo.PayTableLog;
import com.pojo.Permission;
import com.pojo.RolePermission;
import com.pojo.example.*;
import com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Creator Ming
 * @Time 2020/3/23
 * @Other
 */
@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    LoginUserMapper loginUserMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    PayTableMapper payTableMapper;
    @Autowired
    PayTableLogMapper payTableLogMapper;

    @Override
    public JSONArray getMenu(List<RolePermission> rolePermissionList) {
        Map<Integer, List<Permission>> map = new HashMap<>();
        //将角色权限关联表中的权限ID提取出来
        Iterator<RolePermission> iterator = rolePermissionList.iterator();
        List<Integer> permissionIdList = new ArrayList<>();
        while (iterator.hasNext()) {
            RolePermission rolePermission = iterator.next();
            permissionIdList.add(rolePermission.getPermissionId());
        }
        if (permissionIdList.size() > 0) {
            //查询权限表,获取对应的菜单信息
            PermissionExample permissionExample = new PermissionExample();
            permissionExample.or().andPermissionIdIn(permissionIdList);
            List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
            //将查询出来的菜单按父级菜单分类,存放到map中
            if (permissionIdList.size() > 0) {
                Iterator<Permission> permissionIterator = permissionList.iterator();
                while (permissionIterator.hasNext()) {
                    Permission permission = permissionIterator.next();
                    int parent = permission.getParent();
                    if (!map.containsKey(parent)) {
                        map.put(parent, new ArrayList<Permission>());
                    }
                    List<Permission> mapPermissionList = map.get(parent);
                    mapPermissionList.add(permission);
                }
            }
        }
        //生成菜单树,从一级菜单开始
        return createTree(new JSONArray(), map, 0);
    }

    private JSONArray createTree(JSONArray jsonArray, Map<Integer, List<Permission>> map, int parent) {
        //递归创建菜单树
        if (map.containsKey(parent)) {
            List<Permission> permissionList = map.get(parent);
            Iterator<Permission> iterator = permissionList.iterator();
            while (iterator.hasNext()) {
                Permission permission = iterator.next();
                JSONObject jsonObject = new JSONObject();
                jsonArray.add(jsonObject);
                jsonObject.put("title", permission.getPermissionName());
                jsonObject.put("path", permission.getPath());
                jsonObject.put("font", permission.getFont());
                jsonObject.put("icon", permission.getIcon());
                jsonObject.put("spread", false);
                //递归生成子菜单
                JSONArray subMenu = createTree(new JSONArray(), map, permission.getPermissionId());
                jsonObject.put("children", subMenu);
            }
        }
        return jsonArray;
    }

    @Override
    public int getRoleCount() {
        return (int) roleMapper.countByExample(new RoleExample());
    }

    @Override
    public int getUserCount() {
        return (int) loginUserMapper.countByExample(new LoginUserExample());
    }

    @Override
    public int getProductCount() {
        return (int) productMapper.countByExample(new ProductExample());
    }

    @Override
    public int getReviewCount() {
        return (int) reviewMapper.countByExample(new ReviewExample());
    }

    @Override
    public int getUnfinishedOrder() {
        return payTableLogMapper.countByUnFinish();
    }

    @Override
    public int getFinishedOrder() {
        return payTableLogMapper.countByFinish();
    }

    @Override
    public JSONObject getPayLog(int time) {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("未支付订单",new LinkedHashMap<String, Object>());
        map.put("已支付订单",new LinkedHashMap<String, Object>());
        map.put("已完成订单",new LinkedHashMap<String, Object>());
        List<PayTableLog> payTableLogList = payTableLogMapper.selectByPayInfo(time);
        for (PayTableLog payTableLog : payTableLogList) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String changeTime = sdf.format(payTableLog.getChangetime());
            String laterState = payTableLog.getLaterstate();
            switch (laterState) {
                case "PAYMENT":
                    LinkedHashMap<String, Object> noPayment = (LinkedHashMap<String, Object>)map.get("未支付订单");
                    if (!noPayment.containsKey(changeTime)) {
                        noPayment.put(changeTime, 0);
                    }
                    noPayment.replace(changeTime, (int) noPayment.get(changeTime) + 1);
                    break;
                case "SEND":
                case "AFFIRM":
                    LinkedHashMap<String, Object> Prepaid = (LinkedHashMap<String, Object>)map.get("已支付订单");
                    if (!Prepaid.containsKey(changeTime)) {
                        Prepaid.put(changeTime, 0);
                    }
                    Prepaid.replace(changeTime, (int) Prepaid.get(changeTime) + 1);
                    break;
                case "REVIEW":
                case "FINISH":
                    LinkedHashMap<String, Object> Finish = (LinkedHashMap<String, Object>)map.get("已完成订单");
                    if (!Finish.containsKey(changeTime)) {
                        Finish.put(changeTime, 0);
                    }
                    Finish.replace(changeTime, (int) Finish.get(changeTime) + 1);
                    break;
            }
        }
        return new JSONObject(map);
    }
}
