package com.wlm.wlm.contract;

import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/27.
 */

public interface ForgetFourPsdContract extends IView {
    public void onModifyPsdSuccess(String message);
    public void onModiftPsdFail(String msg);

    public void onLoginSuccess(LoginBean loginBean);
    public void onLoginFail(String msg);
}
