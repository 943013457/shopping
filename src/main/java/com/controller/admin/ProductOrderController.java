package com.controller.admin;

import com.Util.JsonLink;
import com.service.OrderTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Creator Ming
 * @Time 2020/3/28
 * @Other
 */
@Controller
public class ProductOrderController {
    @Autowired
    private OrderTableService orderTableService;

    //后台管理-商品管理-商品订单管理
    @RequestMapping(value = {"/adminGetProductOrder"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "商品订单管理")
    private String adminGetProductOrder(@RequestParam Map<String, String> map) {
        int limit = Integer.parseInt(map.get("limit"));
        int page = Integer.parseInt(map.get("page"));
        map.put("page", String.valueOf((page - 1) * limit));
        return JsonLink.LayUISuccess(orderTableService.getOrderList(map));
    }
}
