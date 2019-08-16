package com.wlm.wlm.activity;

import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.UiHelper;

import butterknife.OnClick;

/**
 * Created by LG on 2018/12/27.
 */

public class ForgetTwoPsdActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_two;
    }

    @Override
    public void initEventAndData() {

    }

    @OnClick({R.id.tv_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_next:
                UiHelper.launcher(this,ForgetThreePsdActivity.class);
                break;
        }
    }
}
