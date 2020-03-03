package com.test;

import com.Util.JsonLink;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapper.LoginUserMapper;
import com.mapper.ProductMapper;
import com.mapper.PropertyMapper;
import com.pojo.LoginUser;
import com.pojo.Product;
import com.pojo.Property;
import com.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring/Spring-*.xml")
public class LoginUserTest {
    @Autowired
    private LoginService service;
    @Autowired
    private LoginUserMapper mapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void test() {
//        LoginUserExample exc = new LoginUserExample();
//        exc.or().andUserEqualTo("123");
//        exc.or().andPasswordEqualTo("123");
//        System.out.println(mapper.selectByPassword("123"));
//        System.out.println(service.SelectUserPassword("1234"));

//        System.out.println(mapper.selectByPrimaryKey("123").getState());

//        LoginUser u = new LoginUser();
//        u.setUser("1234");
//        u.setPassword("123123");
//        u.setEmail("1213124");
//        u.setPhone("21312412");
//        u.setState(true);
//        mapper.insertSelective(u);
        Product product = productMapper.selectByPrimaryKey(101);
        List<Property> propertyList = propertyMapper.selectProperty(101);

        Iterator<Property> iterator = propertyList.iterator();
        JSONObject jsonObject = new JSONObject();
        while (iterator.hasNext()) {
            Property p = iterator.next();
            jsonObject.put(p.getName(), p.getValue());
        }
        List<String> l = new ArrayList<>();
        l.add(JSON.toJSONString(product));
        l.add(jsonObject.toJSONString());
        l.add(JSON.toJSONString(product));
        String s = JsonLink.putAll(l);
        System.out.println(s);
//        System.out.println(JSON.toJSONString(product));
    }
}
