package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/29.
 */
public class HomeBean {
    private ArrayList<GoodsListBean> GoodsList;
    private ArrayList<FlashBean> flash;
    private String Time;

    public ArrayList<GoodsListBean> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(ArrayList<GoodsListBean> goodsList) {
        GoodsList = goodsList;
    }

    public ArrayList<FlashBean> getFlash() {
        return flash;
    }

    public void setFlash(ArrayList<FlashBean> flash) {
        this.flash = flash;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
