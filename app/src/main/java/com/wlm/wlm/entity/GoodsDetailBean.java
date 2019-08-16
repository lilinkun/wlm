package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/8.
 */

public class GoodsDetailBean<T> {
    private SelfGoodsBean goodsItem;
    private T goodsAttrItem;

    public SelfGoodsBean getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(SelfGoodsBean goodsItem) {
        this.goodsItem = goodsItem;
    }

    public T getGoodsAttrItem() {
        return goodsAttrItem;
    }

    public void setGoodsAttrItem(T goodsAttrItem) {
        this.goodsAttrItem = goodsAttrItem;
    }
}
