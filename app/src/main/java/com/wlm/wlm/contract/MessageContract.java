package com.wlm.wlm.contract;

import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/23.
 */
public interface MessageContract extends IView {

    public void getArticleSuccess(ArrayList<ArticleBean> articleBeans, PageBean pageBean);

    public void getArticleFail(String msg);

}
