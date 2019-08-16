package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/8.
 */
public class HomeHeadBean {
    private ArrayList<FlashBean> flash_list;
    private ArrayList<HomeCategoryBean> category_list;
    private ArrayList<LycsListBean> zysc_list;
    private ArrayList<HotHomeBean> hot_goods;

    public ArrayList<FlashBean> getFlash_list() {
        return flash_list;
    }

    public void setFlash_list(ArrayList<FlashBean> flash_list) {
        this.flash_list = flash_list;
    }

    public ArrayList<HomeCategoryBean> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(ArrayList<HomeCategoryBean> category_list) {
        this.category_list = category_list;
    }

    public ArrayList<LycsListBean> getZysc_list() {
        return zysc_list;
    }

    public void setZysc_list(ArrayList<LycsListBean> zysc_list) {
        this.zysc_list = zysc_list;
    }

    public ArrayList<HotHomeBean> getHot_goods() {
        return hot_goods;
    }

    public void setHot_goods(ArrayList<HotHomeBean> hot_goods) {
        this.hot_goods = hot_goods;
    }
}
