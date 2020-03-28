package com.service.imp;

import com.Util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.RoleMapper;
import com.pojo.Role;
import com.pojo.example.RoleExample;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/24
 * @Other
 */
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public JSONObject getRoleList(RoleExample roleExample, int page, int limit) {
        JSONArray jsonArray = new JSONArray();
        int Count = (int) roleMapper.countByExample(roleExample);
        roleExample.setOrderByClause("null limit " + (page - 1) * limit + "," + limit);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        Iterator<Role> iterator = roleList.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", role.getRoleId());
            jsonObject.put("name", role.getRoleName());
            jsonObject.put("createTime", DateUtil.getTime(role.getCreatetime()));
            jsonObject.put("status", role.getState());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);
        jsonObject.put("count", Count);
        return jsonObject;
    }

    @Override
    public int updateRoleState(List<Integer> list, boolean state) {
        RoleExample roleExample = new RoleExample();
        roleExample.or().andRoleIdIn(list);
        Role role = new Role();
        role.setState(state);
        return roleMapper.updateByExampleSelective(role, roleExample);
    }
}
