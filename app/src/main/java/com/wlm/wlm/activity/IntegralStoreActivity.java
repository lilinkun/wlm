package com.wlm.wlm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.IntegralStoreContract;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.IntegralStorePresenter;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.ui.PagerSlidingTabStrip;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分商城
 * Created by LG on 2019/8/15.
 */
public class IntegralStoreActivity extends BaseActivity implements IntegralStoreContract, CustomSortLayout.SortListerner, IGoodsTypeListener {

    @BindView(R.id.custom_sort)
    CustomSortLayout custom_sort;
    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip tab_strip;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;

    private int position = 0;
    private ArrayList<GoodsListBean> goodsListBeans = null;
    private IntegralStorePresenter integralStorePresenter = new IntegralStorePresenter();
    private String goodstype = "1";
    private int PAGEINDEX = 1;
    private PageBean pageBean;
    private ArrayList<Category1Bean> category1Beans = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();
    Category1Bean category1Bean;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x110:
                    int position = msg.getData().getInt("position");
                    category1Bean = category1Beans.get(position);
                    integralStorePresenter.getData(PAGEINDEX + "", "20", goodstype, category1Bean.getCategoryID(), "0");
                    break;
            }
        }
    };

    @Override

    public int getLayoutId() {
        return R.layout.activity_integral_store;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.integral_bg));

        ActivityUtil.addHomeActivity(this);

        integralStorePresenter.onCreate(this,this);

        integralStorePresenter.getCategoryList("1","100",WlmUtil.GOODSTYPE_INTEGRAL+"");

        custom_sort.setListener(this);

        ll_top.setListener(this);
    }

    @OnClick({R.id.ll_back,R.id.ll_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;

            case R.id.ll_search:

                Bundle bundle = new Bundle();
                bundle.putString("id","1");
                UiHelper.launcherBundle(this,SearchActivity.class,bundle);

                break;
        }
    }

    @Override
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean) {
        this.goodsListBeans = goodsListBeans;
        this.pageBean = pageBean;
        custom_sort.setVisibility(View.VISIBLE);
        custom_sort.setPageIndex(PAGEINDEX,pageBean);
        custom_sort.setData(goodsListBeans, WlmUtil.GOODSTYPE_INTEGRAL);

    }

    @Override
    public void getFail(String msg) {
        if (msg.contains("查无数据")){
            custom_sort.setVisibility(View.GONE);
        }
    }

    @Override
    public void getCategorySuccess(ArrayList<Category1Bean> category1BeanList) {

        Category1Bean categoryBean = new Category1Bean();
        categoryBean.setCategoryID("");
        categoryBean.setCategoryName("全部");
        category1Beans.add(categoryBean);

        category1Beans.addAll(category1BeanList);

        for (Category1Bean category1Bean : category1Beans){
            strings.add(category1Bean.getCategoryName());
        }

        category1Bean = category1Beans.get(0);
        tab_strip.setTitles(strings, 0, handler);
        integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"0");
    }

    @Override
    public void getCategoryFail(String msg) {

    }

    @Override
    public void onRefresh() {
        PAGEINDEX = 1;
        integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"0");
    }

    @Override
    public void onLoadding(int page) {
        this.PAGEINDEX = page;
        integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"0");
    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1:

                integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"0");

                break;

            case 2:

                integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"5");

                break;


            case 3:

                integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"1");

                break;

            case 4:

                integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"2");

                break;

            case 5:

                integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"3");

                break;

            case 6:

                integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,category1Bean.getCategoryID(),"4");

                break;

        }
    }
}
