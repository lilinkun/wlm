package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

public interface IntegralStoreContract extends IView {
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans);
    public void getFail(String msg);
}
