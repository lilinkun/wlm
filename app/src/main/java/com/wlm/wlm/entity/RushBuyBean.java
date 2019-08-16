package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2019/1/2.
 */
public class RushBuyBean {
    private int goods_id;
    private String add_time;
    private String advance_time;
    private int auditing_status;
    private String begin_date;
    private String end_date;
    private int give_integral;
    private String goods_img;
    private ArrayList<String> goods_imglist;
    private String goods_indeximg;
    private String goods_indextype;
    private String goods_name;
    private int goods_number;
    private String goods_small_name;
    private String goods_sn;
    private boolean is_on_sale;
    private String use_number;
    private String qty;
    private double shop_price;
    private double market_price;

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAdvance_time() {
        return advance_time;
    }

    public void setAdvance_time(String advance_time) {
        this.advance_time = advance_time;
    }

    public int getAuditing_status() {
        return auditing_status;
    }

    public void setAuditing_status(int auditing_status) {
        this.auditing_status = auditing_status;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(int give_integral) {
        this.give_integral = give_integral;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public ArrayList<String> getGoods_imglist() {
        return goods_imglist;
    }

    public void setGoods_imglist(ArrayList<String> goods_imglist) {
        this.goods_imglist = goods_imglist;
    }

    public String getGoods_indeximg() {
        return goods_indeximg;
    }

    public void setGoods_indeximg(String goods_indeximg) {
        this.goods_indeximg = goods_indeximg;
    }

    public String getGoods_indextype() {
        return goods_indextype;
    }

    public void setGoods_indextype(String goods_indextype) {
        this.goods_indextype = goods_indextype;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_small_name() {
        return goods_small_name;
    }

    public void setGoods_small_name(String goods_small_name) {
        this.goods_small_name = goods_small_name;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public boolean isIs_on_sale() {
        return is_on_sale;
    }

    public void setIs_on_sale(boolean is_on_sale) {
        this.is_on_sale = is_on_sale;
    }

    public String getUse_number() {
        return use_number;
    }

    public void setUse_number(String use_number) {
        this.use_number = use_number;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }
}
