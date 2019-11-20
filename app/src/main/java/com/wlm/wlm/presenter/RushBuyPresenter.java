package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.RushBuyContract;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.RushBuyBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/1/2.
 */

public class RushBuyPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private RushBuyContract rushBuyContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        rushBuyContract = (RushBuyContract) view;
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


    public void getRushBuyData(String PageIndex,String PageCount,String GoodsType,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "LimitedActivityGoods_List");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("GoodsType", GoodsType);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.rushBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<RushBuyBean>,Object>(){

                    @Override
                    public void onResponse(ArrayList<RushBuyBean> o, String status,Object page) {
                        rushBuyContract.getDataSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        rushBuyContract.getDataFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean<ArrayList<RushBuyBean>,Object> o) {
                        super.onNext(o);
                    }

                })
        );
    }

    public void getData(String PageIndex,String PageCount,String GoodsType,String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "aheadActivityGoods_List");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("GoodsType", GoodsType);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.rushBuy(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<RushBuyBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<RushBuyBean> o, String status,Object page) {
                        rushBuyContract.getDataSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        rushBuyContract.getDataFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean<ArrayList<RushBuyBean>, Object> o) {
                        super.onNext(o);
                    }

                })
        );
    }
}
