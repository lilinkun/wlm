package com.wlm.wlm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LG on 2019/9/9.
 */
public class RightNowBuyBean<T> implements Serializable {
    private double Money2Balance;//剩余积分
    private double Money5Balance;//剩余余额
    private double Money1Balance;
    private double Money3Balance;
    private int Integral;
    private String GoodsAmount;
    private String Discount;
    private double OrderAmount;
    private double ShippingFree;
    private String Weight;
    private ArrayList<AddressBean> Address;
    private ArrayList<T> list;

    public double getMoney2Balance() {
        return Money2Balance;
    }

    public void setMoney2Balance(double money2Balance) {
        Money2Balance = money2Balance;
    }

    public double getMoney5Balance() {
        return Money5Balance;
    }

    public void setMoney5Balance(double money5Balance) {
        Money5Balance = money5Balance;
    }

    public double getMoney1Balance() {
        return Money1Balance;
    }

    public void setMoney1Balance(double money1Balance) {
        Money1Balance = money1Balance;
    }

    public double getMoney3Balance() {
        return Money3Balance;
    }

    public void setMoney3Balance(double money3Balance) {
        Money3Balance = money3Balance;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public String getGoodsAmount() {
        return GoodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        GoodsAmount = goodsAmount;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        OrderAmount = orderAmount;
    }

    public double getShippingFree() {
        return ShippingFree;
    }

    public void setShippingFree(double shippingFree) {
        ShippingFree = shippingFree;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public ArrayList<AddressBean> getAddress() {
        return Address;
    }

    public void setAddress(ArrayList<AddressBean> address) {
        Address = address;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
