package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.BuyGoodsContract;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.util.PresenterUtil;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/4.
 */

public class BuyGoodsPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private BuyGoodsContract buyGoodsContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        buyGoodsContract = (BuyGoodsContract) view;
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


    public void collectGoods(String goodsId, String SessionId) {


        PresenterUtil.collectGoods(mCompositeSubscription, manager, goodsId, SessionId);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Collect");
        params.put("fun", "CollectCreate");
        params.put("goodsId", goodsId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {
                    @Override
                    public void onResponse(Object o, String status, Object page) {

                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }

                }));
    }

    public void setData(String Point, String SessionId, String NumIid, String CouponId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankBase");
        params.put("fun", "BankBasePointDeduction");
        params.put("Point", Point);
        params.put("NumIid", NumIid);
        params.put("CouponId", CouponId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean, Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean o, String status, Object page) {
                        buyGoodsContract.collectSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        buyGoodsContract.collectFail(msg);
                    }

                }));
    }

    public void isExChange(String Point, String SessionId, String NumIid, String CouponId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankBase");
        params.put("fun", "BankBaseCheckPointDeduction");
        params.put("Point", Point);
        params.put("NumIid", NumIid);
        params.put("CouponId", CouponId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.isExChange(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String str, String status, Object page) {
                        buyGoodsContract.exReChangeSuccess(str);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        buyGoodsContract.exReChangeFail(msg);
                    }

                }));
    }
}
