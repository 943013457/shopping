package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PayTableLog {
    private Integer id;

    private Date changetime;

    private String payid;

    private String orderid;

    private String beforestate;

    private String laterstate;

    private BigDecimal price;

    private String mark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getChangetime() {
        return changetime;
    }

    public void setChangetime(Date changetime) {
        this.changetime = changetime;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid == null ? null : payid.trim();
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getBeforestate() {
        return beforestate;
    }

    public void setBeforestate(String beforestate) {
        this.beforestate = beforestate == null ? null : beforestate.trim();
    }

    public String getLaterstate() {
        return laterstate;
    }

    public void setLaterstate(String laterstate) {
        this.laterstate = laterstate == null ? null : laterstate.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }
}