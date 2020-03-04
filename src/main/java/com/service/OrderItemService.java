package com.service;

import com.pojo.OrderItem;
import com.pojo.example.OrderItemExample;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 我的订单
 */

public interface OrderItemService {
    public List<String> getOrderItemJson(String username);

    public boolean deleteOrderItem(String orderId,String name);

    public int getProductId(String orderId);
}
