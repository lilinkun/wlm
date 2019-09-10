package com.wlm.wlm.activity;

import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class BindCardActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_card;
    }

    @Override
    public void initEventAndData() {

    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.ll_back:

                finish();

                break;

        }
    }
}
