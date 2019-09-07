package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.OnClick;

/**
 * Created by LG on 2019/8/15.
 */

public class CustomSortLayout extends LinearLayout implements TbHotGoodsAdapter.OnItemClickListener {

    private Context context;
    private RecyclerView recyclerView;
    private TbHotGoodsAdapter tbHotGoodsAdapter = null;
    private ArrayList<GoodsListBean> goodsListBeans = null;

    public CustomSortLayout(Context context) {
        super(context);
    }

    public CustomSortLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomSortLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context,AttributeSet attrs){

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.layout_sort,null);


        recyclerView = view.findViewById(R.id.rv_goods);

        int spanCount1 = 5; // 2 columns
        int spacing1 = 20; // 50px

        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(context,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new SpaceItemDecoration(spanCount1, spacing1,0));


        addView(view);

    }

    /**
     * 界面点击typeid
     * @param position
     */
    public void setType(int position){

    }


    public void setData(ArrayList<GoodsListBean> goodsListBeans){
        this.goodsListBeans  = goodsListBeans;
        if(tbHotGoodsAdapter == null){
            tbHotGoodsAdapter = new TbHotGoodsAdapter(context,goodsListBeans,LayoutInflater.from(context));
            tbHotGoodsAdapter.setAdd_Integral();
            recyclerView.setAdapter(tbHotGoodsAdapter);
            tbHotGoodsAdapter.setItemClickListener(this);
        }else {
            tbHotGoodsAdapter.setData(goodsListBeans);
        }

    }


    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID,goodsListBeans.get(position).getGoodsId());
        bundle.putString(WlmUtil.TYPE,WlmUtil.INTEGRAL);
        UiHelper.launcherBundle(context, SelfGoodsDetailActivity.class,bundle);
    }
}
