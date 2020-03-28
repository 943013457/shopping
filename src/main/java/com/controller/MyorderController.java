package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.Util.UserUtil;
import com.service.OrderItemService;
import com.service.OrderTableService;
import com.service.PayTableService;
import com.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 我的订单
 */
@Controller
@Transactional
public class MyorderController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private PayTableService payTableService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderTableService orderTableService;

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
        if (userName == null) {
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

    @RequestMapping(value = "/affirmGoods", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String affirmGoods(@RequestBody Map<String, String> map) {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        String order_id = map.get("order_id");
        orderTableService.setOrderPayTime(order_id, new Date());
        return payTableService.setAffirmState(order_id) ? JsonLink.Success(true) : JsonLink.Error(false);
    }

    @RequestMapping(value = "/submitReview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String submitReview(@RequestBody Map<String, String> map) {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        String orderId = map.get("orderId");
        int productId = orderItemService.getProductId(orderId);
        if (productId == 0) {
            return JsonLink.Error(StateCode.ERR_NOT_ORDER_ID);
        }
        if (!payTableService.setFinishState(orderId)) {
            return JsonLink.Error(StateCode.ERR_NOT_STATE);
        }
        return reviewService.addReview(userName, productId, map.get("reviewText")) ? JsonLink.Success(true) : JsonLink.Error(false);
    }

}
