package com.wlm.wlm.interf;

import com.wlm.wlm.entity.WxUserInfo;

/**
 * Created by LG on 2019/9/5.
 */
public interface IWxLoginListener {

    public void setWxLoginSuccess(WxUserInfo wxSuccess);
    public void setWxLoginFail(String msg);
}
