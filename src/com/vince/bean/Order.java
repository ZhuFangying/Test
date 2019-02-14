package com.vince.bean;

import com.vince.utils.OrderStatusType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private int orderId;
    private List<OrderItem> orderItemList = new ArrayList<>();
    private String creatDate;//订单时间
    private float sum;//总金额
    private OrderStatusType status = OrderStatusType.UNPAID;//状态（枚举类型
    private int userId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Order() {
    }

    public Order(int orderId, List<OrderItem> orderItemList, String creatDate, float sum, OrderStatusType status, int userId) {
        this.orderId = orderId;
        this.orderItemList = orderItemList;
        this.creatDate = creatDate;
        this.sum = sum;
        this.status = status;
        this.userId = userId;
    }
}
