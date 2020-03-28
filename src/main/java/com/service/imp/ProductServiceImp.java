package com.service.imp;

import com.Util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.ProductMapper;
import com.pojo.Product;
import com.pojo.example.ProductExample;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


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
    public JSONObject getProductJson(int id) {
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

        return jsonObject;
    }

    @Override
    public boolean hasProduct(int id) {
        return productMapper.selectByPrimaryKey(id) != null;
    }

    @Override
    public JSONObject getNameAndPrice(int id) {
        //获取商品信息
        Product product = productMapper.selectByPrimaryKey(id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", product.getName());
        jsonObject.put("price", product.getPromoteprice());

        return jsonObject;
    }

    @Override
    public JSONObject getProductList(ProductExample productExample, int page, int limit) {
        JSONArray jsonArray = new JSONArray();
        int Count = (int) productMapper.countByExample(productExample);
        productExample.setOrderByClause("null limit " + (page - 1) * limit + "," + limit);
        List<Product> list = productMapper.selectByExample(productExample);
        Iterator<Product> iterator = list.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", product.getId());
            jsonObject.put("name", product.getName());
            jsonObject.put("subTittle", product.getSubtitle());
            jsonObject.put("originalPrice", product.getOriginalprice());
            jsonObject.put("promotePrice", product.getPromoteprice());
            jsonObject.put("stock", product.getStock());
            jsonObject.put("sales", product.getSales());
            jsonObject.put("createTime", DateUtil.getTime(product.getCreatetime()));
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);
        jsonObject.put("count", Count);
        return jsonObject;
    }

    @Override
    public int deleteProduct(List<Integer> list) {
        ProductExample productExample = new ProductExample();
        productExample.or().andIdIn(list);
        return productMapper.deleteByExample(productExample);
    }

}

