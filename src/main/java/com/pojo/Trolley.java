package com.pojo;

public class Trolley {
    private Integer id;

    private String user;

    private Integer productId;

    private Integer number;

    private String image;

    private String name;

    private Float originalprice;

    private Float promoteprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(Float originalprice) {
        this.originalprice = originalprice;
    }

    public Float getPromoteprice() {
        return promoteprice;
    }

    public void setPromoteprice(Float promoteprice) {
        this.promoteprice = promoteprice;
    }
}