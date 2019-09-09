package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/9.
 */
public class RightNowBuyBean {
    private String Money2Balance;
    private String Money5Balance;
    private String Money1Balance;
    private String Money3Balance;
    private String Integral;
    private String GoodsAmount;
    private String Discount;
    private String OrderAmount;
    private String ShippingFree;
    private String Weight;
    private ArrayList<AddressBean> Address;
    private ArrayList<RightNowGoodsBean> list;

    public String getMoney2Balance() {
        return Money2Balance;
    }

    public void setMoney2Balance(String money2Balance) {
        Money2Balance = money2Balance;
    }

    public String getMoney5Balance() {
        return Money5Balance;
    }

    public void setMoney5Balance(String money5Balance) {
        Money5Balance = money5Balance;
    }

    public String getMoney1Balance() {
        return Money1Balance;
    }

    public void setMoney1Balance(String money1Balance) {
        Money1Balance = money1Balance;
    }

    public String getMoney3Balance() {
        return Money3Balance;
    }

    public void setMoney3Balance(String money3Balance) {
        Money3Balance = money3Balance;
    }

    public String getIntegral() {
        return Integral;
    }

    public void setIntegral(String integral) {
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

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getShippingFree() {
        return ShippingFree;
    }

    public void setShippingFree(String shippingFree) {
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

    public ArrayList<RightNowGoodsBean> getList() {
        return list;
    }

    public void setList(ArrayList<RightNowGoodsBean> list) {
        this.list = list;
    }
}
