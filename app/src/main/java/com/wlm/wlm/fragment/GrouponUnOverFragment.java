package com.wlm.wlm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MyGrouponAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.entity.GrouponListBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/9/16.
 */
public class GrouponUnOverFragment extends BaseFragment {

    @BindView(R.id.rv_all_groupon)
    RecyclerView rv_all_groupon;

    private MyGrouponAdapter myGrouponAdapter;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_groupon_all;
    }

    @Override
    public void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_all_groupon.setLayoutManager(linearLayoutManager);

    }

    public void setGrouponData(ArrayList<GrouponListBean> grouponListBeans){
//        if (myGrouponAdapter == null) {
            myGrouponAdapter = new MyGrouponAdapter(getActivity(), grouponListBeans);
            rv_all_groupon.setAdapter(myGrouponAdapter);
//        }else {
//            myGrouponAdapter.setData(grouponListBeans);
//        }
    }
}