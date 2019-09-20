package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/11/27.
 */

public interface HomeContract extends IView{
        public void getUrlSuccess(String urlBean);
        public void getUrlFail(String msg);

        public void onFlashSuccess(ArrayList<FlashBean> flashBeans);
        public void onFlashFail(String msg);

        public void onGoodsListSuccess(ArrayList<GoodsListBean> goodsListBeans);
        public void onGoodsListFail(String msg);
}
