package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/20.
 */

public class SelfStoreBean {
    private String store_id;
    private String shop_name;
    private String shop_logo;
    private String shop_about;
    private String shop_say;
    private String link_man;
    private String link_phone;
    private String link_address;
    private String link_email;
    private String companyName;
    private String guarantee;
    private ArrayList<HotHomeBean> goods;

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_about() {
        return shop_about;
    }

    public void setShop_about(String shop_about) {
        this.shop_about = shop_about;
    }

    public String getShop_say() {
        return shop_say;
    }

    public void setShop_say(String shop_say) {
        this.shop_say = shop_say;
    }

    public String getLink_man() {
        return link_man;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getLink_address() {
        return link_address;
    }

    public void setLink_address(String link_address) {
        this.link_address = link_address;
    }

    public String getLink_email() {
        return link_email;
    }

    public void setLink_email(String link_email) {
        this.link_email = link_email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public ArrayList<HotHomeBean> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<HotHomeBean> goods) {
        this.goods = goods;
    }
}
