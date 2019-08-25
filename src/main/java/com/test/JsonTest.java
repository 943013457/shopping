package com.test;

import com.Util.JsonLink;
import com.Util.UsernameUtil;
import com.service.OrderItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring/Spring-*.xml")
public class JsonTest {
    @Autowired
    private OrderItemService orderItemService;

    @Test
    public void test() {
//        List<String> JsonList = orderItemService.getOrderItemJson("123");
//        Date date = new Date();
//        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
//        date.setTime(4);
//        System.out.println(sdf.format(date));
    }
}

