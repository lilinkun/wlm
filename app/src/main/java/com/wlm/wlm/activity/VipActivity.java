package com.wlm.wlm.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.adapter.VipAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/14.
 */
public class VipActivity extends BaseActivity {

    @BindView(R.id.rv_vip)
    RecyclerView rv_vip;
    @BindView(R.id.rv_vip_goods)
    RecyclerView rv_vip_goods;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

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

        TbHotGoodsAdapter tbHotGoodsAdapter = new TbHotGoodsAdapter(this,null,getLayoutInflater());

        rv_vip_goods.setAdapter(tbHotGoodsAdapter);

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
