package com.pojo;

public class Category {
    private Integer id;

    private String name;

    private Integer isIndex;

    private String showProduct;

    private Integer productId;

    private String productName;

    private Float promotePrice;

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    public String getShowProduct() {
        return showProduct;
    }

    public void setShowProduct(String showProduct) {
        this.showProduct = showProduct == null ? null : showProduct.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}