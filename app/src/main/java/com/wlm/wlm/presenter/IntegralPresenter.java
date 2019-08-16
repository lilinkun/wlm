package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.IntegralContract;
import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.IntegralBean;
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
 * Created by LG on 2018/12/29.
 */

public class IntegralPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private IntegralContract integralContract;

    @Override
    public void onCreate(Context context) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(IView view) {
        integralContract = (IntegralContract) view;
    }

    public void getIntegralData(String PageIndex,String PageCount,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankBase");
        params.put("fun","BankPointList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getShoppingPrice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<IntegralBean,Object>() {
                    @Override
                    public void onResponse(IntegralBean integralBean, String status,Object page) {
                        integralContract.getGoodsIntegralSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getGoodsIntegralFail(msg);
                    }
                }));
    }


    public void getPriceData(String PageIndex,String PageCount,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankBase");
        params.put("fun","BankAmountList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getAmountPrice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<AmountPriceBean,Object>() {
                    @Override
                    public void onResponse(AmountPriceBean integralBean, String status,Object page) {
                        integralContract.getDataSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getDataFail(msg);
                    }
                }));
    }
}
