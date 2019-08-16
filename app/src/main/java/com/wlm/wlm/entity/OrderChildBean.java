package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/12/17.
 */

public class OrderChildBean {
    private OrderBean orderBean;
    private boolean isChoosed;
    private String parentId;

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
