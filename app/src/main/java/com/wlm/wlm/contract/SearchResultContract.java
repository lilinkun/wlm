package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/24.
 */
public interface SearchResultContract extends IView {

    public void getSearchResultSuccess(ArrayList<GoodsListBean> goodsListBeans);

    public void getSearchResultFail(String msg);

}
