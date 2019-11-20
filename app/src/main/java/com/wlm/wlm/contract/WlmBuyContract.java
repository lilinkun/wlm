package com.wlm.wlm.contract;

import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/11/4.
 */
public interface WlmBuyContract extends IView {
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean);

    public void getDataFail(String msg);


    public void onFlashSuccess(ArrayList<FlashBean> flashBeans);

    public void onFlashFail(String msg);


    public void getCategorySuccess(ArrayList<Category1Bean> category1Beans);

    public void getCategoryFail(String msg);
}
