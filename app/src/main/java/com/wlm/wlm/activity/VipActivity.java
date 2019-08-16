package com.wlm.wlm.activity;

import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.Eyes;

import butterknife.OnClick;

/**
 * Created by LG on 2019/8/14.
 */
public class VipActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));


    }

    @OnClick({R.id.iv_vip1,R.id.iv_vip2,R.id.iv_vip3,R.id.iv_vip4,R.id.iv_vip5,R.id.iv_vip6,R.id.ll_back})
    public void onClick(View view){

        switch (view.getId()){

            case R.id.ll_back:

                finish();

                break;

            case R.id.iv_vip1:

                break;
        }

    }

}
