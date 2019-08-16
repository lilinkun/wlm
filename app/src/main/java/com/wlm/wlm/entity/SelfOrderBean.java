package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/18.
 */

public class SelfOrderBean {
    private String OrderId;
    private String OrderSn;
    private double MoneyPayid;
    private ArrayList<SelfOrderInfoBean> OrderGoodsItem;
    private int  UseIntegral;
    private double OrderAmount;
    private double ShippingFee;
    private int OrderStatus;
    private String ConfirmTime;
    private StoreModelBean StoreModel;
    private String Effective_Payment_Time;
    private String Erm;

    public String getErm() {
        return Erm;
    }

    public void setErm(String erm) {
        Erm = erm;
    }

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

    public double getMoneyPayid() {
        return MoneyPayid;
    }

    public void setMoneyPayid(double moneyPayid) {
        MoneyPayid = moneyPayid;
    }

    public ArrayList<SelfOrderInfoBean> getOrderGoodsItem() {
        return OrderGoodsItem;
    }

    public void setOrderGoodsItem(ArrayList<SelfOrderInfoBean> orderGoodsItem) {
        OrderGoodsItem = orderGoodsItem;
    }

    public int getUseIntegral() {
        return UseIntegral;
    }

    public void setUseIntegral(int useIntegral) {
        UseIntegral = useIntegral;
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

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getConfirmTime() {
        return ConfirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        ConfirmTime = confirmTime;
    }

    public StoreModelBean getStoreModel() {
        return StoreModel;
    }

    public void setStoreModel(StoreModelBean storeModel) {
        StoreModel = storeModel;
    }

    public String getEffective_Payment_Time() {
        return Effective_Payment_Time;
    }

    public void setEffective_Payment_Time(String effective_Payment_Time) {
        Effective_Payment_Time = effective_Payment_Time;
    }
}
