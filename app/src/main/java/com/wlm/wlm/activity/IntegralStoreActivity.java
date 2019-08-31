package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.fragment.TBAllFragment;
import com.wlm.wlm.ui.PagerSlidingTabStrip;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分商城
 * Created by LG on 2019/8/15.
 */
public class IntegralStoreActivity extends BaseActivity {

    private int position = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x110) {
                Log.v("TAG", "hander");
                position = msg.getData().getInt("position");

            }
        }
    };

    @Override

    public int getLayoutId() {
        return R.layout.activity_integral_store;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.integral_bg));
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
