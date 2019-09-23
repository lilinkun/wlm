package com.wlm.wlm.contract;

import com.wlm.wlm.entity.ArticleDetailBean;
import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2019/9/23.
 */
public interface ArticleContract extends IView {
    public void getDataSuccess(ArticleDetailBean articleDetailBean);
    public void getDataFail(String msg);
}
