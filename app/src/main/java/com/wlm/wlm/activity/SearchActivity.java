package com.wlm.wlm.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.ChooseGrouponAdapter;
import com.wlm.wlm.adapter.SearchAdapter;
import com.wlm.wlm.adapter.TbAdapter;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfSearchContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.HotHomeBean;
import com.wlm.wlm.entity.SearchBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.SearchResultPresenter;
import com.wlm.wlm.presenter.SelfSearchPresenter;
import com.wlm.wlm.ui.FlowLayout;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.MallType;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/21.
 */

public class SearchActivity extends BaseActivity implements SelfSearchContract, TbHotGoodsAdapter.OnItemClickListener, TbAdapter.OnItemClickListener, IGoodsTypeListener, SearchAdapter.OnItemClickListener {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rl_search_top)
    RelativeLayout mSearchTopLayout;
    @BindView(R.id.search_line)
    View mSearchTopLine;
    @BindView(R.id.flow_search_history)
    FlowLayout mFlowLayout;
    @BindView(R.id.flow_search_recommend)
    FlowLayout flow_search_recommend;
    @BindView(R.id.ll_result)
    LinearLayout ll_result;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.ll_search_goods_type)
    LinearLayout ll_search_goods_type;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.rv_search_goods)
    RecyclerView rv_search_goods;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.tv_search_et)
    TextView tv_search_et;
    @BindView(R.id.ic_search_icon)
    ImageView ic_search_icon;

    SelfSearchPresenter selfSearchPresenter = new SelfSearchPresenter();
    private PopupWindow popupWindow;
    private List<String> list = new ArrayList<>();
    private String SortField = "add_time";
    private TbHotGoodsAdapter tbHotGoodsAdapter;
    private TbMaterielBean tbDisCountBean;
    private ArrayList<HotHomeBean> hotHomeBeans = new ArrayList<>();
    private LinearLayout.LayoutParams layoutParams;
    private String goodsType = "";
    private String orderby = "0";
    private SearchAdapter searchAdapter;
    private ArrayList<GoodsListBean> goodsListBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        if(getIntent() != null && getIntent().getBundleExtra(WlmUtil.TYPEID) != null && getIntent().getBundleExtra(WlmUtil.TYPEID).getString("id") != null){
            goodsType = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("id");
            ll_top.setText(MallType.getVipById(goodsType).getTypeName());
        }


        selfSearchPresenter.onCreate(this,this);

        List<SearchBean> searchBean =  DBManager.getInstance(this).querySearchBean(MainFragmentActivity.username);

        selfSearchPresenter.selfSearch(ProApplication.SESSIONID(this));

        Collections.reverse(searchBean);

        ActivityUtil.addActivity(this);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 15, 10, 10);
        if (searchBean.size() > 0) {
            //往容器内添加TextView数据
            if (mFlowLayout != null) {
                mFlowLayout.removeAllViews();
            }
            for (int i = 0; i < searchBean.size(); i++) {
                TextView tv = new TextView(this);
                tv.setText(searchBean.get(i).getSearchname());
                tv.setTextColor(getResources().getColor(R.color.pop_text_bg));
                tv.setMaxEms(10);
                tv.setTextSize(12);
                tv.setSingleLine();
                tv.setBackgroundResource(R.drawable.shape_search_item_select);
                tv.setLayoutParams(layoutParams);
                mFlowLayout.addView(tv, layoutParams);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEtSearch.setText(((TextView)v).getText());
                        doSearch();
                    }
                });
            }
            mFlowLayout.specifyLines(6);
        }

        ll_top.setListener(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px
        boolean includeEdge = false;
        rv_search_goods.addItemDecoration(new SpaceItemDecoration(spanCount, spacing,0));

        rv_search_goods.setLayoutManager(gridLayoutManager);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    ll_result.setVisibility(View.GONE);
                    ll_search_goods_type.setVisibility(View.GONE);
                    goodsType = "";
                    tv_search.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                        if (popupWindow != null && popupWindow.isShowing()){
                            popupWindow.dismiss();
                        }
                }
            }
        });

        mEtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtSearch.setSingleLine();
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!mEtSearch.getText().toString().isEmpty()) {
                        //搜索
                        doSearch();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void doSearch(){


        if(!mEtSearch.getText().toString().isEmpty()) {
            if (DBManager.getInstance(this).querySearch(mEtSearch.getText().toString()).size() == 0) {
                SearchBean searchBean = new SearchBean();
                searchBean.setUsername(MainFragmentActivity.username);
                searchBean.setSearchname(mEtSearch.getText().toString());
                if (DBManager.getInstance(this).querySearch(mEtSearch.getText().toString()).size() == 0) {
                    DBManager.getInstance(this).insertSearchBean(searchBean);
                }
            }
        }

        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));
        mSearchTopLayout.setBackgroundColor(getResources().getColor(R.color.setting_title_color));
        tv_search.setVisibility(View.GONE);
        ll_search.setVisibility(View.VISIBLE);
        tv_search_et.setText(mEtSearch.getText().toString());
        mEtSearch.setVisibility(View.INVISIBLE);
        ic_search_icon.setVisibility(View.GONE);

        ll_result.setVisibility(View.VISIBLE);
        ll_search_goods_type.setVisibility(View.VISIBLE);
        selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());
    }

    @OnClick({R.id.tv_search,R.id.ic_search_history_delete,R.id.ll_search_goods_type,R.id.ll_search,R.id.rl_search_top})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_search:

                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }


                doSearch();


                break;

            case R.id.ll_search_goods_type:

                finish();

                break;

            case R.id.ic_search_history_delete:

                DBManager.getInstance(this).deleteAllSearchBean();
                mFlowLayout.removeAllViews();

                break;

            case R.id.ll_search:

                mEtSearch.setVisibility(View.VISIBLE);
                ll_search.setVisibility(View.GONE);
                ll_result.setVisibility(View.GONE);
                ll_search_goods_type.setVisibility(View.GONE);
                tv_search.setVisibility(View.VISIBLE);
                ic_search_icon.setVisibility(View.VISIBLE);
                mEtSearch.setText("");
                Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
                mSearchTopLayout.setBackgroundColor(getResources().getColor(R.color.white));

                break;

            case R.id.rl_search_top:

                if (mEtSearch != null && mEtSearch.getText().toString().length() > 0){
                    ll_search.setVisibility(View.GONE);
                    ll_search_goods_type.setVisibility(View.GONE);
                    tv_search.setVisibility(View.VISIBLE);
                    ll_result.setVisibility(View.GONE);
                    mEtSearch.setVisibility(View.VISIBLE);
                    ic_search_icon.setVisibility(View.VISIBLE);
                    Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
                    mSearchTopLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    mEtSearch.setSelection(mEtSearch.getText().toString().length());
                }

                break;
        }
    }

    @Override
    public void onSuccess(final ArrayList<TbMaterielBean> tbMaterielBeans) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    protected void onDestroy() {
        ActivityUtil.removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onSelfSuccess(ArrayList<String> selfGoodsBeans) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 15, 10, 10);
        if (selfGoodsBeans.size() > 0) {
            //往容器内添加TextView数据
            if (flow_search_recommend != null) {
                flow_search_recommend.removeAllViews();
            }
            for (int i = 0; i < selfGoodsBeans.size(); i++) {
                TextView tv = new TextView(this);
                tv.setText(selfGoodsBeans.get(i));
                tv.setTextColor(getResources().getColor(R.color.pop_text_bg));
                tv.setMaxEms(10);
                tv.setSingleLine();
                tv.setBackgroundResource(R.drawable.shape_search_item_select);
                tv.setLayoutParams(layoutParams);
                flow_search_recommend.addView(tv, layoutParams);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEtSearch.setText(((TextView) v).getText());

                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("position", 20);
                    bundle1.putString(WlmUtil.GOODSNAME, mEtSearch.getText().toString());
                    UiHelper.launcherBundle(SearchActivity.this, SearchResultActivity.class, bundle1);
                    if (ActivityUtil.activityList.size() > 1){
                        ActivityUtil.removeOldActivity();
                    }
                    }
                });
            }
        }
    }

    @Override
    public void onSelfFail(String msg) {

    }

    @Override
    public void getSearchResultSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        if (refreshLayout != null && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        rv_search_goods.setVisibility(View.VISIBLE);

        this.goodsListBeans = goodsListBeans;
        if(searchAdapter == null){
            searchAdapter = new SearchAdapter(this,goodsListBeans);
            searchAdapter.setItemClickListener(this);
            rv_search_goods.setAdapter(searchAdapter);
        }
    }

    @Override
    public void getSearchResultFail(String msg) {
        if (msg.contains("查无数据")){
            rv_search_goods.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
        if (goodsListBeans != null){
            if (goodsListBeans.get(position).getGoodsType().equals("2")){
                Bundle bundle = new Bundle();
                bundle.putSerializable(WlmUtil.GROUPONGOODS,goodsListBeans.get(position));
                UiHelper.launcherBundle(this,GrouponGoodsDetailActivity.class,bundle);
            }else {
                Bundle bundle = new Bundle();
                bundle.putString(WlmUtil.GOODSID,goodsListBeans.get(position).getGoodsId());
                bundle.putString(WlmUtil.TYPE,WlmUtil.getType(goodsListBeans.get(position).getGoodsType()));
                UiHelper.launcherBundle(this,SelfGoodsDetailActivity.class,bundle);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){

            if (requestCode == 0x4431  || requestCode == 0x4432) {
                List<SearchBean> searchBean = DBManager.getInstance(this).querySearchBean(MainFragmentActivity.username);

                Collections.reverse(searchBean);

                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 15, 10, 10);
                if (searchBean.size() > 0) {
                    //往容器内添加TextView数据
                    if (mFlowLayout != null) {
                        mFlowLayout.removeAllViews();
                    }
                    for (int i = 0; i < searchBean.size(); i++) {
                        TextView tv = new TextView(this);
                        tv.setText(searchBean.get(i).getSearchname());
                        tv.setTextColor(getResources().getColor(R.color.pop_text_bg));
                        tv.setMaxEms(10);
                        tv.setSingleLine();
                        tv.setBackgroundResource(R.drawable.shape_search_item_select);
                        tv.setLayoutParams(layoutParams);
                        mFlowLayout.addView(tv, layoutParams);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mEtSearch.setText(((TextView) v).getText());

                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("position", 20);
                                bundle1.putString(WlmUtil.GOODSNAME, mEtSearch.getText().toString());
                                UiHelper.launcherBundle(SearchActivity.this, SearchResultActivity.class, bundle1);
                                if (ActivityUtil.activityList.size() > 1){
                                    ActivityUtil.removeOldActivity();
                                }
                            }
                        });
                    }

                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1://默认排序
                selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());
                ll_top.setText(getString(R.string.groupon_all));
                break;

            case 2://选择商城

                View view = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
                RecyclerView recyclerView = view.findViewById(R.id.rv_groupon);

                ChooseGrouponAdapter chooseGrouponAdapter = new ChooseGrouponAdapter(this,1);

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
                        ll_top.setText(MallType.values()[position].getTypeName());
                        goodsType = MallType.values()[position].getTypeId();
                        if (goodsType.equals("0")){
                            goodsType = "";
                        }
                        selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());
                        popupWindow.dismiss();
                    }
                });

                break;


            case 3://销量上
                orderby = "1";
                selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());

                break;


            case 4://销量下

                orderby = "2";
                selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());

                break;

            case 5://价格上

                orderby = "3";
//                isGrouponType = false;
                selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());

                break;

            case 6://价格下

                orderby = "4";
                selfSearchPresenter.getData("1", WlmUtil.PAGE_COUNT,goodsType,"",mEtSearch.getText().toString());

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
