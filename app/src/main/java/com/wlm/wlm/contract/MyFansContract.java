package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/20.
 */
public interface MyFansContract extends IView {
    public void getFansSuccess();
    public void getFansFail(String msg);
}
