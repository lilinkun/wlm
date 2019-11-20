package com.wlm.wlm.contract;

import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/14.
 */

public interface SettingContract extends IView {

    public void LoginOutSuccess(String msg);

    public void LoginOutFail(String msg);

    public void updateSuccess(DownloadBean downloadBean);

    public void updateFail(String failMsg);
}
