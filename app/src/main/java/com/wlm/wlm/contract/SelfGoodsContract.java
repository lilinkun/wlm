package com.wlm.wlm.contract;

import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/12.
 */

public interface SelfGoodsContract extends IView {

    public void getDataSuccess(ArrayList<SelfGoodsBean> selfGoodsBeans, boolean page);

    public void getDataFail(String msg);
}
