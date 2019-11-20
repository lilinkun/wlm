package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GrouponListBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/16.
 */
public interface MyGrouponContrct extends IView {

    public void getGrouponDataSuccess(ArrayList<GrouponListBean> grouponListBeans);

    public void getGrouponDataFail(String msg);

}
