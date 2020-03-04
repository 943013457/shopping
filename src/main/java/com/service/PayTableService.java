package com.service;

import com.pojo.PayTable;

import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/24
 * @Other 支付状态，订单号
 */
public interface PayTableService {
    public int createPayTable(PayTable payTable);

    public boolean isPayTableAndNotPay(String orderId);

    public String getPayId(String orderId);

    public List<PayTable> getPayIdList(String payId);

    public String getPayState(String orderId);

    public int setPayState(String payId, String state);

    public boolean setAffirmState(String orderId);

    public boolean setFinishState(String orderId);
}
