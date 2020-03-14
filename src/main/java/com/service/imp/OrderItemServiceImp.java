package com.service.imp;

import com.Util.StateCode;
import com.Util.ToDate;
import com.alibaba.fastjson.JSONObject;
import com.mapper.*;
import com.pojo.*;
import com.pojo.example.OrderItemExample;
import com.pojo.example.ProductImageExample;
import com.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/16
 * @Other 我的订单
 */
@Service
@Transactional
public class OrderItemServiceImp implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;

    //下单时间，订单号，商品名，图片，价格，数量，实付款,订单状态
    @Override
    public List<String> getOrderItemJson(String username) {
        List<String> list = new ArrayList<>();

        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.or().andUsernameEqualTo(username);
        List<OrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
        Iterator<OrderItem> itemIterator = orderItemList.iterator();

        while (itemIterator.hasNext()) {
            JSONObject jsonObject = new JSONObject();
            //订单号，数量,商品id
            OrderItem orderItem = itemIterator.next();
            jsonObject.put("OrderId", orderItem.getOrderId());
            jsonObject.put("Number", orderItem.getNumber());
            jsonObject.put("ProductId", orderItem.getProductId());
            //创建时间，价格
            jsonObject.put("CreateDate", ToDate.getTime(orderItem.getCreatedate()));
            jsonObject.put("Price", orderItem.getPrice());
            //商品状态
            jsonObject.put("State", orderItem.getState());
            //商品名字
            jsonObject.put("name", orderItem.getName());
            //取第一张缩略图
            String img = orderItem.getImage();
            jsonObject.put("image", img != null ? img : "/image/default_item_img.jpg");

            list.add(jsonObject.toJSONString());
        }
        return list;
    }


    @Override
    public boolean deleteOrderItem(String orderId, String name) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(orderId);
        if (orderItem != null) {
            int productId = orderItem.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            //库存+
            int stock = product.getStock();
            Product newProduct = new Product();
            newProduct.setId(productId);
            newProduct.setStock(stock + orderItem.getNumber());
            productMapper.updateByPrimaryKeySelective(newProduct);
        }
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.or().andOrderIdEqualTo(orderId).andUsernameEqualTo(name);
        return orderItemMapper.deleteByExample(orderItemExample) > 0;
    }

    @Override
    public int getProductId(String orderId) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(orderId);
        return orderItem.getProductId();
    }
}
