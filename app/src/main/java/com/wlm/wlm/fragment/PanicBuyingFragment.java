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
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/1/2.
 */

public class PanicBuyingFragment extends BaseFragment implements RushBuyContract, PanicbuyingAdapter.OnItemClickListener {

    @BindView(R.id.rv_panicbuying)
    RecyclerView rv_panicbuying;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RushBuyPresenter rushBuyPresenter = new RushBuyPresenter();
    private PanicbuyingAdapter panicbuyingAdapter = null;
    private int pageIndex = 1;
    private ArrayList<RushBuyBean> rushBuyBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_panicbuying;
    }

    @Override
    public void initEventAndData() {

        rushBuyPresenter.attachView(this);
        rushBuyPresenter.onCreate(getActivity());
        rushBuyPresenter.getRushBuyData(pageIndex + "", LzyydUtil.PAGE_COUNT, 2 + "", ProApplication.SESSIONID(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_panicbuying.setLayoutManager(linearLayoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rushBuyPresenter.getRushBuyData(pageIndex + "", LzyydUtil.PAGE_COUNT, 2 + "", ProApplication.SESSIONID(getActivity()));
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
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (panicbuyingAdapter == null) {
            this.rushBuyBeans = rushBuyBeans;
            panicbuyingAdapter = new PanicbuyingAdapter(getActivity(), rushBuyBeans, 0);
            rv_panicbuying.setAdapter(panicbuyingAdapter);
            panicbuyingAdapter.setItemClickListener(this);
        } else {
            panicbuyingAdapter.setData(rushBuyBeans);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("goodsid", rushBuyBeans.get(position).getGoods_id() + "");
        UiHelper.launcherBundle(getActivity(), SelfGoodsDetailActivity.class, bundle);
    }
}
