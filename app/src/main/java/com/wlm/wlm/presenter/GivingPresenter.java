package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.GivingContract;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/1/6.
 */

public class GivingPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GivingContract givingContract;

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
        givingContract = (GivingContract) view;
    }

    public void getAccount(){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankBase");
        params.put("fun","BankPointList");
//        params.put("PageIndex",PageIndex);
//        params.put("PageCount",PageCount);
//        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getShoppingPrice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<IntegralBean,Object>() {
                    @Override
                    public void onResponse(IntegralBean integralBean, String status,Object page) {
//                        integralContract.getGoodsIntegralSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
//                        integralContract.getGoodsIntegralFail(msg);
                    }
                }));
    }

    public void getGivingIntegral(){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","BankBase");
        params.put("fun","BankPointList");
//        params.put("PageIndex",PageIndex);
//        params.put("PageCount",PageCount);
//        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getShoppingPrice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<IntegralBean,Object>() {
                    @Override
                    public void onResponse(IntegralBean integralBean, String status,Object page) {
//                        integralContract.getGoodsIntegralSuccess(integralBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
//                        integralContract.getGoodsIntegralFail(msg);
                    }
                }));
    }

    public void getInfo(String session){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","UserBase");
        params.put("fun","UserBasedetailById");
        params.put("SessionId",session);
        mCompositeSubscription.add(manager.getInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<PersonalInfoBean,Object>() {

                    @Override
                    public void onResponse(PersonalInfoBean loginBean, String status,Object page) {
//                        meContract.getInfoSuccess(loginBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
//                        meContract.getInfoFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean<PersonalInfoBean,Object> o) {
                        super.onNext(o);
                    }

                }));
    }
}
