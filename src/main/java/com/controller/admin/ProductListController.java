package com.controller.admin;

import com.Util.JsonLink;
import com.alibaba.fastjson.JSONObject;
import com.pojo.example.ProductExample;
import com.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;


/**
 * @Creator Ming
 * @Time 2020/3/27
 * @Other
 */
@Controller
public class ProductListController {
    @Autowired
    private ProductService productService;

    //后台管理-商品管理-商品基本信息
    @RequestMapping(value = {"/adminGetProduct"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "商品基本信息")
    private String adminGetProduct(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "productId", required = false) String productId,
                                   @RequestParam(value = "productName", required = false) String productName,
                                   @RequestParam(value = "minPrice", required = false) Float minPrice,
                                   @RequestParam(value = "maxPrice", required = false) Float maxPrice) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        if (productId != null && !"".equals(productId) && productId.matches("^[0-9]*$")) {
            criteria.andIdLike("%" + productId + "%");
        }
        if (productName != null && !"".equals(productName)) {
            criteria.andNameLike("%" + productName + "%");
        }
        if (minPrice != null) {
            criteria.andPromotepriceGreaterThanOrEqualTo(minPrice);
        }
        if (maxPrice != null) {
            criteria.andPromotepriceLessThanOrEqualTo(maxPrice);
        }
        return JsonLink.LayUISuccess(productService.getProductList(productExample, page, limit));
    }

    //后台管理-商品管理-商品删除
    @RequestMapping(value = {"/adminProductDelete"}, method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "商品基本信息")
    private String adminProductDelete(@RequestBody JSONObject jsonObject) {
        String idList = jsonObject.getString("idList");
        int count = 0;
        try {
            List<Integer> list = Arrays.stream(idList.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            count = productService.deleteProduct(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count != 0 ? JsonLink.Success(count) : JsonLink.Error("删除失败");
    }
}
