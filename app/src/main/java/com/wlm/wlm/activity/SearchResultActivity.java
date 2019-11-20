package com.wlm.wlm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.ChooseGrouponAdapter;
import com.wlm.wlm.adapter.SearchAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.SearchResultContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.SearchResultPresenter;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.MallType;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/24.
 */
public class SearchResultActivity extends BaseActivity implements IGoodsTypeListener, SearchResultContract, SearchAdapter.OnItemClickListener {

    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.rv_search_goods)
    RecyclerView rv_search_goods;

    private String orderby = "0";
    private SearchResultPresenter searchResultPresenter = new SearchResultPresenter();
    private SearchAdapter searchAdapter;
    private String goodsType = "";
    private String goodsName;
    private ArrayList<GoodsListBean> goodsListBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor(this, getResources().getColor(R.color.setting_title_color));

        ActivityUtil.addActivity(this);


        ll_top.setListener(this);

        goodsName = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.GOODSNAME);

        searchResultPresenter.onCreate(this, this);
        searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px
        boolean includeEdge = false;
        rv_search_goods.addItemDecoration(new SpaceItemDecoration(spanCount, spacing, 0));

        rv_search_goods.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType) {
            case 1://默认排序
                searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);
                ll_top.setText(getString(R.string.groupon_all));
                break;

            case 2://选择商城

                View view = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
                RecyclerView recyclerView = view.findViewById(R.id.rv_groupon);

                ChooseGrouponAdapter chooseGrouponAdapter = new ChooseGrouponAdapter(this, 1);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(linearLayoutManager);

                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                });

                recyclerView.setAdapter(chooseGrouponAdapter);

                final PopupWindow popupWindow = new PopupWindow(view,
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(ll_top);
                chooseGrouponAdapter.setOnItemClick(new ChooseGrouponAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        goodsType = MallType.values()[position].getTypeId();
                        searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);
                        popupWindow.dismiss();
                    }
                });

                break;


            case 3://销量上
                orderby = "1";
                searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);

                break;


            case 4://销量下

                orderby = "2";
                searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);

                break;

            case 5://价格上

                orderby = "3";
//                isGrouponType = false;
                searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);

                break;

            case 6://价格下

                orderby = "4";
                searchResultPresenter.getData("1", WlmUtil.PAGE_COUNT, goodsType, "", goodsName);

                break;
        }
    }

    @OnClick({R.id.lin_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_list:

                finish();

                break;
        }
    }

    @Override
    public void getSearchResultSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        this.goodsListBeans = goodsListBeans;
        if (searchAdapter == null) {
            searchAdapter = new SearchAdapter(this, goodsListBeans);
            searchAdapter.setItemClickListener(this);
            rv_search_goods.setAdapter(searchAdapter);
        }
    }

    @Override
    public void getSearchResultFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        if (goodsListBeans != null) {

            Bundle bundle = new Bundle();
            bundle.putString(WlmUtil.GOODSID, goodsListBeans.get(position).getGoodsId());
            bundle.putString(WlmUtil.TYPE, WlmUtil.getType(goodsListBeans.get(position).getGoodsType()));
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);

        }
    }
}
