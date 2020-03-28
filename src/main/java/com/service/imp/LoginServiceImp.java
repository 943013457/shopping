package com.service.imp;


import com.Util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.LoginUserMapper;
import com.mapper.UserRoleMapper;
import com.pojo.LoginUser;
import com.pojo.UserRole;
import com.pojo.example.LoginUserExample;
import com.pojo.example.UserRoleExample;
import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private LoginUserMapper loginUserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    private LoginUser getUser(String user) {
        return loginUserMapper.selectByPrimaryKey(user);
    }

    @Override
    public String SelectUserPassword(String Username) {
        LoginUser user = getUser(Username);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }

    @Override
    public boolean SelectUserState(String Username) {
        LoginUser user = getUser(Username);
        if (user == null || !user.getState()) {
            return false;
        }
        return true;
    }

    @Override
    public String SelectUserSalt(String Username) {
        LoginUser user = getUser(Username);
        if (user == null) {
            return null;
        }
        return user.getSalt();
    }

    @Override
    public JSONObject getUserList(LoginUserExample loginUserExample, LoginUserExample.Criteria criteria, int page, int limit) {
        JSONArray jsonArray = new JSONArray();
        //获取所有普通用户,从用户角色表中获取,不存在的则是普通用户
        List<UserRole> userRoleList = userRoleMapper.selectByExample(new UserRoleExample());
        List<String> userList = new ArrayList<>();
        Iterator<UserRole> iterator = userRoleList.iterator();
        while (iterator.hasNext()) {
            UserRole userRole = iterator.next();
            userList.add(userRole.getUserName());
        }
        criteria.andUserNotIn(userList);
        int Count = (int) loginUserMapper.countByExample(loginUserExample);//获取总数
        loginUserExample.setOrderByClause("null limit " + (page - 1) * limit + "," + limit);
        List<LoginUser> loginUserList = loginUserMapper.selectByExample(loginUserExample);
        if (loginUserList.size() > 0) {
            Iterator<LoginUser> loginUserIterator = loginUserList.iterator();
            while (loginUserIterator.hasNext()) {
                LoginUser loginUser = loginUserIterator.next();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", loginUser.getUser());
                jsonObject.put("role", "普通用户");
                jsonObject.put("email", loginUser.getEmail());
//                jsonObject.put("phone",loginUser.getUser());
                jsonObject.put("createTime", DateUtil.getTime(loginUser.getRegistertime()));
                jsonObject.put("status", loginUser.getState());
                jsonObject.put("registerIp", loginUser.getRegisterIp());
                jsonArray.add(jsonObject);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);
        jsonObject.put("count", Count);
        return jsonObject;
    }

    @Override
    public int UpdateUserState(List<String> userList, boolean state) {
        LoginUserExample loginUserExample = new LoginUserExample();
        loginUserExample.or().andUserIn(userList);
        LoginUser loginUser = new LoginUser();
        loginUser.setState(state);
        return loginUserMapper.updateByExampleSelective(loginUser, loginUserExample);
    }
}
