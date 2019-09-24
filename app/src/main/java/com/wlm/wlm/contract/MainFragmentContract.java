package com.wlm.wlm.contract;

import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by Administrator on 2018/12/31.
 */

public interface MainFragmentContract extends IView {
    public void getUpdateSuccess(DownloadBean downloadBean);
    public void getUpdateFail(String msg);


    public void onLoginSuccess(LoginBean loginBean);
    public void onLoginFail(String msg);

}
