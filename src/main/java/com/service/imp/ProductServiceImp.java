package com.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapper.ProductMapper;
import com.pojo.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other 返回页面参数
 */
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public String getProductJson(int id) {
        //获取商品信息
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", product.getName());
        jsonObject.put("subtitle", product.getSubtitle());
        jsonObject.put("originalprice", product.getOriginalprice());
        jsonObject.put("promoteprice", product.getPromoteprice());
        jsonObject.put("sales", product.getSales());
        jsonObject.put("stock", product.getStock());

        return jsonObject.toString();
    }

    @Override
    public boolean hasProduct(int id) {
        if (null == productMapper.selectByPrimaryKey(id)) {
            return false;
        }
        return true;
    }

    @Override
    public String getNameAndPrice(int id) {
        //获取商品信息
        Product product = productMapper.selectByPrimaryKey(id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", product.getName());
        jsonObject.put("price", product.getPromoteprice());

        return jsonObject.toString();
    }

}

