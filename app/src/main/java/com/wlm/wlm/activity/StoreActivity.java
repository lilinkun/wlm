package com.wlm.wlm.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.StoreContract;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.SelfStoreBean;
import com.wlm.wlm.presenter.StorePresenter;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/19.
 */

public class StoreActivity extends BaseActivity implements StoreContract, TbHotGoodsAdapter.OnItemClickListener {

    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_store_phone)
    TextView tv_store_phone;
    @BindView(R.id.iv_store_img)
    CustomRoundAngleImageView iv_store_img;
    @BindView(R.id.gv_hot_commodities)
    RecyclerView mHotGridView;

    StorePresenter storePresenter = new StorePresenter();
    private TbHotGoodsAdapter tbHotGoodsAdapter;
    private ArrayList<SelfStoreBean> selfGoodsBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor(this,getResources().getColor(R.color.text_question));

        storePresenter.onCreate(this,this);

        String storeid = getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("storeid");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 2; // 2 columns
        int spacing = 20; // 50px

        boolean includeEdge = false;
        mHotGridView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mHotGridView.setLayoutManager(gridLayoutManager);

        storePresenter.setData(storeid, ProApplication.SESSIONID(this));
    }


    @Override
    public void getDataSuccess(ArrayList<SelfStoreBean> selfGoodsBeans) {
        this.selfGoodsBeans = selfGoodsBeans;
        SelfStoreBean selfStoreBean = selfGoodsBeans.get(0);
        tv_store_name.setText(selfStoreBean.getShop_name());
        tv_store_phone.setText(selfStoreBean.getLink_phone());
        Picasso.with(this).load(ProApplication.BANNERIMG + selfStoreBean.getShop_logo()).into(iv_store_img);

        tbHotGoodsAdapter = new TbHotGoodsAdapter(this, selfGoodsBeans.get(0).getGoods(), getLayoutInflater());
        mHotGridView.setAdapter(tbHotGoodsAdapter);
        tbHotGoodsAdapter.setItemClickListener(this);

    }

    @Override
    public void getDataFail(String msg) {

    }

    @OnClick({R.id.iv_head_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_head_left:

                finish();

                break;
        }
    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            String goodsid = selfGoodsBeans.get(0).getGoods().get(position).getGoods_id();
            Bundle bundle = new Bundle();
            bundle.putString("goodsid", goodsid);
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);
        }
    }
}
