package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.RightNowBuyBean;
import com.wlm.wlm.entity.RightNowGoodsBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

public interface GrouponGoodsDetailContract extends IView {

    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBean);
    public void getDataFail(String msg);


    public void getCommendGoodsSuccess(ArrayList<GoodsListBean> selfGoodsBean);
    public void getCommendGoodsFail(String msg);
}
