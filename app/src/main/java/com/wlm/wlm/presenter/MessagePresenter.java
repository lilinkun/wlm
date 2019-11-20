package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.MessageContract;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.ui.LoaddingDialog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/23.
 */
public class MessagePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MessageContract messageContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        messageContract = (MessageContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestory() {
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    /**
     * @param sessionId
     */
    public void getArticleList(String PageIndex, String PageCount, String CategoryId, String sessionId) {
        final LoaddingDialog loaddingDialog = new LoaddingDialog(mContext);
        loaddingDialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Article");
        params.put("fun", "ArticleVipList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("CategoryId", CategoryId);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.getArticleList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<ArticleBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<ArticleBean> articleBeans, String status, PageBean page) {
                        messageContract.getArticleSuccess(articleBeans, page);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        messageContract.getArticleFail(msg);
                        if (loaddingDialog != null && loaddingDialog.isShowing()) {
                            loaddingDialog.dismiss();
                        }
                    }
                }));
    }


}
