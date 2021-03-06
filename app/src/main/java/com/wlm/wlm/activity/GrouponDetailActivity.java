package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GrouponDetailAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.GrouponDetailContract;
import com.wlm.wlm.entity.GrouponDetailBean;
import com.wlm.wlm.entity.JoinGrouponBean;
import com.wlm.wlm.presenter.GrouponDetailPresenter;
import com.wlm.wlm.ui.CountdownView;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.ui.PriceTextView;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXEntryActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/8/21.
 */

public class GrouponDetailActivity extends BaseActivity implements GrouponDetailContract {

    @BindView(R.id.tv_grouponing)
    TextView tv_grouponing;
    @BindView(R.id.rv_groupon_list)
    RecyclerView rv_groupon_list;
    @BindView(R.id.tv_groupon_price)
    PriceTextView tv_groupon_price;
    @BindView(R.id.tv_rush_time)
    CountdownView tv_rush_time;
    @BindView(R.id.tv_goods_title)
    TextView tv_goods_title;
    @BindView(R.id.tv_grounon_info)
    TextView tv_grounon_info;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_friends_num)
    TextView tv_friends_num;
    @BindView(R.id.riv_rc)
    RoundImageView riv_rc;
    @BindView(R.id.iv_goods_pic)
    CustomRoundAngleImageView iv_goods_pic;
    @BindView(R.id.tv_rule)
    TextView tv_rule;
    @BindView(R.id.tv_join_groupon)
    TextView tv_join_groupon;
    @BindView(R.id.rl_more)
    RelativeLayout rl_more;
    @BindView(R.id.tv_rush_time_flash_sale)
    CountdownView tv_rush_time_flash_sale;

    private String teamId;
    private GrouponDetailPresenter getGoodsDetail = new GrouponDetailPresenter();
    private ArrayList<JoinGrouponBean> joinGrouponBeans;
    private GrouponDetailBean grouponDetailBean;
    IWXAPI iwxapi = null;
    private boolean isEnd = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_detail;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor1(this, getResources().getColor(R.color.setting_title_color));

        iwxapi = WXAPIFactory.createWXAPI(this, WlmUtil.APP_ID, true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        WXEntryActivity.wxType(WlmUtil.WXTYPE_SHARED);

        ActivityUtil.addHomeActivity(this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        getGoodsDetail.onCreate(this, this);

        if (bundle != null && bundle.getString(WlmUtil.TEAMID) != null) {
            teamId = bundle.getString(WlmUtil.TEAMID);
            isEnd = bundle.getBoolean("over",false);
        }

        tv_rush_time.setVisibility(View.GONE);

        tv_rush_time_flash_sale.setVisibility(View.VISIBLE);


        getGoodsDetail.getGoodsDetail(teamId, ProApplication.SESSIONID(this));

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        Picasso.with(this).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "") + "").error(R.mipmap.ic_adapter_error).into(riv_rc);

        if (isEnd){
            tv_join_groupon.setText("拼团已经完成");
            tv_join_groupon.setTextColor(getResources().getColor(R.color.gray));
            tv_join_groupon.setBackground(getResources().getDrawable(R.drawable.shape_groupon_black_btn));
            rl_more.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.ll_back, R.id.tv_join_groupon, R.id.rl_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:

                finish();

                break;

            case R.id.tv_join_groupon:

            case R.id.rl_more:

                /*Picasso.with(this).load(ProApplication.HEADIMG + grouponDetailBean.getGoodsImg()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);

                        String path = "/pages/Grouping/wantGrouping/wantGrouping?TeamId=" + grouponDetailBean.getTeamId() + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME, "");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        WlmUtil.setShared(iwxapi, path, grouponDetailBean.getGoodsName(), grouponDetailBean.getGoodsName(), baos.toByteArray());
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });*/
                if (!isEnd) {

                    Bundle bundle = new Bundle();
                    bundle.putString("shared", "group");
                    bundle.putString("teamid",teamId);
                    bundle.putSerializable(WlmUtil.GOODS, grouponDetailBean);
                    UiHelper.launcherBundle(this, MyQrCodeActivity.class, bundle);
                }

                break;
        }
    }

    @Override
    public void getDataSuccess(GrouponDetailBean goodsListBean) {

        this.grouponDetailBean = goodsListBean;

        Picasso.with(this).load(ProApplication.HEADIMG + goodsListBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(iv_goods_pic);

        tv_groupon_price.setText(goodsListBean.getPrice() + "");

        tv_goods_title.setText(goodsListBean.getGoodsName());

        tv_grouponing.setBackground(null);
        tv_grouponing.setText("拼团中");

        tv_grounon_info.setText(goodsListBean.getGoodsSmallName());

        if (WlmUtil.isCountdown(goodsListBean.getBeginDate(), goodsListBean.getEndDate(), tv_rush_time_flash_sale) == 0) {
            tv_end_time.setText("至开始");
        } else if (WlmUtil.isCountdown(goodsListBean.getBeginDate(), goodsListBean.getEndDate(), tv_rush_time_flash_sale) == 1) {
            tv_end_time.setText("至截止");
        } else {
            tv_grouponing.setVisibility(View.GONE);
        }

        if (goodsListBean.getTeamType() == 1) {
//            tv_rule.setText("三人团：团长佣金获得规则为25%-35%-40%");
        } else if (goodsListBean.getTeamType() == 2) {
//            tv_rule.setText("五人团：团长佣金获得规则为10%-20%-20-25%-25%");
        }

        if (goodsListBean.getListUser() != null) {
            joinGrouponBeans = goodsListBean.getListUser();
            tv_friends_num.setText(joinGrouponBeans.size() + "");

            GrouponDetailAdapter grouponParticipantAdapter = new GrouponDetailAdapter(this, joinGrouponBeans);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
            gridLayoutManager.setOrientation(LinearLayout.VERTICAL);

            rv_groupon_list.setLayoutManager(gridLayoutManager);
            rv_groupon_list.setAdapter(grouponParticipantAdapter);
        }

    }

    @Override
    public void getDataFail(String msg) {
        toast(msg);
    }


}
