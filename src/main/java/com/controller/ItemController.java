package com.controller;

import com.Util.JsonLink;
import com.service.*;
import org.apache.shiro.SecurityUtils;
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
    @RequestMapping(value = "/getProductJson", produces = "application/text;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getProductJson(@RequestParam(name = "pid") int pid) {
        return JsonLink.put(productService.getProductJson(pid), reviewService.getReviewCountJson(pid));
    }

    //商品图片
    @RequestMapping(value = "/getImageJson", produces = "application/text;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getImageJson(@RequestParam(name = "pid") int pid) {
        return productImageService.getUrlJson(pid);
    }

    //商品基本参数
    @RequestMapping(value = "/getPropertyJson", produces = "application/text;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getPropertyJson(@RequestParam(name = "pid") int pid) {
        return propertyService.getPropertyJson(pid);
    }

    //商品评论
    @RequestMapping(value = "/getReview", produces = "application/text;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getReview(@RequestParam(name = "pid") int pid) {
        return reviewService.getReviewJson(pid);
    }

    //加入购物车
    @RequestMapping(value = "/addTrolley", produces = "application/text;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String addTrolley(@RequestBody Map<String, String> map) {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            return "-1";
        }
        int productID = Integer.parseInt(map.get("productID"));
        int number = Integer.parseInt(map.get("number"));
        return String.valueOf(myTrolleyService.insertItem(name.toString(), productID, number));
    }

    //获取是否加入购物车
    @RequestMapping(value = "/getIsAddTrolley", produces = "application/text;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getIsAddTrolley(@RequestParam(name = "pid") int pid) {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            return "NotLogin";
        }
        return myTrolleyService.isAddTrolley(name.toString(), pid) ? "true" : "false";
    }
}
