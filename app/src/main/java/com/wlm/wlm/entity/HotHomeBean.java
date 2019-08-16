package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/12/14.
 */
public class HotHomeBean {
    private String goods_id;
    private String goods_name;
    private double shop_price;
    private String use_number;
    private String goods_img;
    private int give_integral;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public String getUse_number() {
        return use_number;
    }

    public void setUse_number(String use_number) {
        this.use_number = use_number;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public int getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(int give_integral) {
        this.give_integral = give_integral;
    }
}
