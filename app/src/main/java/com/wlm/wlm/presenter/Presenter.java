package com.wlm.wlm.presenter;

import android.content.Context;

import com.wlm.wlm.mvp.IView;


/**
 * Created by LG on 2018/1/31.
 */

public interface Presenter {
    void onCreate(Context context, IView view);

    void onStart();

    void onDestory();


}