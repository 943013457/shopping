package com.service;

import com.pojo.OrderTable;

import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/8/23
 * @Other
 */

public interface OrderTableService {

    public int insertOrder(OrderTable orderTable);

    public float getOrderPrice(String id);

    public int setOrderPayTime(String id, Date date);

    public boolean isEmptyId(String id);
}
