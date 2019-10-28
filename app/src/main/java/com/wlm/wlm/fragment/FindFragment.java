package com.wlm.wlm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.FindAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.contract.FindContract;
import com.wlm.wlm.entity.GoodsDiscoverBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.FindPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UToast;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/10/23.
 */
public class FindFragment extends BaseFragment implements FindContract {

    @BindView(R.id.rv_find)
    XRecyclerView rv_find;
    @BindView(R.id.ll_find)
    LinearLayout ll_find;

    FindPresenter findPresenter = new FindPresenter();

    private FindAdapter findAdapter;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initEventAndData() {

        findPresenter.onCreate(getActivity(),this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_find.setLayoutManager(linearLayoutManager);

        rv_find.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_find.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rv_find.setArrowImageView(R.drawable.iconfont_downgrey);
        rv_find.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rv_find.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                rv_find.setNoMore(true);
            }
        });

        findPresenter.getFindData("1","20");
    }

    @Override
    public void onGetDataSuccess(ArrayList<GoodsDiscoverBean> goodsDiscoverBeans, PageBean pageBean) {
        if (findAdapter == null){
            ArrayList<String> strings = new ArrayList<>();
            findAdapter = new FindAdapter(getActivity(),goodsDiscoverBeans,ll_find);

            rv_find.setAdapter(findAdapter);
        }
    }

    @Override
    public void onGetDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
