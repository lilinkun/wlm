package com.wlm.wlm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LG on 2018/11/10.
 */

public abstract class BaseFragment extends Fragment {

    public Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getlayoutId(), container, false);
        unbinder = ButterKnife.bind(this, v);
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initEventAndData();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
        return v;
    }

    public abstract int getlayoutId();

    public abstract void initEventAndData();
}
