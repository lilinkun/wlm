package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/13.
 */

public class UrlBean {

    private ArrayList<FlashBean> flash;
    private ArrayList<HomeCategoryBean> News;

    public ArrayList<FlashBean> getFlash() {
        return flash;
    }

    public void setFlash(ArrayList<FlashBean> flash) {
        this.flash = flash;
    }

    public ArrayList<HomeCategoryBean> getNews() {
        return News;
    }

    public void setNews(ArrayList<HomeCategoryBean> news) {
        News = news;
    }
}
