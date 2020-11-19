package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/29.
 */
public class HomeBean {
    private ArrayList<GoodsListBean> GoodsList;
    private ArrayList<FlashBean> flash;
    private ArrayList<FlashBean> flashVip9;
    private String Time;
    private ArrayList<HomeFlashVipBean> flashVip;
    private ArrayList<TitleIconBean> IndexIcon;

    public ArrayList<TitleIconBean> getIndexIcon() {
        return IndexIcon;
    }

    public void setIndexIcon(ArrayList<TitleIconBean> indexIcon) {
        IndexIcon = indexIcon;
    }

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

    public ArrayList<FlashBean> getFlashVip9() {
        return flashVip9;
    }

    public void setFlashVip9(ArrayList<FlashBean> flashVip9) {
        this.flashVip9 = flashVip9;
    }

    public ArrayList<HomeFlashVipBean> getFlashVip() {
        return flashVip;
    }

    public void setFlashVip(ArrayList<HomeFlashVipBean> flashVip) {
        this.flashVip = flashVip;
    }
}
