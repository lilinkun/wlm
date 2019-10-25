package com.wlm.wlm.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

            }

            @Override
            public void onLoadMore() {

            }
        });

        findPresenter.getFindData("1","20");
    }

    @Override
    public void onGetDataSuccess(ArrayList<GoodsDiscoverBean> goodsDiscoverBeans, PageBean pageBean) {
        if (findAdapter == null){
            ArrayList<String> strings = new ArrayList<>();
//            strings.add("http://b-ssl.duitang.com/uploads/item/201208/30/20120830173930_PBfJE.jpeg");
            strings.add("http://192.168.0.144:8080/liguo/1.jpg");
            strings.add("http://192.168.0.144:8080/liguo/2.jpg");
            strings.add("http://192.168.0.144:8080/liguo/3.jpg");
            strings.add("http://192.168.0.144:8080/liguo/4.jpg");
//            strings.add("http://www.forestry.gov.cn/uploadfile/main/2013-6/image/2013-6-19-dbdb3e3f20b644ec959960e9d8308eda.jpg");
//            strings.add("http://pic49.nipic.com/file/20140926/6608733_100333244000_2.jpg");
//            strings.add("http://pic37.nipic.com/20140119/9353120_232654315191_2.jpg");
//            strings.add("http://b-ssl.duitang.com/uploads/blog/201312/12/20131212143029_hNEeN.jpeg");
            findAdapter = new FindAdapter(getActivity(),goodsDiscoverBeans);

            rv_find.setAdapter(findAdapter);
        }
    }

    @Override
    public void onGetDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
