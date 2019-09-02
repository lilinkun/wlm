package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.wlm.wlm.contract.RegisterContract;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/11/29.
 */

public class RegisterPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private RegisterContract registerContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        registerContract = (RegisterContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


    /**
     * 发送短信验证码
     * @param mobile
     * @param sessionId
     */
    public void SendSms(String mobile,String sessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "RegisteredSmsCode");
        params.put("mobile", mobile);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack(){

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        registerContract.onSendVcodeSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }


    /**
     * 注册
     */
   /* public void register(String NickName,String Mobile){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseRegister");
        params.put("Mobile", Mobile);
        params.put("NickName", NickName);
        params.put("NickName", NickName);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack(){

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        registerContract.onRegisterSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.onRegisterFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }*/

    /**
     * 注册
     * @param mobile
     * @param code
     * @param progressDialog
     */
    public void register(String mobile, String code, String invitation, final ProgressDialog progressDialog){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Register");
        params.put("user_password", invitation);
        params.put("mobile", mobile);
        params.put("Code", code);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack(){

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        registerContract.onRegisterSuccess();
                        progressDialog.show();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.onRegisterFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }



    /**
     * 修改密码
     * @param mobile
     * @param code
     */
    public void modify(String mobile, String code, String psd,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UpdatePassWord");
        params.put("new_password", psd);
        params.put("mobile", mobile);
        params.put("Code", code);
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack(){

                    @Override
                    public void onResponse(Object o, String status,Object page) {
                        registerContract.onRegisterSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.onRegisterFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

}
