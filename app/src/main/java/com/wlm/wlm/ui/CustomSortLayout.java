package com.wlm.wlm.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2019/8/15.
 */

public class CustomSortLayout extends LinearLayout implements TbHotGoodsAdapter.OnItemClickListener {

    private Context context;
    private XRecyclerView recyclerView;
    private TbHotGoodsAdapter tbHotGoodsAdapter = null;
    private ArrayList<GoodsListBean> goodsListBeans = null;
    private String type ;
//    private SwipeRefreshLayout refreshLayout;
    private SortListerner sortListerner;
    private int lastVisibleItem = 0;
    private int PAGE_INDEX = 0;
    private PageBean pageBean;

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

//        refreshLayout = view.findViewById(R.id.refreshLayout);

        recyclerView = view.findViewById(R.id.rv_goods);

        int spanCount1 = 10; // 2 columns
        int spacing1 = 20; // 50px

        final GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        final FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(context,2);
//        layoutManager.setOrientation(GridLayoutManager.VERTICAL);


//        recyclerView.addItemDecoration();

//        recyclerView.addItemDecoration(recyclerView.new DividerItemDecoration(dividerDrawable));
        recyclerView.addItemDecoration(new SpaceXItemDecoration(spanCount1, spacing1,0));

        recyclerView.setLayoutManager(layoutManager);

        addView(view);

//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                sortListerner.onRefresh();
//            }
//        });

        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                sortListerner.onRefresh();
            }

            @Override
            public void onLoadMore() {
                if (tbHotGoodsAdapter != null) {
                        if (PAGE_INDEX  > Integer.valueOf(pageBean.getMaxPage())){
//                            recyclerView.loadMoreComplete();
                            recyclerView.setNoMore(true);
                        }else {
                            PAGE_INDEX++;
                            sortListerner.onLoadding(PAGE_INDEX);

                        }

                }
            }
        });

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (tbHotGoodsAdapter != null) {
                        if (lastVisibleItem + 1 == tbHotGoodsAdapter.getItemCount()) {
                            if (PAGE_INDEX  > Integer.valueOf(pageBean.getMaxPage())){

                            }else {
                                PAGE_INDEX++;
                                sortListerner.onLoadding(PAGE_INDEX);

                            }
                        }

                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });*/


    }

    /**
     * 界面点击typeid
     * @param position
     */
    public void setType(int position){

    }


    public void setPageIndex(int pageIndex, PageBean pageBean){
//        if (refreshLayout != null && refreshLayout.isRefreshing()){
//            refreshLayout.setRefreshing(false);
//        }
        this.pageBean = pageBean;
        this.PAGE_INDEX = pageIndex;
    }


    public void setData(ArrayList<GoodsListBean> goodsListBeans,String type){

        recyclerView.refreshComplete();

        recyclerView.loadMoreComplete();

        this.type = type;
        if(tbHotGoodsAdapter == null){
            this.goodsListBeans  = goodsListBeans;
            tbHotGoodsAdapter = new TbHotGoodsAdapter(context,goodsListBeans,LayoutInflater.from(context));
            if (type.equals(WlmUtil.INTEGRAL)) {
                tbHotGoodsAdapter.setAdd_Integral();
            }
            recyclerView.setAdapter(tbHotGoodsAdapter);
            tbHotGoodsAdapter.setItemClickListener(this);
        }else {
            if (PAGE_INDEX == 1) {
                this.goodsListBeans  = goodsListBeans;
            }else {
                this.goodsListBeans.addAll(goodsListBeans);
            }
            tbHotGoodsAdapter.setData(this.goodsListBeans);
        }

    }


    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID,goodsListBeans.get(position).getGoodsId());
        bundle.putString(WlmUtil.TYPE,type);
        UiHelper.launcherBundle(context, SelfGoodsDetailActivity.class,bundle);
    }

    public void setListener(SortListerner listener){
        this.sortListerner = listener;
    }


    public interface SortListerner{
        public void onRefresh();
        public void onLoadding(int page);
    }
}
