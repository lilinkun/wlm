package com.wlm.wlm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbAdapter;
import com.wlm.wlm.adapter.TbGoodsAdapter;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.SelfSearchContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.HomeHeadBean;
import com.wlm.wlm.entity.HotHomeBean;
import com.wlm.wlm.entity.SearchBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.TbGoodsBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.presenter.SelfSearchPresenter;
import com.wlm.wlm.ui.FlowLayout;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wlm.wlm.util.LzyydUtil.PAGE_COUNT;

/**
 * Created by LG on 2018/11/21.
 */

public class SearchActivity extends BaseActivity implements SelfSearchContract, TbHotGoodsAdapter.OnItemClickListener, TbAdapter.OnItemClickListener {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rl_search_top)
    RelativeLayout mSearchTopLayout;
    @BindView(R.id.search_line)
    View mSearchTopLine;
    @BindView(R.id.tv_search_goods_type)
    TextView mSearchGoodsType;
    @BindView(R.id.flow_search_history)
    FlowLayout mFlowLayout;
    @BindView(R.id.flow_search_recommend)
    FlowLayout flow_search_recommend;

    SelfSearchPresenter selfSearchPresenter = new SelfSearchPresenter();
    private PopupWindow popupWindow;
    private TextView tvSelf,tvTb,tvJd;
    private List<String> list = new ArrayList<>();
    private String SortField = "add_time";
    private TbHotGoodsAdapter tbHotGoodsAdapter;
    private TbMaterielBean tbDisCountBean;
    private TbAdapter tbAdapter;
    private ArrayList<HotHomeBean> hotHomeBeans = new ArrayList<>();
    private LinearLayout.LayoutParams layoutParams;

    /*
    *  type 中　１为自营　２为淘宝　３为京东
    * */
    private int type = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        selfSearchPresenter.attachView(this);
        selfSearchPresenter.onCreate(this);

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
                tv.setSingleLine();
                tv.setBackgroundResource(R.drawable.shape_search_item_select);
                tv.setLayoutParams(layoutParams);
                mFlowLayout.addView(tv, layoutParams);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEtSearch.setText(((TextView)v).getText());

                        if (type == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("goodsname", ((TextView) v).getText().toString());
                            UiHelper.launcherForResultBundle(SearchActivity.this, SelfGoodsTypeActivity.class,0x4432, bundle);

                            if (ActivityUtil.activityList.size() > 1){
                                ActivityUtil.removeOldActivity();
                            }
                        }else if(type == 2){
//                            selfSearchPresenter.setList("1", PAGE_COUNT,"",mEtSearch.getText().toString(),LzyydUtil.strs[0], ProApplication.SESSIONID(SearchActivity.this));
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("position",20);
                            bundle1.putString("str",mEtSearch.getText().toString());
                            UiHelper.launcherBundle(SearchActivity.this,GoodsTypeActivity.class,bundle1);
                        }

