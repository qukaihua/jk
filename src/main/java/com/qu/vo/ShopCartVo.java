package com.qu.vo;

/**
 * Created by Administrator on 2018/6/22 0022.
 */

public class ShopCartVo {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private String productname;

    private String productsubtitle;

    private String productdetail;

    private Double price;

    private  Integer stock;

    private  boolean Limit_Number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductsubtitle() {
        return productsubtitle;
    }

    public void setProductsubtitle(String productsubtitle) {
        this.productsubtitle = productsubtitle;
    }

    public String getProductdetail() {
        return productdetail;
    }

    public void setProductdetail(String productdetail) {
        this.productdetail = productdetail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isLimit_Number() {
        return Limit_Number;
    }

    public void setLimit_Number(boolean limit_Number) {
        Limit_Number = limit_Number;
    }
}