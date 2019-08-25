package com.service;

import com.pojo.OrderItem;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 我的订单
 */

public interface OrderItemService {
    public List<String> getOrderItemJson(String username);

    public int insertOrderItem(OrderItem orderItem);

    public int getOrderNumber(String orderId);
}
