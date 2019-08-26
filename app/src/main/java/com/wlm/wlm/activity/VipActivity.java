package com.wlm.wlm.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.VipAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/14.
 */
public class VipActivity extends BaseActivity {

    @BindView(R.id.rv_vip)
    RecyclerView rv_vip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(this,3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        rv_vip.setLayoutManager(gridLayoutManager);

        VipAdapter vipAdapter = new VipAdapter(this);

        rv_vip.setAdapter(vipAdapter);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){

        switch (view.getId()){

            case R.id.ll_back:

                finish();

                break;

        }

    }

}
