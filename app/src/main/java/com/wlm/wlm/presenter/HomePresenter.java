package com.wlm.wlm.presenter;

import android.content.Context;
import android.content.Intent;

import com.wlm.wlm.contract.HomeContract;
import com.wlm.wlm.entity.BalanceDetailBean;
import com.wlm.wlm.entity.FansBean;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.HomeBean;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.PageBean;
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
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        homeContract = (HomeContract) view;
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

    public void getHomeData(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Home");
        params.put("fun","Mobile");
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getHomeData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<HomeBean,Object>() {
                    @Override
                    public void onResponse(HomeBean homeBean, String status,Object page) {
                        homeContract.getHomeDataSuccess(homeBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeContract.getHomeDataFail(msg);
                    }

                }));
    }


    /**
     * 获取首页信息
     * @param Style
     */
    public void setFlash(String Style){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Flash");
        params.put("fun","FlashVipList");
        params.put("Style",Style);
        mCompositeSubscription.add(manager.getFlash(params)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new HttpResultCallBack<ArrayList<FlashBean>,Object>() {
            @Override
            public void onResponse(ArrayList<FlashBean> flashBeans, String status,Object page) {
                homeContract.onFlashSuccess(flashBeans);
            }

            @Override
            public void onErr(String msg, String status) {
                homeContract.onFlashFail(msg);
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
        params.put("fun","SettingParameter");
        params.put("SessionId",SessionId);

        mCompositeSubscription.add(manager.getUrl(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<UrlBean,Object>(){

                    @Override
                    public void onResponse(UrlBean urlBean, String status,Object page) {
                        homeContract.getUrlSuccess(urlBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeContract.getUrlFail(msg);
                    }

                }));

    }

    /*
     * 获取首页热门商品
     */

    public void getGoodsList(String type){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Goods");
        params.put("fun","GoodsListRecommendVip");
        params.put("GoodsFlag","2");
        params.put("type",type);
        mCompositeSubscription.add(manager.getGoodsList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> balanceDetailBeans, String status,Object page) {
                        homeContract.onGoodsListSuccess(balanceDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeContract.onGoodsListFail(msg);
                    }
                }));
    }


}
