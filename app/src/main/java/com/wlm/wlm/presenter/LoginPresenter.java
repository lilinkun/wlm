package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.wlm.wlm.R;
import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/11/30.
 */

public class LoginPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ResultBean<LoginBean,Object> mLoginBean;
    private LoginContract mLoginView;

    @Override
    public void onCreate(Context mContext) {
        this.mContext = mContext;
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
        mLoginView = (LoginContract) view;
    }


    /**
     * 登陆
     */
    public void login(String username,String psw,String sessionId){
        if(username == null || username.isEmpty() || username.isEmpty()){
            mLoginView.showPromptMessage(R.string.prompt_login_name_not_empty);
            return;
        }

        if(psw == null || psw.isEmpty()|| psw.isEmpty()){
            mLoginView.showPromptMessage(R.string.prompt_login_passwrod_not_empty);
            return;
        }
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","登录中...",true);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Login");
        params.put("UserName", username);
        params.put("PassWord", psw);
        params.put("SessionId", sessionId);
        params.put("MobileType","android");
        mCompositeSubscription.add(manager.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<LoginBean,Object>() {
                    @Override
                    public void onResponse(LoginBean loginBean, String status,Object page) {
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
                })
        );
    }

    /**
     * 是否能注册
     */
    public void isRegister(String Type,String sessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Html5Url");
        params.put("Type", Type);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.isRegister(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<Boolean,Object>() {
                    @Override
                    public void onResponse(Boolean aBoolean, String status,Object page) {
                        mLoginView.isRegisterSuccess(aBoolean);
                    }
                    @Override
                    public void onErr(String msg, String status) {
                        mLoginView.isRegisterFail(msg);
                    }
                    @Override
                    public void onNext(ResultBean<Boolean,Object> ResultBean) {
                        super.onNext(ResultBean);
                    }
                })
        );
    }


}
