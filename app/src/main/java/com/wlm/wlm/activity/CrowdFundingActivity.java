package com.wlm.wlm.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GrouponAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.CrowdFundingContract;
import com.wlm.wlm.entity.FlashBean;
import com.wlm.wlm.interf.IGoodsTypeListener;
import com.wlm.wlm.presenter.CrowdFundingPresenter;
import com.wlm.wlm.ui.CustomBannerView;
import com.wlm.wlm.ui.TopLinearlayout;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.xw.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CrowdFundingActivity extends BaseActivity implements IGoodsTypeListener, CrowdFundingContract {

    @BindView(R.id.bannerView)
    Banner banner;
    @BindView(R.id.ll_top)
    TopLinearlayout ll_top;
    @BindView(R.id.rv_crowd_funding)
    XRecyclerView rv_crowd_funding;

    GrouponAdapter grouponAdapter = null;

    CrowdFundingPresenter crowdFundingPresenter = new CrowdFundingPresenter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_crowd_funding;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.bg_crowdfunding));

//        startBanner(null);
        crowdFundingPresenter.onCreate(this,this);
        crowdFundingPresenter.setFlash("3");
        ll_top.setListener(this);
    }


    @Override
    public void getSortType(int sortType) {
        switch (sortType){
            case 1:

                break;

            case 3://销量上

                break;

            case 4://销量下


                break;

            case 5://价格上


                break;

            case 6://价格下


                break;
        }
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
    public void onFlashSuccess(ArrayList<FlashBean> flashBeans) {

        CustomBannerView.startBanner(flashBeans,banner,this,false);

    }

    @Override
    public void onFlashFail(String msg) {

    }
}
