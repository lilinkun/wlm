package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
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
import com.wlm.wlm.util.WlmUtil;

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

    private String teamId;
    private GrouponDetailPresenter getGoodsDetail = new GrouponDetailPresenter();
    private ArrayList<JoinGrouponBean> joinGrouponBeans;
    private GrouponDetailBean grouponDetailBean;
    IWXAPI iwxapi = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupon_detail;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));

        iwxapi = WXAPIFactory.createWXAPI(this,WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        ActivityUtil.addHomeActivity(this);

        Bundle bundle = getIntent().getBundleExtra(WlmUtil.TYPEID);

        getGoodsDetail.onCreate(this,this);

        if (bundle != null && bundle.getString(WlmUtil.TEAMID) != null){
            teamId = bundle.getString(WlmUtil.TEAMID);
        }

        getGoodsDetail.getGoodsDetail(teamId, ProApplication.SESSIONID(this));

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);
        Picasso.with(this).load(sharedPreferences.getString(WlmUtil.ACCOUNT,"")).error(R.mipmap.ic_adapter_error).into(riv_rc);


    }

    @OnClick({R.id.ll_back,R.id.tv_join_groupon})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;

            case R.id.tv_join_groupon:

            case R.id.iv_head_right:

            SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);

            WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
            miniProgramObj.webpageUrl = ProApplication.SHAREDIMG; // 兼容低版本的网页链接
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
            miniProgramObj.userName = "gh_aa9e3dbf8fd0";     // 小程序原始id
            miniProgramObj.path = "/pages/Grouping/wantGrouping/wantGrouping?TeamId="+ grouponDetailBean.getTeamId() + "&UserName=" + sharedPreferences.getString(WlmUtil.USERNAME,"");
            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
            WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
            msg.title = grouponDetailBean.getGoodsName();                    // 小程序消息title
            msg.description = grouponDetailBean.getGoodsName();               // 小程序消息desc

            Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_adapter_error);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            msg.thumbData = baos.toByteArray();

//                msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "";
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
            iwxapi.sendReq(req);

            break;
        }
    }

    @Override
    public void getDataSuccess(GrouponDetailBean goodsListBean) {

        this.grouponDetailBean = goodsListBean;

        Picasso.with(this).load(ProApplication.HEADIMG + goodsListBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(iv_goods_pic);

        tv_groupon_price.setText(goodsListBean.getPrice()+"");

        tv_goods_title.setText(goodsListBean.getGoodsName());

        tv_grouponing.setBackground(null);
        tv_grouponing.setText("拼团中");

        tv_grounon_info.setText(goodsListBean.getGoodsSmallName());

        if(WlmUtil.isCountdown(goodsListBean.getBeginDate(),goodsListBean.getEndDate(),tv_rush_time) == 0){
            tv_end_time.setText("至开始");
        }else if (WlmUtil.isCountdown(goodsListBean.getBeginDate(),goodsListBean.getEndDate(),tv_rush_time) == 1){
            tv_end_time.setText("至截止");
        }else {
            tv_grouponing.setVisibility(View.GONE);
        }

        if (goodsListBean.getListUser() != null){
            joinGrouponBeans = goodsListBean.getListUser();
            tv_friends_num.setText(joinGrouponBeans.size()+"");

            GrouponDetailAdapter grouponParticipantAdapter = new GrouponDetailAdapter(this,joinGrouponBeans);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,7);
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
