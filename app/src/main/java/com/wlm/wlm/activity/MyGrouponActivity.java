package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MyGrouponAdapter;
import com.wlm.wlm.adapter.TabPageAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyGrouponContrct;
import com.wlm.wlm.entity.GrouponListBean;
import com.wlm.wlm.fragment.GrouponAllFragment;
import com.wlm.wlm.fragment.GrouponOverFragment;
import com.wlm.wlm.fragment.GrouponUnOverFragment;
import com.wlm.wlm.presenter.MyGrouponPresenter;
import com.wlm.wlm.ui.tablayout.TabLayout;
import com.wlm.wlm.util.Eyes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LG on 2019/9/16.
 */
public class MyGrouponActivity extends BaseActivity implements MyGrouponContrct {

    @BindView(R.id.order_list_groupon)
    TabLayout order_list_groupon;
    @BindView(R.id.order_list_vp_groupon)
    ViewPager order_list_vp_groupon;

    private MyGrouponPresenter myGrouponPresenter = new MyGrouponPresenter();
    private MyGrouponAdapter myGrouponAdapter = null;
    private List<String> mTitles;
    private List<Fragment> fragments;
    private GrouponAllFragment grouponAllFragment = new GrouponAllFragment();
    private GrouponOverFragment grouponOverFragment = new GrouponOverFragment();
    private GrouponUnOverFragment grouponUnOverFragment = new GrouponUnOverFragment();


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x110:
                    int position = msg.getData().getInt("position");
                    myGrouponPresenter.getMyGrouponData("1","20", ProApplication.SESSIONID(MyGrouponActivity.this));
                    break;

                case 0x111:
                    myGrouponPresenter.getMyGrouponData("1","20", ProApplication.SESSIONID(MyGrouponActivity.this));
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
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        myGrouponPresenter.onCreate(this,this);

        initData();

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(),fragments,mTitles);
        pageAdapter.setTitles(mTitles);
        order_list_vp_groupon.setAdapter(pageAdapter);
        order_list_groupon.setupWithViewPager(order_list_vp_groupon);
        order_list_groupon.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));


        myGrouponPresenter.getMyGrouponData("1","20", ProApplication.SESSIONID(MyGrouponActivity.this));

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

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("全部");
        mTitles.add("拼团中");
        mTitles.add("拼团完成");

        fragments = new ArrayList<>();
        fragments.add(grouponAllFragment);
        fragments.add(grouponUnOverFragment);
        fragments.add(grouponOverFragment);

        grouponAllFragment.setHander(handler);
    }


    @Override
    public void getGrouponDataSuccess(ArrayList<GrouponListBean> grouponListBeans) {

        grouponAllFragment.setGrouponData(grouponListBeans);
        grouponUnOverFragment.setGrouponData(grouponListBeans);
        grouponOverFragment.setGrouponData(grouponListBeans);
    }

    @Override
    public void getGrouponDataFail(String msg) {

    }




}
