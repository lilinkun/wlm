package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.ModifyPayPsdContract;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/24.
 */

public class ModifyPayPsdPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ModifyPayPsdContract modifyPayPsdContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        modifyPayPsdContract = (ModifyPayPsdContract) view;
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

    /**
     * 发送短信验证码
     *
     * @param sessionId
     */
    public void SendSms(String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "SendSafetyVerificationCode");
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status, Object page) {
                        modifyPayPsdContract.onSendVcodeSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPayPsdContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /**
     * 修改支付密码
     *
     * @param sessionId
     */
    public void modifyPsd(String Code, String PassWord, String ConfirmPassWord, String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseChangePassWord_Two");
        params.put("Code", Code);
        params.put("PassWord", PassWord);
        params.put("ConfirmPassWord", ConfirmPassWord);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.modifyPsd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status, Object page) {
                        modifyPayPsdContract.modifySuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPayPsdContract.modifyFail(msg);
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
     *
     * @param mobile
     * @param code
     */
    public void modify(String mobile, String code, String psd, String SessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UpdatePassWord");
        params.put("new_password", psd);
        params.put("mobile", mobile);
        params.put("Code", code);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status, Object page) {
                        modifyPayPsdContract.modifyPsdSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPayPsdContract.modifyPsdFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }
}
