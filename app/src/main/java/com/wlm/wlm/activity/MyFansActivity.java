package com.wlm.wlm.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MyFansAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyFansContract;
import com.wlm.wlm.presenter.MyFansPresenter;

import butterknife.BindView;

/**
 * Created by LG on 2019/9/20.
 */
public class MyFansActivity extends BaseActivity implements MyFansContract {

    @BindView(R.id.rv_myfans)
    RecyclerView rv_myfans;

    private MyFansPresenter myFansPresenter = new MyFansPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_fans;
    }

    @Override
    public void initEventAndData() {
        myFansPresenter.onCreate(this,this);
        myFansPresenter.getFansData("1","20", ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        rv_myfans.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void getFansSuccess() {

        MyFansAdapter myFansAdapter = new MyFansAdapter(this);

    }

    @Override
    public void getFansFail(String msg) {

    }
}
