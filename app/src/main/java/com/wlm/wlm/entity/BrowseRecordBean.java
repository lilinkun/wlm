package com.wlm.wlm.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by LG on 2019/1/17.
 */
@Entity
public class BrowseRecordBean implements Serializable{
    @Id(autoincrement = true)
    private long id;
    private String collect_id;
    private String user_id;
    private Long goods_id;
    private String add_time;
    private String goods_name;
    private String goods_img;
    private double shop_price;
    private String store_name;
    private String browse_date;
    public static final long serialVersionUID = 124237;

    @Generated(hash = 2004139055)
    public BrowseRecordBean(long id, String collect_id, String user_id,
            Long goods_id, String add_time, String goods_name, String goods_img,
            double shop_price, String store_name, String browse_date) {
        this.id = id;
        this.collect_id = collect_id;
        this.user_id = user_id;
        this.goods_id = goods_id;
        this.add_time = add_time;
        this.goods_name = goods_name;
        this.goods_img = goods_img;
        this.shop_price = shop_price;
        this.store_name = store_name;
        this.browse_date = browse_date;
    }

    @Generated(hash = 1803007329)
    public BrowseRecordBean() {
    }
    
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getBrowse_date() {
        return browse_date;
    }

    public void setBrowse_date(String browse_date) {
        this.browse_date = browse_date;
    }
}
