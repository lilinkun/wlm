package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsDiscoverBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/24.
 */
public interface FindContract extends IView {

    public void onGetDataSuccess(ArrayList<GoodsDiscoverBean> goodsDiscoverBeans, PageBean pageBean);

    public void onGetDataFail(String msg);

}
