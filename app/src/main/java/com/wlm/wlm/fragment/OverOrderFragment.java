package com.wlm.wlm.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.AllOrderActivity;
import com.wlm.wlm.adapter.SelfOrderAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfOrderContract;
import com.wlm.wlm.entity.SelfOrderBean;
import com.wlm.wlm.presenter.SelfOrderPresenter;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/1/6.
 */

public class OverOrderFragment extends BaseFragment implements SelfOrderContract, SelfOrderAdapter.OnItemClick, SelfOrderAdapter.OnItemClickListener {

    @BindView(R.id.over_pay_rv)
    RecyclerView over_pay_rv;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.ll_no_order)
    LinearLayout ll_no_order;

    SelfOrderPresenter selfOrderPresenter = new SelfOrderPresenter();
    private int pageIndex = 1;
    private SelfOrderAdapter selfOrderAdapter;
    private ArrayList<SelfOrderBean> selfOrderBeans;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_order_over;
    }

    @Override
    public void initEventAndData() {
        selfOrderPresenter.onCreate(getActivity(), this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        over_pay_rv.setLayoutManager(linearLayoutManager);

        selfOrderPresenter.getOrderData(pageIndex + "", WlmUtil.PAGE_COUNT, "4", ProApplication.SESSIONID(getActivity()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                selfOrderPresenter.getOrderData(pageIndex + "", WlmUtil.PAGE_COUNT, "4", ProApplication.SESSIONID(getActivity()));
            }
        });
    }

    public void setData() {
        if (getActivity() != null) {
            selfOrderPresenter.getOrderData(pageIndex + "", WlmUtil.PAGE_COUNT, "4", ProApplication.SESSIONID(getActivity()));
        }
    }

    @Override
    public void getDataSuccess(ArrayList<SelfOrderBean> selfOrderBeans) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        over_pay_rv.setVisibility(View.VISIBLE);
        if (selfOrderBeans.size() > 0) {
            this.selfOrderBeans = selfOrderBeans;
            if (selfOrderAdapter == null) {
                selfOrderAdapter = new SelfOrderAdapter(getActivity(), selfOrderBeans, this);

                over_pay_rv.setAdapter(selfOrderAdapter);
                over_pay_rv.setVisibility(View.VISIBLE);
                ll_no_order.setVisibility(View.GONE);
                selfOrderAdapter.setItemClickListener(this);
            } else {
                selfOrderAdapter.setData(selfOrderBeans);
            }
        } else {
            ll_no_order.setVisibility(View.VISIBLE);
            over_pay_rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        ll_no_order.setVisibility(View.VISIBLE);
        if (msg.contains("查无数据")) {
            over_pay_rv.setVisibility(View.GONE);
        }
    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {

    }

    @Override
    public void exitOrderFail(String smg) {

    }

    @Override
    public void cancelOrderSuccess(String collectDeleteBean) {

    }

    @Override
    public void cancelOrderFail(String smg) {

    }

    @Override
    public void exit_order(String orderId) {

    }

    @Override
    public void go_pay(SelfOrderBean orderId) {

    }

    @Override
    public void sureReceipt(String orderId) {

    }

    @Override
    public void cancelOrder(String orderId) {
    }


    @Override
    public void getQrcode(String orderId) {

    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putInt("status", selfOrderBeans.get(position).getOrderStatus());
            bundle.putString("order_sn", selfOrderBeans.get(position).getOrderSn());
            UiHelper.launcherForResultBundle(getActivity(), AllOrderActivity.class, 0x0987, bundle);
        }
    }
}
