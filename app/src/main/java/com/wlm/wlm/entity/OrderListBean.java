package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/14.
 */
public class OrderListBean<T> {
    private String store_id;
    private String user_id;
    private String shop_name;
    private String shop_logo;
    private T goodsList;
    private String fare;
    private int max_use_Point;
    private double total_amount;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public T getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(T goodsList) {
        this.goodsList = goodsList;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public int getMax_use_Point() {
        return max_use_Point;
    }

    public void setMax_use_Point(int max_use_Point) {
        this.max_use_Point = max_use_Point;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}
