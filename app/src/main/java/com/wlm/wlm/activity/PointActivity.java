package com.wlm.wlm.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.PointAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.PointContract;
import com.wlm.wlm.presenter.PointPresenter;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/10/28.
 */
public class PointActivity extends BaseActivity implements PointContract {

    @BindView(R.id.rv_point)
    RecyclerView rv_point;

    PointPresenter pointPresenter = new PointPresenter();
    PointAdapter pointAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_point;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor1(this,getResources().getColor(R.color.point_red));

        pointPresenter.onCreate(this,this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_point.setLayoutManager(gridLayoutManager);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
