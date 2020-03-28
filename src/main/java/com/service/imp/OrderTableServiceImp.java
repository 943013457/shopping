package com.service.imp;

import com.Util.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.OrderTableMapper;
import com.pojo.OrderTable;
import com.service.OrderTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Override
    public JSONObject getOrderList(Map paramMap) {
        JSONArray jsonArray = new JSONArray();
        List<OrderTable> list = orderTableMapper.selectOrderList(paramMap);
        Iterator<OrderTable> iterator = list.iterator();
        while (iterator.hasNext()){
            OrderTable orderTable =  iterator.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",orderTable.getId());
            jsonObject.put("productName",orderTable.getProductName());
            jsonObject.put("userName",orderTable.getUsername());
            jsonObject.put("address",orderTable.getAddress());
            jsonObject.put("receiver",orderTable.getReceiver());
            jsonObject.put("phone",orderTable.getPhone());
            jsonObject.put("userMessage",orderTable.getUsermessage());
            jsonObject.put("price",orderTable.getPrice());
            jsonObject.put("number",orderTable.getNumber());
            jsonObject.put("createDate", DateUtil.getTime(orderTable.getCreatedate()));
            jsonObject.put("payDate",DateUtil.getTime(orderTable.getPaydate()));
            jsonObject.put("deliveryDate",DateUtil.getTime(orderTable.getDeliverydate()));
            jsonObject.put("confirmDate",DateUtil.getTime(orderTable.getConfirmdate()));
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",jsonArray);
        jsonObject.put("count",orderTableMapper.countOrderList(paramMap));
        return jsonObject;
    }
}
