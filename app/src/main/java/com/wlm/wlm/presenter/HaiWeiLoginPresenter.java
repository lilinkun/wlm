package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.HaiWeiLoginContract;
import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.ResultBean;
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
public class HaiWeiLoginPresenter extends BasePresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ResultBean<LoginBean,Object> mLoginBean;
    private HaiWeiLoginContract haiWeiLoginContract;

    @Override
    public void onCreate(Context mContext,IView view) {
        this.mContext = mContext;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        haiWeiLoginContract = (HaiWeiLoginContract) view;
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

    /**
     * 登陆
     */
    public void login(final String UserName,final String PassWord,String sessionId){

        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","登录中...",true);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Login");
        params.put("UserName", UserName);
        params.put("PassWord", PassWord);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<LoginBean,Object>() {
                    @Override
                    public void onResponse(LoginBean loginBean, String status,Object page) {

                        haiWeiLoginContract.onLoginSuccess(loginBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onErr(String msg, String status) {
                        haiWeiLoginContract.onLoginFail(msg);
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
}
