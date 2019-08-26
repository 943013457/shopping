package com.service.imp;

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
    private OrderTableMapper orderTableMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private PayTableMapper payTableMapper;

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
            OrderTable orderTable = orderTableMapper.selectByPrimaryKey(orderItem.getOrderId());
            jsonObject.put("CreateDate", ToDate.getTime(orderTable.getCreatedate()));
            jsonObject.put("Price", orderTable.getPrice());

            //商品状态
            PayTable payTable = payTableMapper.selectByPrimaryKey(orderItem.getOrderId());
            jsonObject.put("State", payTable != null ? payTable.getState() : null);

            //商品名字
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            jsonObject.put("name", product.getName());

            ProductImageExample productImageExample = new ProductImageExample();
            //取第一张缩略图
            productImageExample.or().andProductIdEqualTo(orderItem.getProductId()).andLocationEqualTo("small");
            List<ProductImage> productImageList = productImageMapper.selectByExample(productImageExample);
            String default_img = "/image/default_item_img.jpg";
            jsonObject.put("image", productImageList.size() > 0 ? productImageList.get(0).getImage() : default_img);

            list.add(jsonObject.toJSONString());
        }
        return list;
    }

    @Override
    public int insertOrderItem(OrderItem orderItem) {
        return orderItemMapper.insert(orderItem);
    }

    @Override
    public int getOrderNumber(String orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem = orderItemMapper.selectByPrimaryKey(orderId);
        return orderItem != null ? orderItem.getNumber() : 0;
    }

    @Override
    public int deleteOrderItem(OrderItemExample orderItemExample) {
        return orderItemMapper.deleteByExample(orderItemExample);
    }
}
