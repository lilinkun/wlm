package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/22.
 */
public interface GetCashContract extends IView {

    public void getCashSuccess();
    public void getCashFail(String msg);

}
