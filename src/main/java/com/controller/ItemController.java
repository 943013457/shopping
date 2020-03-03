package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.Util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other 商品详情页
 */
@Controller
public class ItemController {
    @Autowired
    private ProductService productService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private MyTrolleyService myTrolleyService;

    @RequestMapping(value = "/items/{pid}")
    private String items(@PathVariable("pid") int pid) {
        if (!productService.hasProduct(pid)) {
            //TODO 跳转到不存在页面
            return "index";
        }
        return "items";
    }

    //商品基本信息
    @RequestMapping(value = "/getProductJson/{pid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getProductJson(@PathVariable(name = "pid") int pid) {
        JSONObject ProductJson = productService.getProductJson(pid);
        JSONObject ReviewCountJson = reviewService.getReviewCountJson(pid);
        if (ProductJson.isEmpty()) {
            return JsonLink.Error("无数据");
        }
        ProductJson.putAll(ReviewCountJson);
        return JsonLink.Success(ProductJson);
    }

    //商品图片
    @RequestMapping(value = "/getImageJson/{pid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getImageJson(@PathVariable(name = "pid") int pid) {
        return JsonLink.Success(productImageService.getUrlJson(pid));
    }

    //商品基本参数
    @RequestMapping(value = "/getPropertyJson/{pid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getPropertyJson(@PathVariable(name = "pid") int pid) {
        return JsonLink.Success(propertyService.getPropertyJson(pid));
    }

    //商品评论
    @RequestMapping(value = "/getReviewJson/{pid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getReviewJson(@PathVariable(name = "pid") int pid) {
        return JsonLink.Success(reviewService.getReviewJson(pid));
    }

    //加入购物车
    @RequestMapping(value = "/addTrolley", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String addTrolley(@RequestBody Map<String, String> map) {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        int productID = Integer.parseInt(map.get("productID"));
        int number = Integer.parseInt(map.get("number"));
        boolean ret = myTrolleyService.insertItem(userName, productID, number);
        return ret ? JsonLink.Success(true) : JsonLink.Error(false);
    }

    //获取是否加入购物车
    @RequestMapping(value = "/getIsAddTrolley/{pid}", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getIsAddTrolley(@PathVariable(name = "pid") int pid) {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        boolean ret = myTrolleyService.isAddTrolley(userName, pid);
        return ret ? JsonLink.Success(true) : JsonLink.Success(false);
    }
}
