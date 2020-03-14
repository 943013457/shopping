package com.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.mapper.PropertyMapper;
import com.pojo.Property;
import com.pojo.example.PropertyExample;
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
    public JSONObject getPropertyJson(int id) {
        //获取商品参数
        JSONObject propertyJson = new JSONObject();
        PropertyExample propertyExample = new PropertyExample();
        propertyExample.or().andProductIdEqualTo(id);
        List<Property> propertyList = propertyMapper.selectByExample(propertyExample);
        Iterator<Property> iterator = propertyList.iterator();
        while (iterator.hasNext()) {
            Property p = iterator.next();
            propertyJson.put(p.getParmKey(), p.getParmValue());
        }
        return propertyJson;
    }
}
