package com.wlm.wlm.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;

import com.wlm.wlm.util.UToast;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LG on 2018/11/10.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    Unbinder unbinder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        //初始化事件跟获取数据以及一些准备工作, 由于使用了ButterKnife, findViewById和基本的Click事件都不会在这里
        initEventAndData();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
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

    public abstract int getLayoutId();

    public abstract void initEventAndData();


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void myPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
