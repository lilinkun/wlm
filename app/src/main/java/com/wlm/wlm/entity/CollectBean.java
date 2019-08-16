package com.wlm.wlm.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 收藏实体类
 * Created by LG on 2018/12/11.
 */
@Entity
public class CollectBean implements Serializable{
    private String collect_id;
    private String user_id;
    @Id
    private Long goods_id;
    private String add_time;
    private String goods_name;
    private String goods_img;
    private double shop_price;
    private String store_name;
    public static final long serialVersionUID = 123237;

    @Generated(hash = 1340028775)
    public CollectBean(String collect_id, String user_id, Long goods_id,
            String add_time, String goods_name, String goods_img, double shop_price,
            String store_name) {
        this.collect_id = collect_id;
        this.user_id = user_id;
        this.goods_id = goods_id;
        this.add_time = add_time;
        this.goods_name = goods_name;
        this.goods_img = goods_img;
        this.shop_price = shop_price;
        this.store_name = store_name;
    }

    @Generated(hash = 420494524)
    public CollectBean() {
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

}
