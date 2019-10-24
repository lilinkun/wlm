package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/10/24.
 */
public interface FindContract extends IView {

    public void onGetDataSuccess();
    public void onGetDataFail(String msg);

}
