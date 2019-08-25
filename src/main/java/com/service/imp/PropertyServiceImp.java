package com.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.mapper.PropertyMapper;
import com.pojo.Property;
import com.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 获取商品参数
 */
@Service
public class PropertyServiceImp implements PropertyService {
    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public String getPropertyJson(int id) {
        //获取商品参数
        JSONObject propertyJson = new JSONObject();
        List<Property> propertyList = propertyMapper.selectProperty(id);
        Iterator<Property> iterator = propertyList.iterator();
        while (iterator.hasNext()) {
            Property p = iterator.next();
            propertyJson.put(p.getName(), p.getValue());
        }
        return propertyJson.toJSONString();
    }
}
