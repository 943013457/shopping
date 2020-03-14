package com.pojo;

public class Property {
    private Integer id;

    private Integer productId;

    private String parmKey;

    private String parmValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getParmKey() {
        return parmKey;
    }

    public void setParmKey(String parmKey) {
        this.parmKey = parmKey == null ? null : parmKey.trim();
    }

    public String getParmValue() {
        return parmValue;
    }

    public void setParmValue(String parmValue) {
        this.parmValue = parmValue == null ? null : parmValue.trim();
    }
}