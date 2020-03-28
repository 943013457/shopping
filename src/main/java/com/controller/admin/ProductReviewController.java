package com.controller.admin;

import com.Util.JsonLink;
import com.pojo.example.ReviewExample;
import com.service.ReviewService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Creator Ming
 * @Time 2020/3/28
 * @Other
 */
@Controller
public class ProductReviewController {
    @Autowired
    private ReviewService reviewService;

    //后台管理-商品管理-商品评价管理
    @RequestMapping(value = {"/adminGetProductReview"}, method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresPermissions(value = "商品评价管理")
    private String adminGetProductReview(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "username", required = false) String username,
                                         @RequestParam(value = "productId", required = false) String productId) {
        ReviewExample reviewExample = new ReviewExample();
        ReviewExample.Criteria criteria = reviewExample.createCriteria();
        if (username != null && !"".equals(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (productId != null && !"".equals(productId)) {
            criteria.andProductIdLike("%" + productId + "%");
        }
        return JsonLink.LayUISuccess(reviewService.getProductReviewList(reviewExample, page, limit));
    }
}
