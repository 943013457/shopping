package com.controller;

import com.service.MyTrolleyService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 购物车
 */
@Controller
public class ShoppingTrolleyController {
    @Autowired
    private MyTrolleyService myTrolleyService;

    @RequestMapping(value = {"/shoppingTrolley"})
    private String shoppingTrolley() {
        return "shoppingTrolley";
    }

    @RequestMapping(value = "/getTrolley", method = RequestMethod.GET)
    private void getTrolley(HttpServletResponse response) throws IOException {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            return;
        }

        List<String> JsonList = myTrolleyService.getTrolleyJson(name.toString());

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JsonList.toString());
    }

    @RequestMapping(value = "/deleteItem/{pid}", method = RequestMethod.GET)
    @ResponseBody
    private int deleteItem(@PathVariable("pid") int pid) {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            return -1;
        }
        return myTrolleyService.deleteItem(name.toString(), pid);
    }
}
