package com.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.mapper.ProductMapper;
import com.pojo.Product;
import com.pojo.example.ProductExample;
import com.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/11
 * @Other
 */
@Service
public class SearchServiceImp implements SearchService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<String> getProductList(String name, String sort, int page, int num) {
        List<String> list = new ArrayList<>();
        int page_min = (page - 1) * num;
        String limit = " limit " + page_min + "," + num;
        //模糊查询
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andNameLike("%" + name + "%");
        productExample.or(productExample.createCriteria().andSubtitleLike("%" + name + "%"));
        productExample.setOrderByClause(sort + limit);

        List<Product> productList = productMapper.searchList(productExample);
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();

            String images = product.getImage();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", product.getId());
            jsonObject.put("name", product.getName());
            jsonObject.put("promoteprice", product.getPromoteprice());
            jsonObject.put("sales", product.getSales());
            jsonObject.put("review", product.getReview());
            jsonObject.put("images", images != null ? images : "/image/default_item_img.jpg");

            list.add(jsonObject.toString());
        }
        return list;
    }

    @Override
    public long getProductListCount(String name) {
        //模糊查询
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andNameLike("%" + name + "%");
        productExample.or(productExample.createCriteria().andSubtitleLike("%" + name + "%"));
        return productMapper.countByExample(productExample);
    }
}
