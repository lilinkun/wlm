package com.wlm.wlm.contract;

import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/11/9.
 */
public interface SplashContract extends IView {

    public void getUrlSuccess(UrlBean urlBean);
    public void getUrlFail(String msg);


}
