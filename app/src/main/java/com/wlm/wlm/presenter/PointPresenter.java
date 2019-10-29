package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.contract.PointContract;
import com.wlm.wlm.manager.DataManager;
import com.wlm.wlm.mvp.IView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/10/29.
 */
public class PointPresenter extends BasePresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PointContract pointContract;


    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        pointContract = (PointContract) view;
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

    public void getData(){

    }
}
