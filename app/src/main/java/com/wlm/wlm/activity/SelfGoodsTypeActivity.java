package com.wlm.wlm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GetPagerAdapter;
import com.wlm.wlm.adapter.RecordAdapter;
import com.wlm.wlm.adapter.SelfGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfGoodsContract;
import com.wlm.wlm.entity.HomeCategoryBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.TBbean;
import com.wlm.wlm.fragment.SelfGoodFragment;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.SelfGoodsPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/12.
 */

public class SelfGoodsTypeActivity extends BaseActivity implements SelfGoodsContract, SelfGoodsAdapter.OnItemClickListener {


    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.view_4)
    View view4;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.tx_price)
    TextView mTvPrice;
    @BindView(R.id.tx_pople)
    TextView mTvPople;
    @BindView(R.id.tx_top)
    TextView mTvTop;
    @BindView(R.id.tx_moren)
    TextView mTvMoren;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_self_goods)
    RecyclerView mSelfGoodsRv;
    @BindView(R.id.text_search)
    TextView text_search;


    private List<TBbean> listTb = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();
    private int bottomLineVisible = 0;
    private boolean isView3 = false;
    private boolean isView4 = false;

    private boolean isPrice = false;
    private boolean isTop = false;
    private boolean isLoad = true;

    private GetPagerAdapter getPagerAdapter;
    private SelfGoodFragment selfGoodFragment;
    private int position = 0;
    private int lastVisibleItem = 0;
    private HashMap<String,BaseFragment> hashMap= new HashMap<>();
    private SelfGoodsPresenter selfGoodsPresenter = new SelfGoodsPresenter();
    private ArrayList<SelfGoodsBean> selfGoodsBeans;
    private SelfGoodsAdapter selfGoodsAdapter;
    private final String PAGE_COUNT = LzyydUtil.PAGE_COUNT;
    private int PAGE_INDEX = 1;
    private String SortField = "add_time";
    private String SortType = "";
    private String goodsname = "";
    private HomeCategoryBean homeCategoryBean;
    private String catid ="";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x110) {
                Log.v("TAG","hander");
                position = msg.getData().getInt("position");
            }
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_self_goods_type;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        homeCategoryBean = (HomeCategoryBean) getIntent().getBundleExtra(LzyydUtil.TYPEID).getSerializable("home");
        if (homeCategoryBean != null && homeCategoryBean.getCat_id() != 0) {
            catid = homeCategoryBean.getCat_id() + "";
        }
        ActivityUtil.addActivity(this);

        if(getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("goodsname") != null  && !getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("goodsname").isEmpty()){
            goodsname = getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("goodsname");
            text_search.setText(goodsname);
        }

        selfGoodsPresenter.onCreate(this,this);

        viewList.add(view4);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        textViewList.add(mTvMoren);
        textViewList.add(mTvPrice);
        textViewList.add(mTvPople);
        textViewList.add(mTvTop);

        //selfGoodsPresenter.getGoodList("1","20",homeCategoryBean.getCat_id(),"add_time", ProApplication.SESSIONID(this));
        selfGoodsPresenter.getGoodList(PAGE_INDEX+"",PAGE_COUNT,catid,SortField,SortType,goodsname, ProApplication.SESSIONID(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PAGE_INDEX = 1;
                selfGoodsPresenter.getGoodList(PAGE_INDEX+"",PAGE_COUNT,catid,SortField,SortType,goodsname, ProApplication.SESSIONID(SelfGoodsTypeActivity.this));
            }
        });

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 2; // 2 columns
        int spacing = 15; // 50px

        boolean includeEdge = false;
        mSelfGoodsRv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mSelfGoodsRv.setLayoutManager(gridLayoutManager);

        mSelfGoodsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (selfGoodsAdapter != null) {
                        if (lastVisibleItem + 1 == selfGoodsAdapter.getItemCount()) {
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
                            if (!isLoad){
                                toast("已到末尾");
                            }else {
                                PAGE_INDEX++;
                                selfGoodsPresenter.getGoodList(PAGE_INDEX + "", PAGE_COUNT, catid, SortField, SortType, goodsname, ProApplication.SESSIONID(SelfGoodsTypeActivity.this));
                            }
//                                }
//                            }, 200);
                        }

                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    @OnClick({R.id.tx_top,R.id.tx_moren, R.id.tx_pople, R.id.tx_price,R.id.lin_list,R.id.text_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_top:
                bottomLine(view2, mTvTop);
                if (isTop){
                    SortType = "1";
                    isTop = !isTop;
                }else {
                    SortType = "0";
                    isTop = !isTop;
                }
                this.SortField = "use_number";
                selfGoodsPresenter.getGoodList(PAGE_INDEX+"",PAGE_COUNT,catid,SortField,SortType,goodsname, ProApplication.SESSIONID(this));
                break;
            case R.id.tx_moren:
                bottomLine(view4, mTvMoren);
                this.SortField = "add_time";
                selfGoodsPresenter.getGoodList(PAGE_INDEX+"",PAGE_COUNT,catid,SortField,SortType,goodsname, ProApplication.SESSIONID(this));
                break;
            case R.id.tx_pople:
                bottomLine(view1, mTvPople);
                this.SortField = "click_count";
                selfGoodsPresenter.getGoodList(PAGE_INDEX+"",PAGE_COUNT,catid,SortField,SortType,goodsname, ProApplication.SESSIONID(this));
                break;
            case R.id.tx_price:
                bottomLine(view3, mTvPrice);
                if (isPrice){
                    SortType = "1";
                    isPrice = !isPrice;
                }else {
                    SortType = "0";
                    isPrice = !isPrice;
                }
                this.SortField = "shop_price";
                selfGoodsPresenter.getGoodList(PAGE_INDEX+"",PAGE_COUNT,catid,SortField,SortType,goodsname, ProApplication.SESSIONID(this));
                break;

            case R.id.lin_list:
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.text_search:
                UiHelper.launcher(this, SearchActivity.class);

                if (ActivityUtil.activityList.size() > 1){
                    ActivityUtil.removeOldActivity();
                }

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void bottomLine(View paramView, TextView paramTextView) {

        if (view3 == paramView) {
            if (isView3) {
                img1.setImageResource(R.mipmap.j_1);
                img2.setImageResource(R.mipmap.j_2_1);
                isView3 = !isView3;
//                selfGoodFragment.onPageChange(4);
            } else {
                img1.setImageResource(R.mipmap.j_1_1);
                img2.setImageResource(R.mipmap.j_2);
                isView3 = !isView3;
//                selfGoodFragment.onPageChange(3);
            }
        } else {
            img1.setImageResource(R.mipmap.j_1);
            img2.setImageResource(R.mipmap.j_2);
            isView3 = false;
        }

        if(view2 == paramView){
            if (isView4) {
                img3.setImageResource(R.mipmap.j_1);
                img4.setImageResource(R.mipmap.j_2_1);
                isView4 = !isView4;
//                selfGoodFragment.onPageChange(4);
            } else {
                img3.setImageResource(R.mipmap.j_1_1);
                img4.setImageResource(R.mipmap.j_2);
                isView4 = !isView4;
//                selfGoodFragment.onPageChange(3);
            }
        } else {
            img3.setImageResource(R.mipmap.j_1);
            img4.setImageResource(R.mipmap.j_2);
            isView4 = false;
        }


        if (view1 == paramView){
//            selfGoodFragment.onPageChange(0);
        }else if(view2 == paramView){
//            selfGoodFragment.onPageChange(1);
        }else if(view3 == paramView){
//            selfGoodFragment.onPageChange(2);
        }

        Object localObject = viewList.iterator();
        while (((Iterator) localObject).hasNext()) {
            View localView = (View) ((Iterator) localObject).next();
            if (localView == paramView) {
                paramView.setVisibility(View.GONE);
            } else {
                localView.setVisibility(View.GONE);
            }
        }

        Object textObject = textViewList.iterator();
        while (((Iterator) textObject).hasNext()) {
            TextView itemView = (TextView) ((Iterator) textObject).next();
            if (itemView == paramTextView) {
                itemView.setTextColor(getResources().getColor(R.color.main_app_color));
            } else {
                itemView.setTextColor(getResources().getColor(R.color.list_divider));
            }
        }

    }

    @Override
    public void getDataSuccess(ArrayList<SelfGoodsBean> selfGoodsBeans,boolean page) {
        this.isLoad = page;
        if (refreshLayout != null  && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if (selfGoodsAdapter == null) {
            this.selfGoodsBeans = selfGoodsBeans;
            selfGoodsAdapter = new SelfGoodsAdapter(this, selfGoodsBeans,2);
            mSelfGoodsRv.setAdapter(selfGoodsAdapter);
            selfGoodsAdapter.setItemClickListener(this);
        }else {
            if (PAGE_INDEX == 1) {
                this.selfGoodsBeans = selfGoodsBeans;
                selfGoodsAdapter.setData(selfGoodsBeans);
            }else {
                this.selfGoodsBeans.addAll(selfGoodsBeans);
                selfGoodsAdapter.setData(this.selfGoodsBeans);
            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        if (refreshLayout != null  && refreshLayout.isRefreshing()){
            refreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodsid", selfGoodsBeans.get(position).getGoods_id());
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);
        }
    }
}

