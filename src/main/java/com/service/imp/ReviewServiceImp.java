package com.service.imp;

import com.Util.ToDate;
import com.Util.UsernameUtil;
import com.alibaba.fastjson.JSONObject;
import com.mapper.ReviewMapper;
import com.pojo.Review;
import com.pojo.example.ReviewExample;
import com.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public String getReviewCountJson(int id) {
        //获取评论数
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.or().andProductIdEqualTo(id);
        long reviewCount = reviewMapper.countByExample(reviewExample);
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("review", reviewCount);
        return reviewJson.toString();
    }

    @Override
    public String getReviewJson(int id) {
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
            jsonObject.put("username", UsernameUtil.ToAnonymity(review.getUsername()));
            jsonObject.put("createDate", ToDate.getTime(review.getCreatedate()));
            list.add(jsonObject.toString());
        }
        return list.toString();
    }
}
