package com.service.imp;

import com.mapper.OrderTableMapper;
import com.pojo.OrderTable;
import com.service.OrderTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Creator Ming
 * @Time 2019/8/23
 * @Other
 */
@Service
@Transactional
public class OrderTableServiceImp implements OrderTableService {
    @Autowired
    private OrderTableMapper orderTableMapper;

    @Override
    public int insertOrder(OrderTable orderTable) {
        return orderTableMapper.insertSelective(orderTable);
    }

    @Override
    public float getOrderPrice(String id) {
        OrderTable orderTable = orderTableMapper.selectByPrimaryKey(id);
        return orderTable != null ? orderTable.getPrice() : 0;
    }

    @Override
    public int setOrderPayTime(String id, Date date) {
        OrderTable orderTable = new OrderTable();
        orderTable.setId(id);
        orderTable.setPaydate(date);
        return orderTableMapper.updateByPrimaryKeySelective(orderTable);
    }

    @Override
    public boolean isEmptyId(String id) {
        OrderTable orderTable = orderTableMapper.selectByPrimaryKey(id);
        return orderTable == null;
    }
}
