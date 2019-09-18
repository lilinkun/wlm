package com.wlm.wlm.activity;

import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.Eyes;

import butterknife.OnClick;

public class CustomerServiceActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
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
