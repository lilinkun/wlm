package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.wlm.wlm.adapter.TbHotGoodsAdapter;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.OnClick;

/**
 * Created by LG on 2019/8/15.
 */

public class CustomSortLayout extends LinearLayout{

    private Context context;
    private RecyclerView recyclerView;

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

        TbHotGoodsAdapter tbHotGoodsAdapter = new TbHotGoodsAdapter(context,null,LayoutInflater.from(context));

        recyclerView.setAdapter(tbHotGoodsAdapter);

        addView(view);

    }

    /**
     * 界面点击typeid
     * @param position
     */
    public void setType(int position){

    }


}
