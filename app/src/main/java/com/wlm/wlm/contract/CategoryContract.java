package com.wlm.wlm.contract;

import com.wlm.wlm.entity.CategoryListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/7.
 */

public interface CategoryContract extends IView {

    public void getDataSuccess(ArrayList<CategoryListBean<ArrayList<Object>>> categoryListBeans);
    public void getDataFail(String msg);
}
