package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/10/29.
 */
public interface PointContract extends IView {

    public void getDataSuccess();
    public void getDataFail(String msg);
}
