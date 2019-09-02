package com.wlm.wlm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.RecordAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.CollectContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.CollectPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.UtilsLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wlm.wlm.util.LzyydUtil.PAGE_COUNT;

/**
 * Created by LG on 2018/11/21.
 */

public class BrowseRecordsActivity extends BaseActivity implements OnTitleBarClickListener, RecordAdapter.OnItemClickListener, CollectContract, RecordAdapter.OnDeleteListener {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.rv_record)
    RecyclerView recyclerView;
    @BindView(R.id.rl_collect)
    RelativeLayout rl_collect;
    @BindView(R.id.all_checkBox)
    CheckBox mAllCheckBox;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    List<CollectBean> collectBeans = new ArrayList<>();
    List<BrowseRecordBean> browseRecordBeans = new ArrayList<>();
    private RecordAdapter recordAdapter;
    private CollectPresenter collectPresenter = new CollectPresenter();
    private boolean isOver = false;
    private String collectId = "";
    private int position = 0;
    private int isChange = 0;
    private static int record_result = 0x0121;
    private int PageIndex = 1;
    private LinearLayoutManager linearLayoutManager;
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

        collectPresenter.onCreate(this,this);

        position = getIntent().getBundleExtra(LzyydUtil.TYPEID).getInt("position");

        if (position == 2) {
            customTitleBar.setTileName(getResources().getString(R.string.me_collection));
            collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, ProApplication.SESSIONID(this));
        } else if (position == 1) {
            customTitleBar.setTileName(getResources().getString(R.string.me_record));
            customTitleBar.setRightText(getResources().getString(R.string.record_delete));
            browseRecordBeans = DBManager.getInstance(this).queryBrowseBean(MainFragmentActivity.username);
            Collections.reverse(browseRecordBeans);
            ArrayList<BrowseRecordBean> arrayList = (ArrayList<BrowseRecordBean>) browseRecordBeans;
//            getCollectDataSuccess(arrayList);
            recordAdapter = new RecordAdapter(this, null, this,browseRecordBeans,1);
            recyclerView.setAdapter(recordAdapter);
            recordAdapter.setItemClickListener(this);
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (position == 2) {
                    PageIndex = 1;
                    customTitleBar.setTileName(getResources().getString(R.string.me_collection));
                    collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, ProApplication.SESSIONID(BrowseRecordsActivity.this));
                }else if (position == 1){
                    browseRecordBeans = DBManager.getInstance(BrowseRecordsActivity.this).queryBrowseBean(MainFragmentActivity.username);
                    Collections.reverse(browseRecordBeans);
                    if (recordAdapter != null) {
                        recordAdapter.setBrowseData(browseRecordBeans);
                    }
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

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
                                    collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, ProApplication.SESSIONID(BrowseRecordsActivity.this));
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

        mAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (recordAdapter != null) {
                        recordAdapter.onAllClick();
                    }
                } else {
                    if (recordAdapter != null) {
                        recordAdapter.onAllUnSecletClick();
                    }
                }
            }
        });

    }

    @OnClick({R.id.tv_head_right, R.id.tv_delete})
    public void onClick(View view) {

        if (!ButtonUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_head_right:
//                    if (position == 2) {
                        if (isOver) {
                            customTitleBar.setRightText(getResources().getString(R.string.collect_manager));
                            rl_collect.setVisibility(View.GONE);
                        } else {
                            customTitleBar.setRightText(getResources().getString(R.string.personal_over));
                            rl_collect.setVisibility(View.VISIBLE);
                        }
                        isOver = !isOver;
                        if (recordAdapter != null) {
                            recordAdapter.setDelete(isOver);
                        }
//                    } else {
//                        DBManager.getInstance(this).deleteAllBrowseBean();
//                        recyclerView.setVisibility(View.GONE);
//                    }

                    break;

                case R.id.tv_delete:
                    if (this.position == 2) {
                        if (collectId != null && !collectId.isEmpty()) {
                                collectPresenter.deleteCollect(collectId, ProApplication.SESSIONID(BrowseRecordsActivity.this));
                            }
                    }else {
                        if (longs != null && longs.size() > 0) {
                            DBManager.getInstance(BrowseRecordsActivity.this).deleteBrowseBean(longs);
                            if (recordAdapter != null){
                                List<BrowseRecordBean> browseRecordBeans = DBManager.getInstance(BrowseRecordsActivity.this).queryBrowseBean(MainFragmentActivity.username);
                                Collections.reverse(browseRecordBeans);
                                recordAdapter.setBrowseData(browseRecordBeans);
                            }
                        }
                    }

                    break;
            }

        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            if (isOver) {
                if (recordAdapter != null) {
                    recordAdapter.onClick(position);
                }
            } else {
                Bundle bundle = new Bundle();
                if (this.position == 2) {
                    bundle.putString("goodsid", collectBeans.get(position).getGoods_id() + "");
                }else {
                    bundle.putString("goodsid", browseRecordBeans.get(position).getGoods_id() + "");
                }
                UiHelper.launcherForResultBundle(this, SelfGoodsDetailActivity.class, record_result, bundle);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == record_result) {
                if (position == 2) {
                    isChange = 2;
                    PageIndex = 1;
                    collectPresenter.getCollectDataList(PageIndex + "", PAGE_COUNT, ProApplication.SESSIONID(this));
                }
            }
        }
    }

    @Override
    public void getCollectDataSuccess(ArrayList<CollectBean> collectBeans,String maxPage) {

        if (maxPage != null && maxPage.toString().length() >= 1){
            this.maxPage = Integer.valueOf(maxPage);
        }

        if (refreshLayout != null && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }

        if (recordAdapter == null) {
            this.collectBeans = collectBeans;
            recordAdapter = new RecordAdapter(this, collectBeans, this,null,0);
            recyclerView.setAdapter(recordAdapter);
            recordAdapter.setItemClickListener(this);
        } else {
            if (isChange == 2) {
                this.collectBeans = collectBeans;
                recordAdapter.setData(collectBeans);
            } else {
                if (PageIndex != 1) {
                    collectBeans.addAll(collectBeans);
                }else {
                    this.collectBeans = collectBeans;
                }
                recordAdapter.setData(collectBeans);
            }
        }
    }

    @Override
    public void getCollectFail(String msg) {
        if (refreshLayout != null && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if (msg.contains("查无数据")) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteCollectSuccess(String msg) {
        if (!collectId.isEmpty()) {
            if (collectId.contains(",")) {
                String[] strings = collectId.split(",");
                Iterator<CollectBean> iterator = collectBeans.iterator();
                for (int i = 0; i < strings.length; i++) {
                    for (int j = 0; j < collectBeans.size(); j++) {
//                        CollectBean collectBean = iterator.next();
                        if (collectBeans.get(j).getCollect_id().equals(strings[i])) {
//                            iterator.remove();
                            collectBeans.remove(collectBeans.get(j));
                        }
                    }
                }
            } else {
                for (int j = 0; j < collectBeans.size(); j++) {
                    if (collectBeans.get(j).getCollect_id().equals(collectId)) {
                        collectBeans.remove(j);
                    }
                }
            }
            if (recordAdapter != null) {
                recordAdapter.setDeleteData(collectBeans);
            }
        }

        if (this.position == 2){
            toast("取消收藏成功");
        }
    }

    @Override
    public void deleteCollectFail(String msg) {
        toast(msg);
    }

    @Override
    public void delete(String collectId) {
        this.collectId = collectId;
    }

    @Override
    public void deleteBrowse(ArrayList<Long> longs) {
        this.longs = longs;
    }
}
