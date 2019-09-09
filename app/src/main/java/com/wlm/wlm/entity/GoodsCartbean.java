package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/9.
 */
public class GoodsCartbean {
    private double sumamount;
    private double sumprice;
    private int carriage;
    private int sumintegral;
    private ArrayList<OrderBean> list;

    public double getSumamount() {
        return sumamount;
    }

    public void setSumamount(double sumamount) {
        this.sumamount = sumamount;
    }

    public double getSumprice() {
        return sumprice;
    }

    public void setSumprice(double sumprice) {
        this.sumprice = sumprice;
    }

    public int getCarriage() {
        return carriage;
    }

    public void setCarriage(int carriage) {
        this.carriage = carriage;
    }

    public int getSumintegral() {
        return sumintegral;
    }

    public void setSumintegral(int sumintegral) {
        this.sumintegral = sumintegral;
    }

    public ArrayList<OrderBean> getList() {
        return list;
    }

    public void setList(ArrayList<OrderBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GoodsCartbean{" +
                "sumamount=" + sumamount +
                ", sumprice=" + sumprice +
                ", carriage=" + carriage +
                ", sumintegral=" + sumintegral +
                ", list=" + list +
                '}';
    }
}
