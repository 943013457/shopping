package com.controller;

import com.Util.JsonLink;
import com.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2020/3/10
 * @Other
 */
@Controller
public class SearchController {
    private final String pageNumber = "30";//每页默认显示数据数

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search")
    private String search() {
        return "search";
    }

    @RequestMapping(value = "/getSearchList", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getSearchList(@RequestParam(name = "productName") String productName,
                                 @RequestParam(name = "sort", required = false, defaultValue = "null") String sort,
                                 @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(name = "num", required = false, defaultValue = pageNumber) int num) {
        List<String> list = searchService.getProductList(productName, sort, page, num);
        return list.size() > 0 ? JsonLink.Success(list) : JsonLink.Error("无数据");
    }

    //获取总页数
    @RequestMapping(value = "/getSearchTotalPage", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    private String getSearchTotalPage(@RequestParam(name = "productName") String productName) {
        long count = searchService.getProductListCount(productName);
        double page = Math.ceil(count / Float.parseFloat(pageNumber));//求总页数 向上取整
        return JsonLink.Success(page);
    }
}
