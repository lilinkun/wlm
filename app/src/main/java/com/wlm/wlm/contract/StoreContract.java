package com.wlm.wlm.contract;

import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.SelfStoreBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/20.
 */

public interface StoreContract extends IView {
    public void getDataSuccess(ArrayList<SelfStoreBean> selfGoodsBeans);
    public void getDataFail(String msg);
}
