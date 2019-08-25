package com.controller;

import com.service.OrderItemService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    private String myorder(Model model) {
        //防止浏览器缓存
        model.addAttribute("ran", UUID.randomUUID().toString().replace("-", "").toUpperCase());
        return "redirect:/html/myorder.html";
    }

    @RequestMapping(value = "/getMyorder",method = RequestMethod.GET)
    private void getMyorder(HttpServletResponse response) throws IOException {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            return;
        }
        List<String> JsonList = orderItemService.getOrderItemJson(name.toString());

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JsonList.toString());
    }
}
