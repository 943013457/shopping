package com.service.imp;

import com.Util.DateUtil;
import com.Util.UserUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.ReviewMapper;
import com.pojo.Review;
import com.pojo.example.ReviewExample;
import com.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 获取评论
 */
@Service
public class ReviewServiceImp implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public JSONObject getReviewCountJson(int id) {
        //获取评论数
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.or().andProductIdEqualTo(id);
        long reviewCount = reviewMapper.countByExample(reviewExample);
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("review", reviewCount);
        return reviewJson;
    }

    @Override
    public List<String> getReviewJson(int id) {
        //获取评论
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.or().andProductIdEqualTo(id);
        //时间降序
        reviewExample.setOrderByClause("createDate desc");
        List<Review> reviewList = reviewMapper.selectByExample(reviewExample);

        List<String> list = new ArrayList<>();

        Iterator<Review> iterator = reviewList.iterator();
        while (iterator.hasNext()) {
            Review review = iterator.next();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("content", review.getContent());
            jsonObject.put("username", UserUtil.ToAnonymity(review.getUsername()));
            jsonObject.put("createDate", DateUtil.getTime(review.getCreatedate()));
            list.add(jsonObject.toString());
        }
        return list;
    }

    @Override
    public boolean addReview(String userName, int productId, String reviewText) {
        Review review = new Review();
        review.setUsername(userName);
        review.setProductId(productId);
        review.setContent(reviewText);
        review.setCreatedate(new Date());
        return reviewMapper.insertSelective(review) > 0;
    }

    @Override
    public JSONObject getProductReviewList(ReviewExample reviewExample, int page, int limit) {
        int Count = (int) reviewMapper.countByExample(reviewExample);
        reviewExample.setOrderByClause("null limit " + (page - 1) * limit + "," + limit);
        JSONArray jsonArray = new JSONArray();
        List<Review> reviewList = reviewMapper.selectByExample(reviewExample);
        Iterator<Review> iterator = reviewList.iterator();
        while (iterator.hasNext()){
            Review review = iterator.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId",review.getProductId());
            jsonObject.put("username",review.getUsername());
            jsonObject.put("content",review.getContent());
            jsonObject.put("createDate",DateUtil.getTime(review.getCreatedate()));
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);
        jsonObject.put("count", Count);
        return jsonObject;
    }

}
