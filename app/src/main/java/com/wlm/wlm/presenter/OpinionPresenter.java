package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.OpinionContract;
import com.wlm.wlm.entity.ErrorBean;
import com.wlm.wlm.entity.OpinionBean;
import com.wlm.wlm.entity.ResultBean;
import com.wlm.wlm.http.callback.HttpResultCallBack;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2018/12/2.
 */

public class OpinionPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private OpinionContract opinionContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        opinionContract = (OpinionContract) view;
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

    public void upload(String Type, String Title, String Question, String SessionId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cls", "Message");
        hashMap.put("fun", "MessageCreate");
        hashMap.put("Type", Type);
        hashMap.put("Title", Title);
        hashMap.put("Question", Question);
        hashMap.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.opinion(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {


                    @Override
                    public void onResponse(Object o, String status, Object page) {
                        opinionContract.onUploadSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        opinionContract.onFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }
                }));
    }


    public void getErrorType(String SessionId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cls", "Message");
        hashMap.put("fun", "MessageType");
        hashMap.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getErrorType(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<ErrorBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<ErrorBean> errorBeans, String status, Object page) {
                        opinionContract.getTypeSuccess(errorBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        opinionContract.getTypeFail(msg);
                    }
                }));
    }

    /**
     * 获取留言历史列表
     *
     * @param Type
     * @param SessionId
     */
    public void getList(String PageIndex, String PageCount, String Type, String SessionId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cls", "Message");
        hashMap.put("fun", "MessageList");
        hashMap.put("PageIndex", PageIndex);
        hashMap.put("PageCount", PageCount);
        if (!Type.equals("")) {
            hashMap.put("Type", Type);
        }
        hashMap.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.opinionHistory(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<OpinionBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<OpinionBean> errorBeans, String status, Object page) {
                        opinionContract.getOpinionListSuccess(errorBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        opinionContract.getOpinionListFail(msg);
                    }
                }));
    }

}
