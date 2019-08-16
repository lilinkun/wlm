package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.MainFragmentContract;
import com.wlm.wlm.entity.DownloadBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

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
    public void onCreate(Context context) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
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

    @Override
    public void attachView(IView view) {
        mainFragmentContract = (MainFragmentContract) view;
    }


    public void update(String sessionid){
        HashMap<String, String> params = new HashMap<>();
        params.put("fun", "Update");
        params.put("cls", "Home");
        params.put("SessionId", sessionid);
        mCompositeSubscription.add(manager.update(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<DownloadBean, Object>() {

                    @Override
                    public void onResponse(DownloadBean downloadBean, String status,Object page) {
                        mainFragmentContract.getUpdateSuccess(downloadBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        mainFragmentContract.getUpdateFail(msg);
                    }


                    @Override
                    public void onNext(ResultBean resultBean) {
                        super.onNext(resultBean);
                    }
                })
        );

    }


}
