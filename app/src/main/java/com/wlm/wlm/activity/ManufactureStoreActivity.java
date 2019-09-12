package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.IntegralStoreContract;
import com.wlm.wlm.contract.ManufactureStoreContract;
import com.wlm.wlm.entity.Category1Bean;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.IntegralStorePresenter;
import com.wlm.wlm.presenter.ManufactureStorePresenter;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.PagerSlidingTabStrip;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 制造商称
 * Created by LG on 2019/8/15.
 */
public class ManufactureStoreActivity extends BaseActivity implements IGoodsTypeListener, ManufactureStoreContract {

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.custom_sort)
    CustomSortLayout custom_sort;

//    String[] strs = {"精选","护肤套装", "防晒脱毛", "彩妆香水", "面部精华", "男士服饰", "化妆品", "文体车品", "鞋包", "数码", "内衣"};
    ArrayList<String> strings = new ArrayList<>();
    private ManufactureStorePresenter manufactureStorePresenter = new ManufactureStorePresenter();
    private String goodstype = "8";
    private ArrayList<GoodsListBean> goodsListBeans = null;
    private Category1Bean category1Bean  = null ;
    private ArrayList<Category1Bean> category1Beans = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x110:
                    int position = msg.getData().getInt("position");
                    category1Bean = category1Beans.get(position);
                    manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"0");
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_manufacture;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.store_bg));

        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(this,2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        manufactureStorePresenter.onCreate(this,this);

        manufactureStorePresenter.getCategoryList("1","100");


        ll_top.setListener(this);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1:

                manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"0");

                break;


            case 3:

                manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"1");

                break;

            case 4:

                manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"2");

                break;

            case 5:

                manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"3");

                break;

            case 6:

                manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"4");

                break;

        }
    }

    @Override
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        this.goodsListBeans = goodsListBeans;
        custom_sort.setData(goodsListBeans, WlmUtil.MANUFACURE);
    }

    @Override
    public void getFail(String msg) {

    }

    @Override
    public void getCategorySuccess(ArrayList<Category1Bean> category1Beans) {
        this.category1Beans = category1Beans;
        for (Category1Bean category1Bean : category1Beans){
            strings.add(category1Bean.getCategoryName());
        }

        category1Bean = category1Beans.get(0);
        pagerSlidingTabStrip.setTitles(strings, 0, handler);
        manufactureStorePresenter.getData("1","20",goodstype,category1Bean.getCategoryID(),"0");
    }

    @Override
    public void getCategoryFail(String msg) {

    }
}
