package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.WlmBuyContract;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.WlmBuyPresenter;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.ui.PagerSlidingTabStrip;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/10/31.
 */
public class WlmBuyActivity extends BaseActivity implements WlmBuyContract, IGoodsTypeListener, CustomSortLayout.SortListerner {

    @BindView(R.id.rv_point)
    CustomSortLayout custom_sort;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.bannerView)
    Banner bannerView;
    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip tab_strip;

    WlmBuyPresenter wlmBuyPresenter = new WlmBuyPresenter();

    private int pageIndex = 1;
    private int goodstype = WlmUtil.GOODSTYPE_WLMBUY;
    private String orderby = "0";
    private ArrayList<Category1Bean> category1Beans = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();
    private Category1Bean category1Bean = null;
    private PageBean pageBean;

    private ArrayList<FlashBean> flashBeans;
    private ArrayList<GoodsListBean> goodsListBeans;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x110:
                    int position = msg.getData().getInt("position");
                    category1Bean = category1Beans.get(position);
                    wlmBuyPresenter.getData(pageIndex + "", WlmUtil.PAGE_COUNT, goodstype + "", "0", category1Bean.getCategoryID(), false);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_wlmbuy;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor1(this, getResources().getColor(R.color.point_red));

        wlmBuyPresenter.onCreate(this, this);

        ActivityUtil.addHomeActivity(this);
        ll_top.setListener(this);
        custom_sort.setListener(this);

        wlmBuyPresenter.setFlash("6");

        wlmBuyPresenter.getCategoryList("1", "100", WlmUtil.GOODSTYPE_WLMBUY + "");

    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean) {
        this.goodsListBeans = goodsListBeans;
        this.pageBean = pageBean;
        custom_sort.setVisibility(View.VISIBLE);
        custom_sort.setPageIndex(pageIndex, pageBean);
        custom_sort.setData(goodsListBeans, WlmUtil.GOODSTYPE_POINT);
    }

    @Override
    public void getDataFail(String msg) {
        if (msg.contains("查无数据")) {
            custom_sort.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFlashSuccess(ArrayList<FlashBean> flashBeans) {

        this.flashBeans = flashBeans;

        CustomBannerView.startBanner(flashBeans, bannerView, this, true);
    }

    @Override
    public void onFlashFail(String msg) {

    }

    @Override
    public void getCategorySuccess(ArrayList<Category1Bean> category1BeanList) {

        Category1Bean categoryBean = new Category1Bean();
        categoryBean.setCategoryID("");
        categoryBean.setCategoryName("全部");
        category1Beans.add(categoryBean);

        category1Beans.addAll(category1BeanList);
        for (Category1Bean category1Bean : category1Beans) {
            strings.add(category1Bean.getCategoryName());
        }

        category1Bean = category1Beans.get(0);
        tab_strip.setTitles(strings, 0, handler);
        wlmBuyPresenter.getData(pageIndex + "", WlmUtil.PAGE_COUNT, goodstype + "", "0", category1Bean.getCategoryID(), true);

    }

    @Override
    public void getCategoryFail(String msg) {

    }

    @Override
    public void getSortType(int sortType) {
        pageIndex = 1;
        switch (sortType) {
            case 1:
                orderby = "0";

                break;

            case 3://销量上
                orderby = "1";

                break;

            case 4://销量下

                orderby = "2";

                break;

            case 5://价格上

                orderby = "3";

                break;

            case 6://价格下

                orderby = "4";

                break;
        }
        wlmBuyPresenter.getData(pageIndex + "", WlmUtil.PAGE_COUNT, goodstype + "", orderby, category1Bean.getCategoryID(), true);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        wlmBuyPresenter.getData(pageIndex + "", WlmUtil.PAGE_COUNT, goodstype + "", orderby, category1Bean.getCategoryID(), true);
    }

    @Override
    public void onLoadding(int page) {
        pageIndex = page;
        wlmBuyPresenter.getData(pageIndex + "", WlmUtil.PAGE_COUNT, goodstype + "", orderby, category1Bean.getCategoryID(), true);
    }
}
