package com.wlm.wlm.contract;

import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/12.
 */
public interface ManufactureStoreContract extends IView {
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean);

    public void getFail(String msg);

    public void getCategorySuccess(ArrayList<Category1Bean> category1Beans);

    public void getCategoryFail(String msg);
}
