package com.wlm.wlm.contract;

import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/11/9.
 */
public interface HaiWeiLoginContract extends IView {
    public void onLoginSuccess(LoginBean loginBean);

    public void onLoginFail(String msg);
}
