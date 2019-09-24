package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/19.
 */

public interface SelfSearchContract extends IView {
    public void onSuccess(ArrayList<TbMaterielBean> tbShopBeans);
    public void onError(String msg);

    public void onSelfSuccess(ArrayList<String> selfGoodsBeans);
    public void onSelfFail(String msg);

    public void getSearchResultSuccess(ArrayList<GoodsListBean> goodsListBeans);
    public void getSearchResultFail(String msg);

}
