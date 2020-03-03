package com.service.imp;

import com.Util.UrlLink;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapper.ProductImageMapper;
import com.pojo.ProductImage;
import com.pojo.example.ProductImageExample;
import com.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 获取图片地址
 */
@Service
public class ProductImageServiceImp implements ProductImageService {
    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public JSONObject getUrlJson(int id) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.or().andProductIdEqualTo(id);
        List<ProductImage> productImageList = productImageMapper.selectByExample(productImageExample);
        //图片
        List<String> topList = new ArrayList<>();
        List<String> smallList = new ArrayList<>();
        List<String> contentList = new ArrayList<>();

        Iterator<ProductImage> iterator = productImageList.iterator();
        while (iterator.hasNext()) {
            ProductImage p = iterator.next();
            String location = p.getLocation().toLowerCase();

            if ("top".equals(location)) {
                topList.add(p.getImage());
            } else if ("small".equals(location)) {
                smallList.add(p.getImage());
            } else if ("content".equals(location)) {
                contentList.add(p.getImage());
            }
        }

        JSONObject jsonObject = new JSONObject();
        UrlLink.setSeparator(",");
        String default_img = "/image/default_item_img.jpg";

        jsonObject.put("top", topList.size() > 0 ? UrlLink.putAll(topList) : default_img);
        jsonObject.put("small", smallList.size() > 0 ? UrlLink.putAll(smallList) : default_img);
        jsonObject.put("content", contentList.size() > 0 ? UrlLink.putAll(contentList) : default_img);

        return jsonObject;
    }

    @Override
    public JSONObject getFirstImgJson(int id) {
        ProductImageExample example = new ProductImageExample();
        example.or().andProductIdEqualTo(id).andLocationEqualTo("small");
        List<ProductImage> list = productImageMapper.selectByExample(example);

        JSONObject jsonObject = new JSONObject();
        String default_img = "/image/default_item_img.jpg";
        jsonObject.put("imgUrl", list.size() > 0 ? list.get(0).getImage() : default_img);

        return jsonObject;
    }
}