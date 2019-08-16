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

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;

    String[] strs = {"精选","护肤套装", "防晒脱毛", "彩妆香水", "面部精华", "男士服饰", "化妆品", "文体车品", "鞋包", "数码", "内衣"};
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
        pagerSlidingTabStrip.setTitles(strs, 0, handler);
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
