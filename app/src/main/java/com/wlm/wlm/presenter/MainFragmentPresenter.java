package com.wlm.wlm.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MainFragmentContract;
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
 * Created by Administrator on 2018/12/31.
 */

public class MainFragmentPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MainFragmentContract mainFragmentContract;


    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        mainFragmentContract = (MainFragmentContract) view;
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
                        mainFragmentContract.getUrlSuccess(urlBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        mainFragmentContract.getUrlFail(msg);
                    }

                }));

    }

    /**
     * 登陆
     */
    public void login(final String openid, final String unionid, String TPLType, String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "WxLogin");
//        params.put("openid", "o-Pjfv7ZCRp1NaQamoaKgDxqZFRs");
//        params.put("unionid", "obdLtwllDsCeXXVRuLXCq4uEIY4w");
        params.put("openid", openid);
        params.put("unionid", unionid);
        params.put("SessionId", sessionId);
        params.put("TPLType", TPLType);
        params.put("MobileType", "android");
        mCompositeSubscription.add(manager.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<LoginBean, Object>() {
                    @Override
                    public void onResponse(LoginBean loginBean, String status, Object page) {

                        SharedPreferences sharedPreferences = mContext.getSharedPreferences(WlmUtil.LOGIN, mContext.MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID(mContext)).putBoolean(WlmUtil.LOGIN, true)
                                .putString(WlmUtil.OPENID, openid).putString(WlmUtil.UNIONID, unionid).putString(WlmUtil.HEADIMGURL, loginBean.getPortrait() + "").commit();

                        mainFragmentContract.onLoginSuccess(loginBean);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        mainFragmentContract.onLoginFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean<LoginBean, Object> ResultBean) {
                        super.onNext(ResultBean);
                    }
                })
        );
    }

}
