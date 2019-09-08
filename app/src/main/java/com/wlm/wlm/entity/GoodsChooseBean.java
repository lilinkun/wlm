package com.wlm.wlm.entity;

import java.io.Serializable;

/**
 * Created by LG on 2018/12/8.
 */
public class GoodsChooseBean implements Serializable{
    private int attr_id;
    private String GoodsId;
    private String spec1;
    private String spec1_image;
    private String spec2;
    private String price;
    private int amount;
    private int use_amount;
    private String backup1;
    private String backup2;
    private String spec_order;
    private String spec_weight;
    private String market_Price;
    private String integral;

    public int getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(int attr_id) {
        this.attr_id = attr_id;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec1_image() {
        return spec1_image;
    }

    public void setSpec1_image(String spec1_image) {
        this.spec1_image = spec1_image;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUse_amount() {
        return use_amount;
    }

    public void setUse_amount(int use_amount) {
        this.use_amount = use_amount;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }

    public String getSpec_order() {
        return spec_order;
    }

    public void setSpec_order(String spec_order) {
        this.spec_order = spec_order;
    }

    public String getSpec_weight() {
        return spec_weight;
    }

    public void setSpec_weight(String spec_weight) {
        this.spec_weight = spec_weight;
    }

    public String getMarket_Price() {
        return market_Price;
    }

    public void setMarket_Price(String market_Price) {
        this.market_Price = market_Price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
