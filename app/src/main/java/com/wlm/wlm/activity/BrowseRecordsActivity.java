package com.wlm.wlm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.RecordAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.CollectContract;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.CollectPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.wlm.wlm.util.WlmUtil.PAGE_COUNT;

/**
 * Created by LG on 2018/11/21.
 */

public class BrowseRecordsActivity extends BaseActivity implements OnTitleBarClickListener, RecordAdapter.OnItemClickListener, CollectContract {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.rv_record)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    List<CollectBean> collectBeans = new ArrayList<>();
    private RecordAdapter recordAdapter;
    private CollectPresenter collectPresenter = new CollectPresenter();
    private boolean isOver = false;
    private String collectId = "";
    private int position = 0;
    private int isChange = 0;
    private static int record_result = 0x0121;
    private int PageIndex = 1;
    private GridLayoutManager gridLayoutManager;
    private int lastVisibleItem = 0;
    private int maxPage = 1;
    ArrayList<Long> longs = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_browse_record;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);

        collectPresenter.onCreate(this, this);

        position = getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("position");

        collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, "1", ProApplication.SESSIONID(this));


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PageIndex = 1;
                collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, "1", ProApplication.SESSIONID(BrowseRecordsActivity.this));

            }
        });

        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px
//        api.registerApp("wx3686dfb825618610");

        boolean includeEdge = false;
        recyclerView.addItemDecoration(new SpaceItemDecoration(spanCount, spacing, 0));
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recordAdapter != null) {
                        if (lastVisibleItem + 1 == recordAdapter.getItemCount()) {
                            if (position == 2) {
                                if (PageIndex < maxPage) {
                                    PageIndex++;
                                    collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, "1", ProApplication.SESSIONID(BrowseRecordsActivity.this));
                                }
                            }
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


    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {

            Bundle bundle = new Bundle();
            bundle.putString(WlmUtil.GOODSID, collectBeans.get(position).getGoodsId() + "");

            switch (collectBeans.get(position).getGoodsType()) {
                case 1:

                    bundle.putString(WlmUtil.TYPE, WlmUtil.INTEGRAL);

                    break;

                case 4:

                    bundle.putString(WlmUtil.TYPE, WlmUtil.VIP);

                    break;

                case 8:

                    bundle.putString(WlmUtil.TYPE, WlmUtil.MANUFACURE);

                    break;


            }


            UiHelper.launcherForResultBundle(this, SelfGoodsDetailActivity.class, record_result, bundle);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == record_result) {
                if (position == 2) {
                    isChange = 2;
                    PageIndex = 1;
                    collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, "1", ProApplication.SESSIONID(this));
                }
            }
        }
    }

    @Override
    public void getCollectDataSuccess(ArrayList<CollectBean> collectBeans, String maxPage) {

        if (maxPage != null && maxPage.toString().length() >= 1) {
            this.maxPage = Integer.valueOf(maxPage);
        }

        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        if (recordAdapter == null) {
            this.collectBeans = collectBeans;
            recordAdapter = new RecordAdapter(this, collectBeans);
            recyclerView.setAdapter(recordAdapter);
            recordAdapter.setItemClickListener(this);
        } else {
            if (isChange == 2) {
                this.collectBeans = collectBeans;
                recordAdapter.setData(collectBeans);
            } else {
                if (PageIndex != 1) {
                    collectBeans.addAll(collectBeans);
                } else {
                    this.collectBeans = collectBeans;
                }
                recordAdapter.setData(collectBeans);
            }
        }
    }

    @Override
    public void getCollectFail(String msg) {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (msg.contains("查无数据")) {
            recyclerView.setVisibility(View.GONE);
        }
    }


}
