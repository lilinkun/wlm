package com.wlm.wlm.model;

import android.content.SharedPreferences;

import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.util.WlmUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LG on 2019/11/19.
 */
public class LoginModel extends BaseMvpModel {


    public void login(HttpResultCallBack<LoginBean,Object> subscriber,DataManager dataManager){

        HashMap<String,String> params = new HashMap<>();

        Subscription subscription = dataManager.login(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

        addSubscriber(subscription);

        /*Subscription subscription = manager.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<LoginBean,Object>() {
                    @Override
                    public void onResponse(LoginBean loginBean, String status,Object page) {

                        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WlmUtil.LOGIN, mContext.MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID(mContext)).putBoolean(WlmUtil.LOGIN, true)
                                .putString(WlmUtil.OPENID, openid).putString(WlmUtil.UNIONID, unionid).commit();

                        mLoginView.onLoginSuccess(loginBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onErr(String msg, String status) {
                        mLoginView.onLoginFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onNext(ResultBean<LoginBean,Object> ResultBean) {
                        super.onNext(ResultBean);
                    }
                })*/

    }

}
