package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.ArticleContract;
import com.wlm.wlm.contract.BindCardContract;
import com.wlm.wlm.entity.ArticleDetailBean;
import com.wlm.wlm.entity.BankBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/23.
 */
public class ArticlePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ArticleContract articleContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        articleContract = (ArticleContract) view;
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    public void getArticleDetail(String ArticleId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Article");
        params.put("fun","ArticleGet");
        params.put("ArticleId",ArticleId);
        params.put("SessionId",SessionId);

        mCompositeSubscription.add(manager.getArticleDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArticleDetailBean, Object>() {

                    @Override
                    public void onResponse(ArticleDetailBean addressBeans, String status, Object page) {
                        articleContract.getDataSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        articleContract.getDataFail(msg);
                    }
                })
        );
    }

}
