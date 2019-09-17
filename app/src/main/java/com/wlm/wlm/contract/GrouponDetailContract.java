package com.wlm.wlm.contract;

import com.wlm.wlm.entity.GrouponDetailBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/17.
 */
public interface GrouponDetailContract extends IView {

    public void getDataSuccess(GrouponDetailBean goodsListBean);
    public void getDataFail(String msg);
}
