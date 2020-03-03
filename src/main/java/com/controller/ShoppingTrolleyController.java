package com.controller;

import com.Util.JsonLink;
import com.Util.StateCode;
import com.Util.UserUtil;
import com.service.MyTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    private String shoppingTrolley(HttpServletResponse response) {
        //防止浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        return "shoppingTrolley";
    }

    @RequestMapping(value = "/getTrolley", produces = "application/json;charset=utf-8" ,method = RequestMethod.GET)
    @ResponseBody
    private String getTrolley() {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        List<String> JsonList = myTrolleyService.getTrolleyJson(userName);
        String json = JsonList.toString();
        return json != null ? JsonLink.Success(json) : JsonLink.Error("获取数据失败");
    }

    @RequestMapping(value = "/deleteItem/{id}",produces = "application/json;charset=utf-8", method = RequestMethod.DELETE)
    @ResponseBody
    private String deleteItem(@PathVariable(name = "id") int id) {
        String userName = UserUtil.getUserName();
        if (userName == null) {
            return JsonLink.Error(StateCode.ERR_NOT_LOGIN);
        }
        return myTrolleyService.deleteItem(userName, id) ? JsonLink.Success(true) : JsonLink.Error(false);
    }
}
