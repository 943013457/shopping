package com.service;

import com.alibaba.fastjson.JSONObject;
import com.pojo.example.ReviewExample;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other
 */
public interface ReviewService {
    public JSONObject getReviewCountJson(int id);

    public List<String> getReviewJson(int id);

    public boolean addReview(String userName, int productId, String reviewText);

    JSONObject getProductReviewList(ReviewExample reviewExample, int page, int limit);

}
