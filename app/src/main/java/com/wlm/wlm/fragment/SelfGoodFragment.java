package com.wlm.wlm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.adapter.SelfGoodsAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfGoodsContract;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.presenter.SelfGoodsPresenter;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2018/12/12.
 */

public class SelfGoodFragment extends BaseFragment implements SelfGoodsContract, SelfGoodsAdapter.OnItemClickListener {

    @BindView(R.id.rv_self_goods)
    RecyclerView mSelfGoodsRv;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private int lastVisibleItem = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SelfGoodsPresenter selfGoodsPresenter = new SelfGoodsPresenter();
    private SelfGoodsAdapter selfGoodsAdapter;
    private ArrayList<SelfGoodsBean> selfGoodsBeans;
    private boolean isLoad = true;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_self_goods;
    }

    @Override
    public void initEventAndData() {

        selfGoodsPresenter.onCreate(getActivity(),this);

        selfGoodsPresenter.getGoodList("1","20","132","add_time","","",ProApplication.SESSIONID(getActivity()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 2; // 2 columns
        int spacing = 20; // 50px

        boolean includeEdge = false;
        mSelfGoodsRv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mSelfGoodsRv.setLayoutManager(gridLayoutManager);

        mSelfGoodsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (selfGoodsAdapter != null) {
                        if (lastVisibleItem + 1 == selfGoodsAdapter.getItemCount()) {
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    PAGE_INDEX++;
//                                    tbAllPresenter.setList(PAGE_INDEX + "",PAGE_COUNT,"64362000477",getArguments().getString("TBId"),"tk_total_sales_des", ProApplication.SESSIONID(getActivity()));
//                                }
//                            }, 200);
                        }

                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });


    }



    public void onPageChange(int page){
//        tbDisCountBeans.clear();
//        PAGE_INDEX = 1;
//        tbAllPresenter.setList(PAGE_INDEX + "",PAGE_COUNT,"64362000477",getArguments().getString("TBId"), LzyydUtil.strs[page], ProApplication.SESSIONID(getActivity()));
    }


    @Override
    public void getDataSuccess(ArrayList<SelfGoodsBean> selfGoodsBeans,boolean load) {
        this.selfGoodsBeans = selfGoodsBeans;
        this.isLoad = load;
        if (selfGoodsAdapter == null) {
            selfGoodsAdapter = new SelfGoodsAdapter(getActivity(), selfGoodsBeans,2);
            mSelfGoodsRv.setAdapter(selfGoodsAdapter);
            selfGoodsAdapter.setItemClickListener(this);
        }else {

        }

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putString("goodsid", selfGoodsBeans.get(position).getGoods_id());
            UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class, bundle);
        }
    }
}
