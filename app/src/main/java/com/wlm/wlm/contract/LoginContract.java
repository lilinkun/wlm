package com.wlm.wlm.contract;

import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/11/30.
 */

public interface LoginContract extends IView {
    public void onLoginSuccess(LoginBean loginBean);
    public void onLoginFail(String msg);

    public void showPromptMessage(int str);

}
