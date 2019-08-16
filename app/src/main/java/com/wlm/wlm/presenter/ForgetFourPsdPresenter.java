package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.R;
import com.wlm.wlm.contract.ForgetFourPsdContract;
import com.wlm.wlm.contract.ForgetPasswordContract;
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
 * Created by LG on 2018/12/27.
 */

public class ForgetFourPsdPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ForgetFourPsdContract forgetPasswordContract;

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
        forgetPasswordContract = (ForgetFourPsdContract) view;
    }

    /**
     * 忘记密码
     * @param username
     */
    public void getModify(String new_password,String mobile,String Code,String username){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","UserBase");
        params.put("fun","RetrievePassword");
        params.put("new_password",new_password);
        params.put("mobile",mobile);
        params.put("Code",Code);
        params.put("username",username);
        mCompositeSubscription.add(manager.getMobile(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {
                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        forgetPasswordContract.onModifyPsdSuccess(o.toString());
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        forgetPasswordContract.onModiftPsdFail(msg);
                    }
                }));
    }

    /**
     * 登陆
     */
    public void login(String username,String psw,String sessionId){

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
                        forgetPasswordContract.onLoginSuccess(loginBean);
                    }
                    @Override
                    public void onErr(String msg, String status) {
                        forgetPasswordContract.onLoginFail(msg);
                    }
                    @Override
                    public void onNext(ResultBean<LoginBean,Object> ResultBean) {
                        super.onNext(ResultBean);
                    }
                })
        );
    }
}
