package com.wlm.wlm.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.adapter.VipAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.VipContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.presenter.VipPresenter;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/14.
 */
public class VipActivity extends BaseActivity implements VipContract, TbHotGoodsAdapter.OnItemClickListener {

    @BindView(R.id.rv_vip)
    RecyclerView rv_vip;
    @BindView(R.id.rv_vip_goods)
    RecyclerView rv_vip_goods;

    private VipPresenter vipPresenter = new VipPresenter();
    private String goodsType = "4";
    private TbHotGoodsAdapter tbHotGoodsAdapter = null;
    ArrayList<GoodsListBean> goodsListBeans = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        vipPresenter.onCreate(this,this);
        vipPresenter.getData("1","20",goodsType,"0","0");

        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(this,3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 2; // 2 columns
        int spacing = 0; // 50px

        boolean includeEdge = false;
        rv_vip.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


        rv_vip.setLayoutManager(gridLayoutManager);

        VipAdapter vipAdapter = new VipAdapter(this);

        rv_vip.setAdapter(vipAdapter);


        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(this,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount1 = 5; // 2 columns
        int spacing1 = 20; // 50px
        rv_vip_goods.setLayoutManager(layoutManager);

        rv_vip_goods.addItemDecoration(new GridSpacingItemDecoration(spanCount1, spacing1,false));



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
    public void getDataSuccess(ArrayList<GoodsListBean> integralBean) {
        this.goodsListBeans = integralBean;
        if (tbHotGoodsAdapter == null) {
            tbHotGoodsAdapter = new TbHotGoodsAdapter(this, integralBean, getLayoutInflater());
            rv_vip_goods.setAdapter(tbHotGoodsAdapter);
            tbHotGoodsAdapter.setItemClickListener(this);
        }else {
            tbHotGoodsAdapter.setData(integralBean);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID,goodsListBeans.get(position).getGoodsId());
        UiHelper.launcherBundle(this,SelfGoodsDetailActivity.class,bundle);
    }
}
