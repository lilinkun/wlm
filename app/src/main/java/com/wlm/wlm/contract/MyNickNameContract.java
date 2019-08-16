package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/26.
 */

public interface MyNickNameContract extends IView {
    public void modifySuccess();
    public void modifyFail(String msg);
}
