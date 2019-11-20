package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.BindCardContract;
import com.wlm.wlm.entity.BankBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.UserBankBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/17.
 */
public class BindCardPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private BindCardContract bindCardContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        bindCardContract = (BindCardContract) view;
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
     * 获取银行卡信息
     *
     * @param SessionId
     */
    public void getBankCard(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBankGet");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBankBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<UserBankBean, Object>() {
                    @Override
                    public void onResponse(UserBankBean balanceDetailBeans, String status, Object page) {
                        bindCardContract.getBankSuccess(balanceDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        bindCardContract.getBankFail(msg);
                    }
                }));
    }

    public void getBankInfo(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankNameList");
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getBankInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<BankBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<BankBean> addressBeans, String status, Object page) {
                        bindCardContract.getBankInfoSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        bindCardContract.getBankInfoFail(msg);
                    }
                })
        );
    }

    public void upBankInfo(String BankName, String BankDetails, String BankUserName, String BankNo, String Code, String UserCode, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBankUpdate");
        params.put("BankName", BankName);
        params.put("BankDetails", BankDetails);
        params.put("BankUserName", BankUserName);
        params.put("BankNo", BankNo);
        params.put("Code", Code);
        params.put("UserCode", UserCode);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.upBankInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String s, String status, Object page) {
                        bindCardContract.upBankInfoSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        bindCardContract.upBankInfoFail(msg);
                    }
                })
        );
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
                        bindCardContract.onSendVcodeSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        bindCardContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }
}
