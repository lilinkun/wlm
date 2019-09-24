package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.TbHotGoodsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.VipContract;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.VipPresenter;
import com.wlm.wlm.ui.FullyGridLayoutManager;
import com.wlm.wlm.ui.GridSpacingItemDecoration;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.ui.SpaceItemDecoration;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/14.
 */
public class VipActivity extends BaseActivity implements VipContract, TbHotGoodsAdapter.OnItemClickListener {

    @BindView(R.id.rv_vip_goods)
    RecyclerView rv_vip_goods;
    @BindView(R.id.tv_vipvalidity)
    TextView tv_vipvalidity;
    @BindView(R.id.tv_userid)
    TextView tv_userid;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.iv_vip_face)
    RoundImageView iv_vip_face;

    private VipPresenter vipPresenter = new VipPresenter();
    private String goodsType = "4";
    private TbHotGoodsAdapter tbHotGoodsAdapter = null;
    ArrayList<GoodsListBean> goodsListBeans = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        vipPresenter.onCreate(this,this);
        vipPresenter.getData("1","20",goodsType,"0","0");

        vipPresenter.getUpdataData(ProApplication.SESSIONID(this));

        GridLayoutManager fullyGridLayoutManager = new GridLayoutManager(this, 2);
        fullyGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        int spanCount = 5; // 2 columns
        int spacing = 20; // 50px
        rv_vip_goods.setLayoutManager(fullyGridLayoutManager);

        rv_vip_goods.addItemDecoration(new SpaceItemDecoration(spanCount, spacing,0));

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);

        if (sharedPreferences.getString(WlmUtil.VIPVALIDITY,"") != null && !sharedPreferences.getString(WlmUtil.VIPVALIDITY,"").equals("")) {
            tv_vipvalidity.setText("有效期至 " + sharedPreferences.getString(WlmUtil.VIPVALIDITY, ""));
        }else {
            tv_vipvalidity.setVisibility(View.GONE);
        }

        tv_username.setText(sharedPreferences.getString(WlmUtil.USERNAME,"") + "");
        if (tv_username.getText().toString().trim().length() > 3) {
            String str = tv_username.getText().toString();
            tv_userid.setText(str.substring(3,str.length()-1));
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
    public void getDataSuccess(ArrayList<GoodsListBean> integralBean, PageBean page) {
        this.goodsListBeans = integralBean;
        if (tbHotGoodsAdapter == null) {
            tbHotGoodsAdapter = new TbHotGoodsAdapter(this, integralBean, getLayoutInflater());
            rv_vip_goods.setAdapter(tbHotGoodsAdapter);
            tbHotGoodsAdapter.setItemClickListener(this);
        }else {
            tbHotGoodsAdapter.setData(integralBean);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getQrCodeSuccess(LoginBean loginBean) {
        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);
        sharedPreferences.edit().putString(WlmUtil.VIPVALIDITY,loginBean.getVipValidity()).commit();
        Picasso.with(this).load(loginBean.getPortrait()).error(R.mipmap.ic_adapter_error).into(iv_vip_face);
        if (loginBean.getVipValidity() != null && !loginBean.getVipValidity().equals("")) {
            tv_vipvalidity.setText("有效期至 " + loginBean.getVipValidity());
        }else {
            tv_vipvalidity.setVisibility(View.GONE);
        }

    }

    @Override
    public void getQrCodeFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(WlmUtil.GOODSID,goodsListBeans.get(position).getGoodsId());
        bundle.putString(WlmUtil.TYPE,WlmUtil.VIP);
        UiHelper.launcherBundle(this,SelfGoodsDetailActivity.class,bundle);
    }
}
