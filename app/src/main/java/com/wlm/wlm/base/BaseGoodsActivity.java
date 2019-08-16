package com.wlm.wlm.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.wlm.wlm.util.UToast;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LG on 2018/12/17.
 */

public abstract class BaseGoodsActivity extends RxAppCompatActivity {
    Unbinder unbinder = null;

    public static List<Activity> activityList = new ArrayList<>();
    public static int mPage = 0;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        //初始化事件跟获取数据以及一些准备工作, 由于使用了ButterKnife, findViewById和基本的Click事件都不会在这里
        initEventAndData();
        activityList.add(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
    }

    public abstract int getLayoutId();

    public abstract void initEventAndData();
    // 去掉第一个跳转的Activity
    public void removeLast(){
        activityList.get(1).finish();
        activityList.remove(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
    }

    // 移除所有Activity
    public void finishAll(){
        for (Activity activity : activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

    protected void toast(@StringRes int resId) {
        UToast.show(this, resId);
    }

    protected void toast(@StringRes int resId, int duration) {
        UToast.show(this, resId, duration);
    }

    protected void toast(@NonNull String text) {
        UToast.show(this, text);
    }

    protected void toast(@NonNull String text, int duration) {
        UToast.show(this, text, duration);
    }
}