//                        selfSearchPresenter.selfSearch("1",PAGE_COUNT,"132",SortField,mEtSearch.getText().toString(), ProApplication.SESSIONID(SearchActivity.this));
                    }
                });
            }

            mFlowLayout.specifyLines(6);

        }

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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



        View popView = LayoutInflater.from(this).inflate(R.layout.popup_search_type, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvSelf = popView.findViewById(R.id.tv_home_self);
        tvTb = popView.findViewById(R.id.tv_home_tb);
        tvJd = popView.findViewById(R.id.tv_home_jd);
        tvSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                tvSelf.setTextColor(getResources().getColor(R.color.white));
                tvTb.setTextColor(getResources().getColor(R.color.login_title_text));
                tvJd.setTextColor(getResources().getColor(R.color.login_title_text));
                tvSelf.setBackgroundResource(R.drawable.search_text_bg_select);
                tvTb.setBackgroundResource(R.drawable.search_text_bg_unselect);
                tvJd.setBackgroundResource(R.drawable.search_text_bg_unselect);

                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                mSearchGoodsType.setText(R.string.home_self_goods);
            }
        });

        tvTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                tvSelf.setTextColor(getResources().getColor(R.color.login_title_text));
                tvTb.setTextColor(getResources().getColor(R.color.white));
                tvJd.setTextColor(getResources().getColor(R.color.login_title_text));
                tvSelf.setBackgroundResource(R.drawable.search_text_bg_unselect);
                tvTb.setBackgroundResource(R.drawable.search_text_bg_select);
                tvJd.setBackgroundResource(R.drawable.search_text_bg_unselect);
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                mSearchGoodsType.setText(R.string.home_tb);
            }
        });

        tvJd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                tvSelf.setTextColor(getResources().getColor(R.color.login_title_text));
                tvTb.setTextColor(getResources().getColor(R.color.login_title_text));
                tvJd.setTextColor(getResources().getColor(R.color.white));
                tvSelf.setBackgroundResource(R.drawable.search_text_bg_unselect);
                tvTb.setBackgroundResource(R.drawable.search_text_bg_unselect);
                tvJd.setBackgroundResource(R.drawable.search_text_bg_select);
                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                mSearchGoodsType.setText(R.string.home_jd);
            }
        });
        if (getIntent() != null && getIntent().getBundleExtra(LzyydUtil.TYPEID) != null && getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("search") != null && !getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("search").isEmpty()){
            if(getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("search").equals("taobao")) {
                type = 2;
                tvSelf.setTextColor(getResources().getColor(R.color.login_title_text));
                tvTb.setTextColor(getResources().getColor(R.color.white));
                tvJd.setTextColor(getResources().getColor(R.color.login_title_text));
                tvSelf.setBackgroundResource(R.drawable.search_text_bg_unselect);
                tvTb.setBackgroundResource(R.drawable.search_text_bg_select);
                tvJd.setBackgroundResource(R.drawable.search_text_bg_unselect);
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                mSearchGoodsType.setText(R.string.home_tb);
            }
        }


    }


    @OnClick({R.id.tv_search,R.id.ic_search_history_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_search:

                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }

                if(!mEtSearch.getText().toString().isEmpty()){
                    if (DBManager.getInstance(this).querySearch(mEtSearch.getText().toString()).size() == 0) {
                        SearchBean searchBean = new SearchBean();
                        searchBean.setUsername(MainFragmentActivity.username);
                        searchBean.setSearchname(mEtSearch.getText().toString());
                        if ( DBManager.getInstance(this).querySearch(mEtSearch.getText().toString()).size() == 0) {
                            DBManager.getInstance(this).insertSearchBean(searchBean);
                        }
                    }


//                    TextView tv = new TextView(this);
//                    tv.setPadding(28, 10, 28, 10);
//                    tv.setText(mEtSearch.getText().toString());
//                    tv.setMaxEms(10);
//                    tv.setSingleLine();
//                    tv.setBackgroundResource(R.drawable.shape_search_item_select);
//                    tv.setLayoutParams(layoutParams);
//                    mFlowLayout.addView(tv, layoutParams);

                    switch (type){
                        case 1:

                            Bundle bundle = new Bundle();
                            bundle.putString("goodsname",mEtSearch.getText().toString());
                            UiHelper.launcherForResultBundle(SearchActivity.this,SelfGoodsTypeActivity.class,0x4432,bundle);

                            if (ActivityUtil.activityList.size() > 1){
                                ActivityUtil.removeOldActivity();
                            }
//                            selfSearchPresenter.selfSearch("1",PAGE_COUNT,"132",SortField,mEtSearch.getText().toString(), ProApplication.SESSIONID(this));
                            break;
                        case 2:

                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("position",20);
                            bundle1.putString("str",mEtSearch.getText().toString());
                            UiHelper.launcherForResultBundle(this,GoodsTypeActivity.class,0x4431,bundle1);

                            if (ActivityUtil.activityList.size() > 1){
                                ActivityUtil.removeOldActivity();
                            }
//                            selfSearchPresenter.setList("1", PAGE_COUNT,"",mEtSearch.getText().toString(),LzyydUtil.strs[0], ProApplication.SESSIONID(this));
                            break;
                        case 3:

                            break;

                    }

                }else {
                    toast(R.string.input_search_word);
                }

                break;

            case R.id.ll_search_goods_type:

                if (popupWindow == null) {

                    popupWindow.showAsDropDown(mSearchTopLine);




                }else {
                    if (popupWindow.isShowing()){
                        popupWindow.dismiss();
                    }else {
                        popupWindow.showAsDropDown(mSearchTopLine);
                    }
                }


                break;

            case R.id.ic_search_history_delete:

                DBManager.getInstance(this).deleteAllSearchBean();
                mFlowLayout.removeAllViews();

                break;
        }
    }

    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }

    @Override
    public void onSuccess(final ArrayList<TbMaterielBean> tbMaterielBeans) {
        if (tbAdapter == null) {

            Bundle bundle = new Bundle();
            bundle.putInt("position",20);
            bundle.putString("str",mEtSearch.getText().toString());
            UiHelper.launcherBundle(this,GoodsTypeActivity.class,bundle);

           /* tbAdapter = new TbAdapter(this, tbMaterielBeans);
            tbAdapter.setItemClickListener(new TbAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("discount",tbMaterielBeans.get(position));
                    UiHelper.launcherBundle(SearchActivity.this, BuyGoodsActivity.class,bundle);
                }
            });
            recyclerView.setAdapter(tbAdapter);
        }else {
            tbHotGoodsAdapter.setData(hotHomeBeans);*/
        }
        /*if (tbMaterielBeans.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        }*/

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

                        if (type == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("goodsname", ((TextView) v).getText().toString());
                            UiHelper.launcherForResultBundle(SearchActivity.this, SelfGoodsTypeActivity.class, 0x4432, bundle);

                            if (ActivityUtil.activityList.size() > 1) {
                                ActivityUtil.removeOldActivity();
                            }
                        } else if (type == 2) {
//                            selfSearchPresenter.setList("1", PAGE_COUNT,"",mEtSearch.getText().toString(),LzyydUtil.strs[0], ProApplication.SESSIONID(SearchActivity.this));
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("position", 20);
                            bundle1.putString("str", mEtSearch.getText().toString());
                            UiHelper.launcherBundle(SearchActivity.this, GoodsTypeActivity.class, bundle1);
                        }
//                        selfSearchPresenter.selfSearch("1",PAGE_COUNT,"132",SortField,mEtSearch.getText().toString(), ProApplication.SESSIONID(SearchActivity.this));
                    }
                });
            }
        }
    }

    @Override
    public void onSelfFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            Bundle bundle = new Bundle();
            bundle.putString("goodsid", hotHomeBeans.get(position).getGoods_id());
            UiHelper.launcherBundle(this, SelfGoodsDetailActivity.class, bundle);
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

                                if (type == 1) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("goodsname", ((TextView) v).getText().toString());
                                    UiHelper.launcherForResultBundle(SearchActivity.this, SelfGoodsTypeActivity.class, 0x4432, bundle);

                                    if (ActivityUtil.activityList.size() > 1) {
                                        ActivityUtil.removeOldActivity();
                                    }
                                } else if (type == 2) {
//                            selfSearchPresenter.setList("1", PAGE_COUNT,"",mEtSearch.getText().toString(),LzyydUtil.strs[0], ProApplication.SESSIONID(SearchActivity.this));
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putInt("position", 20);
                                    bundle1.putString("str", mEtSearch.getText().toString());
                                    UiHelper.launcherBundle(SearchActivity.this, GoodsTypeActivity.class, bundle1);
                                }
//                        selfSearchPresenter.selfSearch("1",PAGE_COUNT,"132",SortField,mEtSearch.getText().toString(), ProApplication.SESSIONID(SearchActivity.this));
                            }
                        });
                    }

                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
