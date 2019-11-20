package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.GetCashContract;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/22.
 */
public class GetCashPresenter extends BasePresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GetCashContract getCashContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        getCashContract = (GetCashContract) view;
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

    public void getCash(String TransactionAamount, String Brokerage, String PassWordTwo, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "提现中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "WithdrawCash");
        params.put("fun", "WithdrawCash_Add");
        params.put("TransactionAamount", TransactionAamount);
        params.put("Brokerage", Brokerage);
        params.put("PassWordTwo", PassWordTwo);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getCash(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String grouponDetailBean, String status, Object page) {
                        getCashContract.getCashSuccess();
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        getCashContract.getCashFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));

    }

}
