package com.wlm.wlm.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.wlm.wlm.contract.PersonalInfoContract;
import com.wlm.wlm.entity.CollectDeleteBean;
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

public class PersonalInfoPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PersonalInfoContract personalInfoContract;


    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        personalInfoContract = (PersonalInfoContract) view;
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

    public void modifyInfo(String account, String session) {
    }

    /**
     * 获取个人信息
     *
     * @param session
     */
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
                        personalInfoContract.getInfoSuccess(loginBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }

                    @Override
                    public void onNext(ResultBean<PersonalInfoBean, Object> o) {
                        super.onNext(o);
                    }

                }));
    }

    public void uploadImage(String HeadPic, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UploadHeadPic");
        params.put("HeadPic", HeadPic);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getHeadInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectDeleteBean, Object>() {
                    @Override
                    public void onResponse(CollectDeleteBean o, String status, Object page) {
                        personalInfoContract.uploadImageSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        personalInfoContract.uploadImageFail(msg);
                    }
                }));
    }

    public void modifyInfo(String session) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBasedetailById");
        params.put("SessionId", session);
    }

    public void LoginOut(String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "退出登录中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Logout");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.loginout(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String selfGoodsBeans, String status, Object page) {
                        personalInfoContract.LoginOutSuccess(selfGoodsBeans);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        personalInfoContract.LoginOutFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /*public void update(String sessionid){
        HashMap<String, String> params = new HashMap<>();
        params.put("fun", "Update");
        params.put("cls", "Home");
        params.put("SessionId", sessionid);
        mCompositeSubscription.add(manager.update(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<DownloadBean , Object>() {

                    @Override
                    public void onResponse(DownloadBean downloadBean, String status,Object pageBean) {
                        settingView.updateSuccess(downloadBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        settingView.updateFail(msg);
                    }


                    @Override
                    public void onNext(ResultBean resultBean) {
                        super.onNext(resultBean);
                    }
                })
        );

    }*/


}
