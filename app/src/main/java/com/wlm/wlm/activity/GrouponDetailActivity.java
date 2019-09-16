package com.wlm.wlm.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.GrouponGoodsDetailContract;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.presenter.GrouponGoodsDetailPresenter;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.PriceTextView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/21.
 */

public class GrouponDetailActivity extends BaseActivity implements GrouponGoodsDetailContract {

    @BindView(R.id.tv_grouponing)
    TextView tv_grouponing;
    @BindView(R.id.rv_groupon_list)
    RecyclerView rv_groupon_list;
    @BindView(R.id.tv_groupon_price)
    PriceTextView tv_groupon_price;
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_grounon_info)
    TextView tv_grounon_info;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;

    private String goodsId;
    private GrouponGoodsDetailPresenter getGoodsDetail = new GrouponGoodsDetailPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_detail;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        getGoodsDetail.onCreate(this,this);

        if (bundle != null && bundle.getString(WlmUtil.GOODSID) != null){
            goodsId = bundle.getString(WlmUtil.GOODSID);
        }

        getGoodsDetail.getGoodsDetail(goodsId, ProApplication.SESSIONID(this));


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
    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBean) {


        tv_groupon_price.setText(goodsListBean.getPrice()+"");

        tv_goods_title.setText(goodsListBean.getGoodsName());

        tv_grouponing.setBackground(null);
        tv_grouponing.setText("拼团中");

        tv_grounon_info.setText(goodsListBean.getGoodsTypeName());

        if(WlmUtil.isCountdown(goodsListBean.getBeginDate(),goodsListBean.getEndDate(),tv_rush_time) == 0){
            tv_end_time.setText("至开始");
        }else if (WlmUtil.isCountdown(goodsListBean.getBeginDate(),goodsListBean.getEndDate(),tv_rush_time) == 1){
            tv_end_time.setText("至截止");
        }else {
            tv_grouponing.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataFail(String msg) {
        toast(msg);
    }
}
