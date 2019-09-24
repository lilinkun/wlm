package com.wlm.wlm.contract;

import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/24.
 */
public interface MyQrCodeContract extends IView {
    public void getQrCodeSuccess(LoginBean loginBean);
    public void getQrCodeFail(String msg);
}
