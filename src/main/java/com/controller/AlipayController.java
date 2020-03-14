package com.controller;

import com.Util.AlipayConfig;
import com.Util.AlipayUtil;
import com.Util.StateCode;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.pojo.PayTable;
import com.service.OrderTableService;
import com.service.PayTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Creator Ming
 * @Time 2019/8/24
 * @Other 支付宝接口
 */
@Controller
public class AlipayController {

    @Autowired
    private PayTableService payTableService;
    @Autowired
    private OrderTableService orderTableService;

    //支付状态页
    @RequestMapping(value = "/payState", method = RequestMethod.GET)
    private String payState() {
        return "pay/payState";
    }

    //支付请求发起
    @RequestMapping(value = "/pay/alipay/{orderString}", method = RequestMethod.GET)
    private void payment(@PathVariable("orderString") String orderString, HttpServletResponse response) {
        try {
            String orderList[] = orderString.split("&");
            if (orderList.length > 0) {
                //订单支付ID
                String uuid = "";
                float price = 0;

                //存放未创建的订单
                List<String> payTableNullList = new ArrayList<>();
                //校验支付号是否统一
                for (String order : orderList) {
                    //判断是否创建商品订单号
                    if (orderTableService.isEmptyId(order)) {
                        return;
                    }
                    //判断支付订单是否已创建
                    if (payTableService.isPayTableAndNotPay(order)) {
                        String db_uuid = payTableService.getPayId(order);
                        if (!uuid.isEmpty()) {
                            //有两个不相同的订单号,或订单状态不是未支付 则退出
                            if (!uuid.equals(db_uuid) || !"PAYMENT".equals(payTableService.getPayState(order))) {
                                return;
                            }
                        } else {
                            uuid = payTableService.getPayId(order);
                        }
                        price += orderTableService.getOrderPrice(order);
                    } else {
                        payTableNullList.add(order);
                    }
                }

                for (String order : payTableNullList) {
                    if (uuid.isEmpty()) {
                        uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                    }
                    PayTable payTable = new PayTable();
                    payTable.setOrderId(order);
                    payTable.setPayId(uuid);
                    payTable.setState("PAYMENT");
                    payTableService.createPayTable(payTable);
                    price += orderTableService.getOrderPrice(order);
                }

                if (0 != price) {
                    //cookie保存支付订单号,金额
                    Cookie cookie = new Cookie("payID_price", uuid + "&" + price);
                    cookie.setPath("/");
                    cookie.setMaxAge(60 * 30);//过期时间30min
                    response.addCookie(cookie);
                    //调用支付宝
                    AlipayClient alipayClient =
                            new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type); // 获得初始化的AlipayClient
                    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
                    alipayRequest.setReturnUrl(AlipayConfig.return_url);
                    alipayRequest.setNotifyUrl(AlipayConfig.notify_url);// 在公共参数中设置回跳和通知地址
                    alipayRequest.setBizContent("{"
                            + "\"out_trade_no\":\"" + uuid + "\","
                            + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
                            + "\"total_amount\":" + price + ","
                            + "\"subject\":\"订单支付\","
                            + "\"goods_type\":\"1\","
                            + "\"timeout_express\":\"30m\""
                            + "}");// 填充业务参数

                    String form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单

                    response.setContentType("text/html;charset=" + AlipayConfig.charset);
                    response.getWriter().write(form);// 直接将完整的表单html输出到页面
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //同步回调
    @RequestMapping(value = "returnUrl", method = RequestMethod.GET)
    private String returnUrl(HttpServletRequest request, HttpServletResponse response) {
        int payStateCode = 0;
        Map<String, String> params = AlipayUtil.getParams(request);
        try {
            //验签
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
            if (signVerified) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if ("payID_price".equals(cookie.getName())) {
                        String value = cookie.getValue();
                        if (value.isEmpty()) {
                            payStateCode = StateCode.ERR_PAY_PARAM_FAIL_IN;
                            break;
                        }
                        String[] str = value.split("&");
                        String uuid = str[0];
                        float price = Float.parseFloat(str[1]);
                        if (uuid.equals(request.getParameter("out_trade_no"))
                                && price == Float.parseFloat(request.getParameter("total_amount"))) {
                            payStateCode = StateCode.PAY_SUCCESS;
                        } else {
                            payStateCode = StateCode.ERR_PAY_VERIFY_FAIL;
                        }
                    }
                }
                //无法到指定cookie
                if (payStateCode == 0) {
                    payStateCode = StateCode.ERR_PAY_PARAM_FAIL;
                }
            } else {
                payStateCode = StateCode.ERR_PAY_SIGN_VERIFIED_FAIL;
            }
        } catch (AlipayApiException e) {
            payStateCode = StateCode.ERR_PAY_SIGN_VERIFIED_FAIL;
        } finally {
            Cookie cookie = new Cookie("payStateCode", String.valueOf(payStateCode));
            cookie.setPath("/");
            cookie.setMaxAge(60 * 5);
            response.addCookie(cookie);
            return "redirect:/payState";
        }
    }

    //异步回调
    @RequestMapping(value = "notifyUrl", method = RequestMethod.POST)
    private void notifyUrl(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = AlipayUtil.getParams(request);
        try {
            //验签
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
            if (signVerified) {
                //验证APP_ID
                if (!AlipayConfig.app_id.equals(request.getParameter("app_id"))) {
                    return;
                }
                String trade_status = request.getParameter("trade_status");
                if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                    String payId = request.getParameter("out_trade_no");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date payTime = sdf.parse(request.getParameter("gmt_payment"));

                    List<PayTable> payTableList = payTableService.getPayIdList(payId);
                    for (PayTable payTable : payTableList) {
                        String orderId = payTable.getOrderId();
                        String state = payTable.getState();
                        if ("PAYMENT".equals(state) || state.isEmpty()) {
                            payTableService.setPayState(payId, "SEND");//设置待发货
                        }
                        orderTableService.setOrderPayTime(orderId, payTime);
                    }

                    response.getWriter().write("success");
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
