package com.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.mapper.ProductImageMapper;
import com.mapper.ProductMapper;
import com.mapper.TrolleyMapper;
import com.pojo.Product;
import com.pojo.ProductImage;
import com.pojo.Trolley;
import com.pojo.example.ProductImageExample;
import com.pojo.example.TrolleyExample;
import com.service.MyTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/20
 * @Other 我的购物车
 */

@Service
@Transactional
public class MyTrolleyServiceImp implements MyTrolleyService {
    @Autowired
    private TrolleyMapper trolleyMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public List<String> getTrolleyJson(String username) {
        TrolleyExample trolleyExample = new TrolleyExample();
        trolleyExample.or().andUserEqualTo(username);
        List<Trolley> trolleyList = trolleyMapper.selectByExample(trolleyExample);

        List<String> list = new ArrayList<>();

        Iterator<Trolley> iterator = trolleyList.iterator();
        while (iterator.hasNext()) {
            Trolley trolley = iterator.next();
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", trolley.getProductId());
            jsonObject.put("number", trolley.getNumber());

            ProductImageExample productImageExample = new ProductImageExample();
            productImageExample.or().andProductIdEqualTo(trolley.getProductId()).andLocationEqualTo("small");
            List<ProductImage> productImageList = productImageMapper.selectByExample(productImageExample);
            String default_img = "image/default_item_img.jpg";
            jsonObject.put("img", productImageList.size() > 0 ? productImageList.get(0).getImage() : default_img);

            Product product = productMapper.selectByPrimaryKey(trolley.getProductId());
            jsonObject.put("name", product.getName());
            jsonObject.put("originalprice", product.getOriginalprice());
            jsonObject.put("promoteprice", product.getPromoteprice());

            list.add(jsonObject.toString());
        }

        return list;
    }

    @Override
    public int deleteItem(String username, int productId) {
        TrolleyExample trolleyExample = new TrolleyExample();
        trolleyExample.or().andUserEqualTo(username).andProductIdEqualTo(productId);
        return trolleyMapper.deleteByExample(trolleyExample);
    }

    @Override
    public int insertItem(String username, int productID, int number) {
        Trolley trolley = new Trolley();
        trolley.setNumber(number);
        //添加前确认是否已存在
        TrolleyExample example = new TrolleyExample();
        example.or().andUserEqualTo(username).andProductIdEqualTo(productID);
        if (1 == trolleyMapper.updateByExampleSelective(trolley, example)) {
            return 1;
        }

        trolley.setUser(username);
        trolley.setProductId(productID);
        return trolleyMapper.insertSelective(trolley);
    }

    @Override
    public boolean isAddTrolley(String username, int productID) {
        TrolleyExample trolleyExample = new TrolleyExample();
        trolleyExample.or().andUserEqualTo(username).andProductIdEqualTo(productID);
        if (trolleyMapper.countByExample(trolleyExample) <= 0) {
            return false;
        }
        return true;
    }
}
