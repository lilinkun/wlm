package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/12/15.
 */
public class ChildListBean {
    private int goods_id;
    private String goods_name;
    private double shop_price;
    /**
     * 商品可使用几分
     */
    private int give_integral;
    private String goods_weight;
    private int return_integral;
    private int num;
    private String total_price;
    private String spec1;
    private String spec2;
    private String attr_id;
    private String goods_img;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
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

    public int getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(int give_integral) {
        this.give_integral = give_integral;
    }

    public String getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(String goods_weight) {
        this.goods_weight = goods_weight;
    }

    public int getReturn_integral() {
        return return_integral;
    }

    public void setReturn_integral(int return_integral) {
        this.return_integral = return_integral;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }
}
