package com.controller;

import com.Util.*;
import com.pojo.OrderTableAndItem;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private MyTrolleyService myTrolleyService;
    @Autowired
    private OrderTableAndItemService orderTableAndItemService;

    @RequestMapping(value = "/submitOrder")
    private String submitOrder() {
        return "submitOrder";
    }

    @RequestMapping(value = "/getOrderJson", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String getOrderJson(@RequestBody List<Map<String,String>> list) {
        List<String> retList = new ArrayList<>();
        Iterator<Map<String,String>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map map = iterator.next();
            //统一字符串
            int pid = Integer.parseInt(map.get("pid").toString());

            List<String> jsonList = new ArrayList<>();
            jsonList.add(productService.getNameAndPrice(pid).toJSONString());
            jsonList.add(productImageService.getFirstImgJson(pid).toJSONString());
            jsonList.add("{\"number\":" + map.get("number").toString() + "}");
            jsonList.add("{\"pid\":" + pid + "}");

            retList.add(JsonLink.putAll(jsonList));
        }
        return JsonLink.Success(retList.toString());
    }

    @RequestMapping(value = "/createOrder", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String createOrder(@RequestBody List<Map<String,String>> list) {
        String userName = UserUtil.getUserName();
        if(userName == null){
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        //删除购物车对应商品（如果有） 生成商品订单/生成用户订单 减去库存
        List<String> uuid_list = new ArrayList<>();
        Iterator<Map<String,String>> iterator = list.iterator();
        while (iterator.hasNext()){
            Map<String,String> map = iterator.next();
            int productId = Integer.parseInt(map.get("productId"));
            String uuid = KeyUtil.generateUniqueKey();
            String address = map.get("address");
            String receiver = map.get("receiver");
            String phone = map.get("phone");
            String userMessage = map.get("userMessage");
            float price = Float.parseFloat(map.get("price"));
            int number = Integer.parseInt(map.get("number"));

            myTrolleyService.deleteItem(userName, productId);
            //生成订单
            OrderTableAndItem orderTableAndItem = new OrderTableAndItem();
            orderTableAndItem.setId(uuid);
            orderTableAndItem.setAddress(address);
            orderTableAndItem.setReceiver(receiver);
            orderTableAndItem.setPhone(phone);
            orderTableAndItem.setUsermessage(userMessage);
            orderTableAndItem.setPrice(price);
            orderTableAndItem.setCreatedate(new Date());
            orderTableAndItem.setUsername(userName);
            orderTableAndItem.setProductId(productId);
            orderTableAndItem.setNumber(number);

            if(!orderTableAndItemService.insertOrder(orderTableAndItem)){
                return JsonLink.Error(StateCode.ERR_NOT_ORDER);
            }
            uuid_list.add(uuid);
        }
        UrlLink.setSeparator("&");
        return JsonLink.Success(UrlLink.putAll(uuid_list));
    }
}
