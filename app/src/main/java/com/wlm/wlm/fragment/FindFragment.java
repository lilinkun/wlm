package com.wlm.wlm.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.FindAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.contract.FindContract;
import com.wlm.wlm.entity.GoodsDiscoverBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.FindPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.WlmUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LG on 2019/10/23.
 */
public class FindFragment extends BaseFragment implements FindContract, FindAdapter.FindListener {

    @BindView(R.id.rv_find)
    XRecyclerView rv_find;
    @BindView(R.id.ll_find)
    LinearLayout ll_find;

    FindPresenter findPresenter = new FindPresenter();

    private FindAdapter findAdapter;
    private int page_index = 1;
    private PageBean pageBean;
    private ArrayList<GoodsDiscoverBean> goodsDiscoverBeans;
    IWXAPI iwxapi = null;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initEventAndData() {

        iwxapi = WXAPIFactory.createWXAPI(getActivity(),WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);

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
                page_index = 1;
                findPresenter.getFindData(page_index+"", WlmUtil.PAGE_COUNT);
            }

            @Override
            public void onLoadMore() {

                if (pageBean != null){
                    if(pageBean.getMaxPage() > page_index){
                        page_index++;
                        findPresenter.getFindData(page_index+"", WlmUtil.PAGE_COUNT);
                    }else {
                        rv_find.setNoMore(true);
                    }
                }

            }
        });

        findPresenter.getFindData(page_index+"",WlmUtil.PAGE_COUNT);
    }

    @Override
    public void onGetDataSuccess(ArrayList<GoodsDiscoverBean> goodsDiscoverList, PageBean pageBean) {
        this.pageBean = pageBean;
        rv_find.refreshComplete();
        rv_find.loadMoreComplete();

        if (findAdapter == null){
            goodsDiscoverBeans = goodsDiscoverList;
            ArrayList<String> strings = new ArrayList<>();
            findAdapter = new FindAdapter(getActivity(),goodsDiscoverBeans,ll_find);
            findAdapter.setFindListener(this);
            rv_find.setAdapter(findAdapter);
        }else {
            if (pageBean.getPageIndex() > 1){
                goodsDiscoverBeans.addAll(goodsDiscoverList);
            }else {
                goodsDiscoverBeans = goodsDiscoverList;
            }
            findAdapter.setData(goodsDiscoverBeans);
        }
    }

    @Override
    public void onGetDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void onShard(final String goodsid,final String goodsname,String imgPath) {

        Picasso.with(getActivity()).load(imgPath).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                String path = "/pages/cart/productdetail/productdetail?GoodsId=" + goodsid + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME,"");

                WlmUtil.setShared(iwxapi,path,goodsname,goodsname,baos.toByteArray());
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


    }
}
