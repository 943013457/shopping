package com.controller;

import com.Util.JsonLink;
import com.Util.KeyUtil;
import com.Util.UrlLink;
import com.alibaba.fastjson.JSONObject;
import com.pojo.OrderItem;
import com.pojo.OrderTable;
import com.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @Creator Ming
 * @Time 2019/8/22
 * @Other 提交订单模块
 */
@Controller
public class SubmitOrderController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private OrderTableService orderTableService;
    @Autowired
    private MyTrolleyService myTrolleyService;
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping(value = "/submitOrder")
    private String submitOrder() {
        return "submitOrder";
    }

    @RequestMapping(value = "/getOrderJson", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String getOrderJson(@RequestBody List<JSONObject> list) {
        List<String> retList = new ArrayList<>();
        Iterator<JSONObject> iterator = list.iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = iterator.next();
            //统一字符串
            int pid = Integer.parseInt(jsonObject.get("pid").toString());

            List<String> jsonList = new ArrayList<>();
            jsonList.add(productService.getNameAndPrice(pid));
            jsonList.add(productImageService.getFirstImgJson(pid));
            jsonList.add("{\"number\":" + jsonObject.get("number").toString() + "}");

            retList.add(JsonLink.putAll(jsonList));
        }

        return retList.toString();
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    private String createOrder(@RequestBody List<JSONObject> list, RedirectAttributes attr) {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            //TODO 跳转到错误页
            return "-1";
        }
        //删除购物车对应商品（如果有） 生成商品订单  生成用户订单
        List<String> uuid_list = new ArrayList<>();

        for (Iterator<JSONObject> iterator = list.iterator();iterator.hasNext();) {
            JSONObject jsonObject = iterator.next();
            int productId = Integer.parseInt(jsonObject.get("productId").toString());
            String uuid = KeyUtil.generateUniqueKey();
            String address = jsonObject.get("address").toString();
            String receiver = jsonObject.get("receiver").toString();
            String phone = jsonObject.get("phone").toString();
            String userMessage = jsonObject.get("userMessage").toString();
            float price = Float.parseFloat(jsonObject.get("price").toString());
            int number = Integer.parseInt(jsonObject.get("number").toString());

            myTrolleyService.deleteItem(name.toString(), productId);
            //生成订单
            OrderTable orderTable = new OrderTable();
            orderTable.setId(uuid);
            orderTable.setAddress(address);
            orderTable.setReceiver(receiver);
            orderTable.setPhone(phone);
            orderTable.setUsermessage(userMessage);
            orderTable.setPrice(price);
            orderTable.setCreatedate(new Date());
            orderTable.setUsername(name.toString());
            //生成我的订单
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(uuid);
            orderItem.setUsername(name.toString());
            orderItem.setProductId(productId);
            orderItem.setNumber(number);

            if (1 != orderTableService.insertOrder(orderTable)) {
                //TODO 跳转到错误页
                return "-2";
            }
            if (1 != orderItemService.insertOrderItem(orderItem)) {
                //TODO 跳转到错误页
                return "-3";
            }
            uuid_list.add(uuid);
        }
        UrlLink.setSeparator("&");
        return UrlLink.putAll(uuid_list);
    }
}
