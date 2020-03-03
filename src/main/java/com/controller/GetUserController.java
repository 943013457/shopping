package com.controller;

import com.Util.JsonLink;
import com.Util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other
 */
@Controller
public class GetUserController {

    @RequestMapping(value = {"/getUser"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String getUser() {
        String name = UserUtil.getUserName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name != null ? name : "");
        return JsonLink.Success(jsonObject.toJSONString());
    }
}
