package com.service.imp;

import com.mapper.OrderItemMapper;
import com.mapper.PayTableMapper;
import com.pojo.PayTable;
import com.pojo.example.PayTableExample;
import com.service.PayTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/24
 * @Other
 */

@Service
@Transactional
public class PayTableServiceImp implements PayTableService {
    @Autowired
    private PayTableMapper payTableMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public int createPayTable(PayTable payTable) {
        return payTableMapper.insert(payTable);
    }

    @Override
    public boolean isPayTableAndNotPay(String orderId) {
        PayTableExample payTableExample = new PayTableExample();
        payTableExample.or().andOrderIdEqualTo(orderId).andStateEqualTo("PAYMENT");
        List<PayTable> payTableList = payTableMapper.selectByExample(payTableExample);
        return payTableList.size() == 1;
    }

    @Override
    public String getPayId(String orderId) {
        PayTable payTable = payTableMapper.selectByPrimaryKey(orderId);
        return payTable != null ? payTable.getPayId() : null;
    }

    @Override
    public List<PayTable> getPayIdList(String payId) {
        PayTableExample payTableExample = new PayTableExample();
        payTableExample.or().andPayIdEqualTo(payId);
        return payTableMapper.selectByExample(payTableExample);
    }

    @Override
    public String getPayState(String orderId) {
        PayTable payTable = payTableMapper.selectByPrimaryKey(orderId);
        return payTable != null ? payTable.getState() : null;
    }

    @Override
    public int setPayState(String payId, String state) {
        PayTable payTable = new PayTable();
        payTable.setState(state);
        PayTableExample payTableExample = new PayTableExample();
        payTableExample.or().andPayIdEqualTo(payId);
        return payTableMapper.updateByExampleSelective(payTable, payTableExample);
    }

    @Override
    public boolean setAffirmState(String orderId) {
        //确认收货,增加销量
        orderItemMapper.updateBySales(orderId);//增加销量
        PayTable payTable = new PayTable();
        payTable.setOrderId(orderId);
        payTable.setState("REVIEW");
        return payTableMapper.updateByPrimaryKeySelective(payTable) > 0;
    }

    @Override
    public boolean setFinishState(String orderId) {
        //完成订单
        PayTable payTable = new PayTable();
        payTable.setOrderId(orderId);
        payTable.setState("FINISH");
        return payTableMapper.updateByPrimaryKeySelective(payTable) > 0;
    }


}
