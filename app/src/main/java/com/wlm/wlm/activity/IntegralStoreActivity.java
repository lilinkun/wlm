package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.IntegralStoreContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.IntegralStorePresenter;
import com.wlm.wlm.ui.CustomSortLayout;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分商城
 * Created by LG on 2019/8/15.
 */
public class IntegralStoreActivity extends BaseActivity implements IntegralStoreContract, CustomSortLayout.SortListerner {

    @BindView(R.id.custom_sort)
    CustomSortLayout custom_sort;

    private int position = 0;
    private ArrayList<GoodsListBean> goodsListBeans = null;
    private IntegralStorePresenter integralStorePresenter = new IntegralStorePresenter();
    private String goodstype = "1";
    private int PAGEINDEX = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x110) {
                Log.v("TAG", "hander");
                position = msg.getData().getInt("position");

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

        integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,"0");

        custom_sort.setListener(this);
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
    public void getSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean) {
        this.goodsListBeans = goodsListBeans;
        custom_sort.setPageIndex(PAGEINDEX,pageBean);
        custom_sort.setData(goodsListBeans, WlmUtil.GOODSTYPE_INTEGRAL);
    }

    @Override
    public void getFail(String msg) {
        toast(msg);
    }

    @Override
    public void onRefresh() {
        this.PAGEINDEX = 1;
        integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,"0");
    }

    @Override
    public void onLoadding(int page) {
        this.PAGEINDEX = page;
        integralStorePresenter.getData(PAGEINDEX+"",WlmUtil.PAGE_COUNT,goodstype,"0");
    }
}
