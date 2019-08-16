package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/29.
 */

public class AmountPriceBean {
    private double total_amount;
    private ArrayList<PointListBean> amount_list;

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public ArrayList<PointListBean> getAmount_list() {
        return amount_list;
    }

    public void setAmount_list(ArrayList<PointListBean> amount_list) {
        this.amount_list = amount_list;
    }
}
