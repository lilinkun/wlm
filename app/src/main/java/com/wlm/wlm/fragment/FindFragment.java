package com.wlm.wlm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;

/**
 * Created by LG on 2019/10/23.
 */
public class FindFragment extends BaseFragment {

    @BindView(R.id.rv_find)
    RecyclerView rv_find;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initEventAndData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_find.setLayoutManager(linearLayoutManager);


    }
}
