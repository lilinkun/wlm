package com.wlm.wlm.contract;

import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

public interface IntegralStoreContract extends IView {
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean);
    public void getFail(String msg);
}
