package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.mvp.IView;

public interface GrouponGoodsDetailContract extends IView {

    public void getDataSuccess(GoodsListBean goodsListBean);
    public void getDataFail(String msg);

}
