package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/8.
 */

public interface SelfGoodsDetailContract extends IView {
    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean);

    public void getDataFail(String msg);

    public void addCollectSuccess(String collectBean);

    public void addCollectFail(String msg);

    public void isGoodsCollectSuccess(String msg);

    public void deleteCollectSuccess(String msg);

    public void addCartSuccess(String msg);

    public void addCartFail(String msg);

    public void getCommendGoodsSuccess(ArrayList<GoodsListBean> selfGoodsBean);

    public void getCommendGoodsFail(String msg);

    public void isAddressSuccess(ArrayList<AddressBean> msg);

    public void isAddressFail(String msg);
}
