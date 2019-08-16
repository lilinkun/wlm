package com.wlm.wlm.interf;

/**
 * Created by LG on 2018/12/5.
 */

public interface IOrderItemClickListener {
    void toPayOrder(int position);
    void tosendOrder(int position);
    void toCancleOrder(int position);
    void sureGetOrder(int position);
}