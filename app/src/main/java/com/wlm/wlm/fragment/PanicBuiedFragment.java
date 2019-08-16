package com.wlm.wlm.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.adapter.PanicbuyingAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.RushBuyContract;
import com.wlm.wlm.entity.RushBuyBean;
import com.wlm.wlm.presenter.RushBuyPresenter;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/1/2.
 */

public class PanicBuiedFragment extends BaseFragment implements RushBuyContract, PanicbuyingAdapter.OnItemClickListener {


    @BindView(R.id.rv_panicbuied)
    RecyclerView rv_panicbuied;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RushBuyPresenter rushBuyPresenter = new RushBuyPresenter();
    private PanicbuyingAdapter panicbuyingAdapter;
    private ArrayList<RushBuyBean> rushBuyBeans;
    private int pageIndex = 1;
    private int lastVisibleItem = 0;
    private boolean isUpdate = false;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_panicbuied;
    }

    @Override
    public void initEventAndData() {

        rushBuyPresenter.attachView(this);
        rushBuyPresenter.onCreate(getActivity());
        rushBuyPresenter.getData(pageIndex+"", LzyydUtil.PAGE_COUNT,2+"", ProApplication.SESSIONID(getActivity()));

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_panicbuied.setLayoutManager(linearLayoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                rushBuyPresenter.getData(pageIndex+"", LzyydUtil.PAGE_COUNT,2+"", ProApplication.SESSIONID(getActivity()));
                isUpdate = true;
            }
        });

        rv_panicbuied.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (panicbuyingAdapter != null) {
                        if (lastVisibleItem + 1 == panicbuyingAdapter.getItemCount()) {

                            if (pageIndex * Integer.valueOf(LzyydUtil.PAGE_COUNT) > rushBuyBeans.size()){
                                UToast.show(getActivity(),"已到末尾");
                            }else {
                                pageIndex++;
                                rushBuyPresenter.getData(pageIndex + "", LzyydUtil.PAGE_COUNT,2+"", ProApplication.SESSIONID(getActivity()));
                            }
                        }

                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }

    @Override
    public void getDataSuccess(ArrayList<RushBuyBean> rushBuyBeans) {
        if (refreshLayout != null && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if (rushBuyBeans != null) {
            if (pageIndex == 1) {
                if (isUpdate) {
                    this.rushBuyBeans = rushBuyBeans;
                    panicbuyingAdapter.setData(rushBuyBeans);
                } else {
                    this.rushBuyBeans = rushBuyBeans;
                    panicbuyingAdapter = new PanicbuyingAdapter(getActivity(), rushBuyBeans, 1);
                    rv_panicbuied.setAdapter(panicbuyingAdapter);
                    panicbuyingAdapter.setItemClickListener(this);
                }
            }else {
                this.rushBuyBeans.addAll(rushBuyBeans);
                panicbuyingAdapter.setData(rushBuyBeans);
            }
        }else {
            if (pageIndex == 1){
                this.rushBuyBeans = rushBuyBeans;
            }else {
                this.rushBuyBeans.addAll(rushBuyBeans);
            }
            panicbuyingAdapter.setData(rushBuyBeans);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("goodsid",rushBuyBeans.get(position).getGoods_id()+"");
        UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class,bundle);
    }
}
