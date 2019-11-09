package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.contract.SplashContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.entity.UrlBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;
import com.wlm.wlm.util.WlmUtil;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/11/9.
 */
public class SplashPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SplashContract mSplashContract;

    @Override
    public void onCreate(Context mContext, IView view) {
        this.mContext = mContext;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        mSplashContract = (SplashContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    /**
     * 获取图片地址前缀
     */
    public void getUrl() {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "SettingParameter");

        mCompositeSubscription.add(manager.getUrl(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<UrlBean, Object>() {

                    @Override
                    public void onResponse(UrlBean urlBean, String status, Object page) {
                        mSplashContract.getUrlSuccess(urlBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        mSplashContract.getUrlFail(msg);
                    }

                }));

    }
}