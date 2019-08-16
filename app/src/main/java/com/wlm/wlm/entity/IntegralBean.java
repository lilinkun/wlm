package com.wlm.wlm.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/29.
 */
public class IntegralBean {

    private double total_point;
    private ArrayList<PointListBean> point_list;

    public double getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }

    public ArrayList<PointListBean> getPoint_list() {
        return point_list;
    }

    public void setPoint_list(ArrayList<PointListBean> point_list) {
        this.point_list = point_list;
    }
}
