package com.wlm.wlm.contract;

import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/27.
 */

public interface MemberShipContract extends IView{
    public void getDataSuccess(ArrayList<SelfGoodsBean> selfGoodsBeans);
    public void getDataFail(String msg);
}
