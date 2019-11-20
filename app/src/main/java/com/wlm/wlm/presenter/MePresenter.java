package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.MeContract;
import com.wlm.wlm.entity.BalanceBean;
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
 * Created by LG on 2018/12/3.
 */

public class MePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MeContract meContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        meContract = (MeContract) view;
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

    public void getInfo(String session) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBasedetailById");
        params.put("SessionId", session);
        mCompositeSubscription.add(manager.getInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<PersonalInfoBean, Object>() {

                    @Override
                    public void onResponse(PersonalInfoBean loginBean, String status, Object page) {
                        meContract.getInfoSuccess(loginBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        meContract.getInfoFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean<PersonalInfoBean, Object> o) {
                        super.onNext(o);
                    }

                }));
    }

    public void getBalance(String SessionId) {
//        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBalance(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, Object page) {
                        meContract.getBalanceSuccess(balanceBean);
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        meContract.getBalanceFail(msg);
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
                    }
                }));
    }


}
