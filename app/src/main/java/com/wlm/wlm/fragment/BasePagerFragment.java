package com.wlm.wlm.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wlm.wlm.base.BaseFragment;

/**
 * Created by LG on 2018/12/26.
 */
public abstract class BasePagerFragment extends BaseFragment {

    protected AppCompatActivity mActivity;
    protected boolean isViewInitiated; //控件是否初始化完成
    protected boolean isVisibleToUser; //页面是否可见
    protected boolean isDataInitiated; //数据是否加载


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {   //page 1等会此读数据  生命周期
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {   //page 0进入再此读数据
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData(false);
    }

    public abstract void loadData();


    protected void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData();
            isDataInitiated = true;
        }
    }


}