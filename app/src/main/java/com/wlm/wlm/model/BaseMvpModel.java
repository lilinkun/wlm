package com.wlm.wlm.model;

import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/11/19.
 */
public class BaseMvpModel {
    private static final String TAG = "BaseMvpModel";
    protected final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void addSubscriber(Subscription subscription) {
        mCompositeSubscription.add(subscription);
        Log.e(TAG, "addSubscriber: 添加");
    }

    public void removeSubscriber() {
        Log.e(TAG, "removeSubscriber: 移除");
        if (!mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

}
