package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.IntegralStoreContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.IntegralStorePresenter;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.PagerSlidingTabStrip;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.Eyes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 制造商称
 * Created by LG on 2019/8/15.
 */
public class ManufactureStoreActivity extends BaseActivity implements IGoodsTypeListener, IntegralStoreContract {

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.custom_sort)
    CustomSortLayout custom_sort;

    String[] strs = {"精选","护肤套装", "防晒脱毛", "彩妆香水", "面部精华", "男士服饰", "化妆品", "文体车品", "鞋包", "数码", "内衣"};
    private IntegralStorePresenter integralStorePresenter = new IntegralStorePresenter();
    private String goodstype = "8";
    private ArrayList<GoodsListBean> goodsListBeans = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_manufacture;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.store_bg));
        pagerSlidingTabStrip.setTitles(strs, 0, handler);

        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(this,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        integralStorePresenter.onCreate(this,this);
        integralStorePresenter.getData("1","20",goodstype,"0");

        ll_top.setListener(this);
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
    public void getSortType(int sortType) {

    }

    @Override
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        this.goodsListBeans = goodsListBeans;
        custom_sort.setData(goodsListBeans);
    }

    @Override
    public void getFail(String msg) {

    }
}
