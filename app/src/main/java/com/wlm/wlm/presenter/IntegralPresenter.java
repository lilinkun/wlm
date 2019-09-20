package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.IntegralContract;
import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.BalanceDetailBean;
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
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        integralContract = (IntegralContract) view;
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


    public void getPriceData(String PageIndex,String PageCount,String type,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankCurrency");
        params.put("fun","CurrencyList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("type",type);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getAmountPrice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<BalanceDetailBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<BalanceDetailBean> balanceDetailBeans, String status,Object page) {
                        integralContract.getDataSuccess(balanceDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getDataFail(msg);
                    }
                }));
    }
}
