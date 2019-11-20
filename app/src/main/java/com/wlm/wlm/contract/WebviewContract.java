package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/27.
 */

public interface WebviewContract extends IView {

    public void onDataSuccess(String str);

    public void onDataFail(String msg);
}
