package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

public interface CrowdFundingContract extends IView {

    public void onFlashSuccess(ArrayList<FlashBean> flashBeans);

    public void onFlashFail(String msg);


    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean);

    public void getDataFail(String msg);


}
