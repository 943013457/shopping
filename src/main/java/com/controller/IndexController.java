package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other 首页跳转, 页面内容加载
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    private String index() {
        return "index";
    }

}
