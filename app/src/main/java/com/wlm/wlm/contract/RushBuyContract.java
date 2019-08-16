package com.wlm.wlm.contract;

import com.wlm.wlm.entity.RushBuyBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/1/2.
 */

public interface RushBuyContract extends IView{
    public void getDataSuccess(ArrayList<RushBuyBean> rushBuyBeans);
    public void getDataFail(String msg);
}
