package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.ForgetPasswordContract;
import com.wlm.wlm.entity.CollectBean;
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

public class ForgetPasswordPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ForgetPasswordContract forgetPasswordContract;

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
        forgetPasswordContract = (ForgetPasswordContract) view;
    }

    /**
     * 获得绑定手机号码
     * @param user_name
     */
    public void getMobile(String user_name){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","UserBase");
        params.put("fun","UserBaseInfoByUserName");
        params.put("user_name",user_name);
        mCompositeSubscription.add(manager.getMobile(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {
                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        forgetPasswordContract.getMobileSuccess(o.toString());
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        forgetPasswordContract.getMobileFail(msg);
                    }
                }));
    }

    /**
     * 获取验证码
     */

    public void getVcode(String mobile){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","SendSms");
        params.put("fun","RegisteredSmsCode");
        params.put("mobile",mobile);
        mCompositeSubscription.add(manager.getMobile(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {
                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        forgetPasswordContract.getVcodeSuccess(status.toString());
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        forgetPasswordContract.getVcodeFail(msg);
                    }
                }));
    }
}
