package com.wlm.wlm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单实体
 * Created by LG on 2018/12/18.
 */
public class SelfOrderBean<T> {
    private String OrderId;
    private String OrderSn;
    private String UserId;
    private String UserName;
    private int Integral;
    private int OrderStatus;
    private String OrderStatusName;
    private String Consignee;
    private int OrderType;
    private String Address;
    private String Mobile;
    private double OrderAmount;
    private double ShippingFee;
    private String ConfirmTime;
    private String CreateDate;
    private ArrayList<SelfOrderInfoBean> list;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String orderSn) {
        OrderSn = orderSn;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        OrderStatusName = orderStatusName;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        OrderAmount = orderAmount;
    }

    public double getShippingFee() {
        return ShippingFee;
    }

    public void setShippingFee(double shippingFee) {
        ShippingFee = shippingFee;
    }

    public String getConfirmTime() {
        return ConfirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        ConfirmTime = confirmTime;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public ArrayList<SelfOrderInfoBean> getList() {
        return list;
    }

    public void setList(ArrayList<SelfOrderInfoBean> list) {
        this.list = list;
    }
}
