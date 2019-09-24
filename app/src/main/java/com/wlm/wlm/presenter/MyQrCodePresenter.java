package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.MyQrCodeContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/24.
 */
public class MyQrCodePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MyQrCodeContract myQrCodeContract;

    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        myQrCodeContract = (MyQrCodeContract) view;
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

    public void getUpdataData(String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","生成二维码中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","UserBase");
        params.put("fun","UserBaseGet");
        params.put("SessionId",SessionId);
        mCompositeSubscription.add(manager.getUpdataData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<LoginBean, Object>() {

                    @Override
                    public void onResponse(LoginBean loginBean, String status, Object page) {
                        myQrCodeContract.getQrCodeSuccess(loginBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        myQrCodeContract.getQrCodeFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));

    }
}
