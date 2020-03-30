package com.controller.admin;

import com.Util.JsonLink;
import com.pojo.example.PayTableExample;
import com.service.PayTableService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Creator Ming
 * @Time 2020/3/30
 * @Other
 */
@Controller
public class PayOrderListAdminController {
    @Autowired
    private PayTableService payTableService;

    //后台管理-支付管理-支付订单管理
    @RequestMapping(value = {"/adminGetPayOrderList"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "支付订单管理")
    private String adminGetPayOrderList(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "payId", required = false) String payId,
                                        @RequestParam(value = "orderId", required = false) String orderId) {
        PayTableExample payTableExample = new PayTableExample();
        PayTableExample.Criteria criteria = payTableExample.createCriteria();
        if (payId != null && !"".equals(payId)) {
            criteria.andPayIdLike("%" + payId + "%");
        }
        if (orderId != null && !"".equals(orderId)) {
            criteria.andOrderIdLike("%" + orderId + "%");
        }
        return JsonLink.LayUISuccess(payTableService.getPayOrderList(payTableExample, page, limit));
    }
}
