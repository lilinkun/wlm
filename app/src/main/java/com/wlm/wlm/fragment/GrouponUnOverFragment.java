package com.wlm.wlm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.GrouponDetailActivity;
import com.wlm.wlm.adapter.MyGrouponAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyGrouponContrct;
import com.wlm.wlm.entity.GrouponListBean;
import com.wlm.wlm.presenter.MyGrouponPresenter;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/9/16.
 */
public class GrouponUnOverFragment extends BaseFragment implements MyGrouponContrct, MyGrouponAdapter.OnItemClickListener {

    @BindView(R.id.rv_all_groupon)
    RecyclerView rv_all_groupon;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private MyGrouponAdapter myGrouponAdapter;
    private Handler handler;
    private ArrayList<GrouponListBean> grouponListBeans;
    private MyGrouponPresenter myGrouponPresenter = new MyGrouponPresenter();
    private String isEnd = "0";

    @Override
    public int getlayoutId() {
        return R.layout.fragment_groupon_all;
    }


    public void setHander(Handler hander) {
        this.handler = hander;
    }

    @Override
    public void initEventAndData() {

        myGrouponPresenter.onCreate(getActivity(), this);
        myGrouponPresenter.getMyGrouponData("1", "20", isEnd, ProApplication.SESSIONID(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_all_groupon.setLayoutManager(linearLayoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myGrouponPresenter.getMyGrouponData("1", "20", isEnd, ProApplication.SESSIONID(getActivity()));
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.TEAMID, grouponListBeans.get(position).getTeamId() + "");
        bundle.putBoolean("over",false);
        UiHelper.launcherBundle(getActivity(), GrouponDetailActivity.class, bundle);
    }

    @Override
    public void getGrouponDataSuccess(ArrayList<GrouponListBean> grouponListBeans) {
        this.grouponListBeans = grouponListBeans;
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        if (myGrouponAdapter == null) {
            myGrouponAdapter = new MyGrouponAdapter(getActivity(), grouponListBeans,isEnd);
            rv_all_groupon.setAdapter(myGrouponAdapter);
            myGrouponAdapter.setItemClickListener(this);
        } else {
            myGrouponAdapter.setData(grouponListBeans);
        }
    }

    @Override
    public void getGrouponDataFail(String msg) {

    }
}
