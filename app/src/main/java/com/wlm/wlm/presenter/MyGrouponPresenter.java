package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.MyGrouponContrct;
import com.wlm.wlm.entity.GrouponListBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/16.
 */
public class MyGrouponPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MyGrouponContrct myGrouponContrct;

    @Override
    public void onCreate(Context context, IView view) {
        manager = new DataManager(context);
        this.mContext = context;
        mCompositeSubscription = new CompositeSubscription();
        myGrouponContrct = (MyGrouponContrct) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void getMyGrouponData(String PageIndex,String PageCount,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "GoodsTeamPublish");
        params.put("fun", "GoodsTeamPublishVipList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getMyGrouponData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GrouponListBean>,Object>() {

                    @Override
                    public void onResponse(ArrayList<GrouponListBean> loginBean, String status, Object page) {
                        myGrouponContrct.getGrouponDataSuccess(loginBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        myGrouponContrct.getGrouponDataFail(msg);
                    }


                }));
    }

}
