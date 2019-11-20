package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/30.
 */
public interface FlashSaleContract extends IView {

    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean);

    public void getDataFail(String msg);

    public void onFlashSuccess(ArrayList<FlashBean> flashBeans);

    public void onFlashFail(String msg);

}
