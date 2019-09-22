package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

public interface VipContract extends IView {

    public void getDataSuccess(ArrayList<GoodsListBean> integralBean, PageBean pageBean);
    public void getDataFail(String msg);
}
