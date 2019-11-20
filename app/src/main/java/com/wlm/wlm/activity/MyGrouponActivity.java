package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MyGrouponAdapter;
import com.wlm.wlm.adapter.TabPageAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.fragment.GrouponOverFragment;
import com.wlm.wlm.fragment.GrouponUnOverFragment;
import com.wlm.wlm.util.Eyes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/16.
 */
public class MyGrouponActivity extends BaseActivity {

    @BindView(R.id.order_list_groupon)
    SlidingTabLayout order_list_groupon;
    @BindView(R.id.order_list_vp_groupon)
    ViewPager order_list_vp_groupon;

    private MyGrouponAdapter myGrouponAdapter = null;
    private List<String> mTitles;
    private List<Fragment> fragments;
    private GrouponOverFragment grouponOverFragment = new GrouponOverFragment();
    private GrouponUnOverFragment grouponUnOverFragment = new GrouponUnOverFragment();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x110:
                    int position = msg.getData().getInt("position");
                    break;

                case 0x111:
                    break;
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_mygroupon;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));


        initData();

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments, mTitles);
        pageAdapter.setTitles(mTitles);
        order_list_vp_groupon.setAdapter(pageAdapter);
//        order_list_groupon.setupWithViewPager(order_list_vp_groupon);
//        order_list_groupon.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));


        order_list_groupon.setViewPager(order_list_vp_groupon);

        order_list_vp_groupon.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:

                finish();

                break;
        }
    }


    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("拼团中");
        mTitles.add("拼团完成");

        fragments = new ArrayList<>();
        fragments.add(grouponUnOverFragment);
        fragments.add(grouponOverFragment);

    }


}
