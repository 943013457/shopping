package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.Util.UserUtil;
import com.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 我的订单
 */
@Controller
public class MyorderController {
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping(value = {"/myorder"}, method = RequestMethod.GET)
    private String myorder(HttpServletResponse response) {
        //防止浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        return "myorder";
    }

    @RequestMapping(value = "/getMyorder", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String getMyorder() {
        String userName = UserUtil.getUserName();
        if(userName == null){
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        List<String> JsonList = orderItemService.getOrderItemJson(userName);
        String json = JsonList.toString();
        return json != null ? JsonLink.Success(json) : JsonLink.Error(json);
    }

    @RequestMapping(value = "/deleteOrder/{order_id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String deleteOrder(@PathVariable(name = "order_id") String order_id) {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        return orderItemService.deleteOrderItem(order_id, userName) ?
                JsonLink.Success(true) : JsonLink.Error(false);
    }
}
