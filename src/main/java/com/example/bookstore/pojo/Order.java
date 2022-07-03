package com.example.bookstore.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Order {
    private String orderId;
    private Timestamp createTime;
    private BigDecimal price;
    private Integer status;
    private String  username;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return username;
    }

    public void setUserId(String userId) {
        this.username = userId;
    }

    public Order(String orderId, Timestamp createTime, BigDecimal price, Integer status, String username) {
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.username = username;
    }

    public Order() {
    }
}
