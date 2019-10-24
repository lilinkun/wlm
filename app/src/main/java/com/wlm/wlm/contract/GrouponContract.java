package com.wlm.wlm.contract;

import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/2.
 */
public interface GrouponContract extends IView {
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page);
    public void getFail(String msg);

    public void onFlashSuccess(ArrayList<FlashBean> flashBeans);
    public void onFlashFail(String msg);
}
