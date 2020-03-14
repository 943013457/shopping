package com.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.CategoryMapper;
import com.pojo.example.CategoryExample;
import com.pojo.Product;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Creator Ming
 * @Time 2020/3/14
 * @Other 获取首页分类商品
 */
@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public JSONObject getIndexProduct() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andIsIndexEqualTo(1);
        List<Product> productList = categoryMapper.getIndexProduct(categoryExample);

        JSONObject jsonObject = new JSONObject();
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            String categoryName = product.getCategoryName();
            if(!jsonObject.containsKey(categoryName)){
                jsonObject.put(categoryName,new JSONArray());
            }
            JSONArray jsonArray = jsonObject.getJSONArray(categoryName);
            jsonArray.add(product);
        }
        return jsonObject;
    }
}
