package com.controller;

import com.Util.JsonLink;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 首页跳转, 页面内容加载
 */
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/index")
    private String index() {
        return "index";
    }

    @RequestMapping(value = "/getIndexCategory", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    private String getIndexCategory(){
        return JsonLink.Success(categoryService.getIndexProduct());
    }

}
