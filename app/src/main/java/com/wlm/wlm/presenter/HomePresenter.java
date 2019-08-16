package com.wlm.wlm.presenter;

import android.content.Context;
import android.content.Intent;

import com.wlm.wlm.contract.HomeContract;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.TbjsonBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/11/26.
 */

public class HomePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private HomeContract homeContract;

    @Override
    public void onCreate(Context context) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void attachView(IView view) {
        homeContract = (HomeContract) view;
    }


    /**
     * 获取首页信息
     * @param SessionId
     */
    public void setFlash(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Home");
        params.put("fun","FalshAndCategory_List");
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getFlash(params)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new HttpResultCallBack<HomeHeadBean,Object>() {
            @Override
            public void onResponse(HomeHeadBean homeHeadBean, String status,Object page) {
                homeContract.onFlashSuccess(homeHeadBean);
            }

            @Override
            public void onErr(String msg, String status) {
                homeContract.onFlashFail(msg);
            }

            @Override
            public void onNext(ResultBean<HomeHeadBean, Object> result) {
                super.onNext(result);
            }
        }));
    }

    /**
     * 获取图片地址前缀
     * @param SessionId
     */
    public void getUrl(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Home");
        params.put("fun","GetUrl");
        params.put("SessionId",SessionId);

        mCompositeSubscription.add(manager.getUrl(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<UrlBean,Object>(){

                    @Override
                    public void onResponse(UrlBean urlBean, String status,Object page) {
                        homeContract.getUrlSuccess(urlBean);
//                        homeContract.onSuccess(arrayListTbjsonBean.getResults()));
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeContract.getUrlFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean<UrlBean, Object> result) {
                        super.onNext(result);
                    }
                }));

    }
}
