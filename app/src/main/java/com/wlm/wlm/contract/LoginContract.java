package com.wlm.wlm.contract;

import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/11/30.
 */

public interface LoginContract extends IView {
    public void onLoginSuccess(LoginBean loginBean);

    public void onLoginFail(String msg,String status);

    public void showPromptMessage(int str);

    public void getUrlSuccess(UrlBean urlBean);

    public void getUrlFail(String msg);

}
