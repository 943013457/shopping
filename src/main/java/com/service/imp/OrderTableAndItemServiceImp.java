package com.service.imp;

import com.mapper.OrderTableAndItemMapper;
import com.mapper.ProductMapper;
import com.pojo.OrderTableAndItem;
import com.pojo.Product;
import com.service.OrderTableAndItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Creator Ming
 * @Time 2019/10/28
 * @Other
 */
@Service
@Transactional
public class OrderTableAndItemServiceImp implements OrderTableAndItemService {

    @Autowired
    private OrderTableAndItemMapper orderTableAndItemMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean insertOrder(OrderTableAndItem orderTableAndItem) {
        int productId = orderTableAndItem.getProductId();
        Product product = productMapper.selectByPrimaryKey(productId);
        //库存
        int newStock = product.getStock() - orderTableAndItem.getNumber();
        if (newStock < 0) {
            return false;
        }
        Product productStock = new Product();
        productStock.setId(productId);
        productStock.setStock(newStock);
        productMapper.updateByPrimaryKeySelective(productStock);
        return (orderTableAndItemMapper.insertItem(orderTableAndItem) ==
                orderTableAndItemMapper.insertTable(orderTableAndItem));
    }
}
