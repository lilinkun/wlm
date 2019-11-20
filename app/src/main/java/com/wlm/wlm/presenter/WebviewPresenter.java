package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.TbAllContract;
import com.wlm.wlm.contract.WebviewContract;
import com.wlm.wlm.entity.OrderBean;
import com.wlm.wlm.entity.OrderListBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/27.
 */

public class WebviewPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private WebviewContract webviewContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        webviewContract = (WebviewContract) view;
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
     *
     * @param SessionId
     */
    public void getNewUrl(String Type,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","UserBase");
        params.put("fun","Html5Url");
        params.put("Type",Type);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getNewUrl(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        webviewContract.onDataSuccess(o.toString());
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        webviewContract.onDataSuccess(msg);
                    }
                }));
    }
}
