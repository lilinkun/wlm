package com.wlm.wlm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.SmallImageAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.BuyGoodsContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.presenter.BuyGoodsPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;
import com.wlm.wlm.util.UiHelper;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wlm.wlm.fragment.MeFragment.checkPackage;

/**
 * Created by LG on 2018/11/27.
 */
public class BuyGoodsActivity extends BaseActivity implements BuyGoodsContract{

    @BindView(R.id.img_big_icon)
    ImageView mImgBigIcon;
    @BindView(R.id.img_small_icon)
    ImageView mImgSmallIcon;
    @BindView(R.id.tx_title)
    TextView mTvTitle;
    @BindView(R.id.tx_pay_money)
    TextView mTvMoney;
    @BindView(R.id.tx_ord_price)
    TextView mOrdPrice;
    @BindView(R.id.tx_quan_price)
    TextView mCouponPrice;
    @BindView(R.id.tx_tb_money)
    TextView mTbMoney;
    @BindView(R.id.tx_msg_about)
    TextView mTvMsgAbout;
    @BindView(R.id.expiry_date)
    TextView mTvExpiryDate;
    @BindView(R.id.quan_number)
    TextView mTvCoupon;
    @BindView(R.id.tv_goods_volume)
    TextView mTvGoodsVolume;
    @BindView(R.id.tx_TB)
    TextView mTvTb;
    @BindView(R.id.iv_goods_back)
    ImageView mIvGoodsBack;
    @BindView(R.id.rv_small_pic)
    RecyclerView recyclerView;
    @BindView(R.id.tx_more_picture)
    TextView moreText;
    @BindView(R.id.iv_up_down)
    ImageView mUpAndDown;
    @BindView(R.id.img_iscollect)
    ImageView mIsCollect;
    @BindView(R.id.tx_go_TB)
    TextView mTvGoTb;
    @BindView(R.id.tx_after_quan)
    TextView tx_after_quan;

    private String mLinkUrl;
    private TbMaterielBean tbDisCountBean;
    private SmallImageAdapter smallImageAdapter;
    private boolean bool = false;
    private BuyGoodsPresenter buyGoodsPresenter = new BuyGoodsPresenter();
    private boolean isCheckPoint = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_goods;
    }

    @Override
    public void initEventAndData() {

        Eyes.translucentStatusBar(this);

        tbDisCountBean = (TbMaterielBean) getIntent().getBundleExtra(LzyydUtil.TYPEID).getSerializable("discount");

        try {
            DBManager.getInstance(this).insertTbMateriel(tbDisCountBean);
        }catch (Exception e){
            e.printStackTrace();
        }
        String pictUrl = tbDisCountBean.getPictUrl();
        if (pictUrl == null ){
            pictUrl = tbDisCountBean.getPictUrl();
        }
        if (pictUrl.startsWith("//")){
            pictUrl = "https:" + pictUrl;
        }
        Picasso.with(this).load(pictUrl).into(mImgBigIcon);


        if (Integer.valueOf(tbDisCountBean.getUserType()) == 1){
            mImgSmallIcon.setImageResource(R.mipmap.ic_tm);
            mTvGoTb.setText(R.string.go_Tm);
            tx_after_quan.setText(R.string.price_tm);
        }else if (Integer.valueOf(tbDisCountBean.getUserType()) == 0){
            mImgSmallIcon.setImageResource(R.mipmap.ic_tb);
            mTvGoTb.setText(R.string.go_Tb);
            tx_after_quan.setText(R.string.price_tb);
        }

        BigDecimal zkFinalPrice = new BigDecimal(tbDisCountBean.getZkFinalPrice());
        BigDecimal couponInfo = new BigDecimal(tbDisCountBean.getCouponInfo());
        double newPrice = zkFinalPrice.subtract(couponInfo).doubleValue();
        String price = "";
        if(Math.round(newPrice) - newPrice == 0){
            price = String.valueOf((long) newPrice);
        }else {
            price = String.valueOf(newPrice);
        }

        mTvTitle.setText(tbDisCountBean.getTitle());
        mLinkUrl = tbDisCountBean.getItemUrl();
        mTvMoney.setText(price + "");
        mOrdPrice.setText(tbDisCountBean.getZkFinalPrice()+"");
        mCouponPrice.setText(price + "");
        mTbMoney.setText(tbDisCountBean.getZkFinalPrice()+"");
        mTvMsgAbout.setText(tbDisCountBean.getShortTitle());
//        Date date = new Date(Long.valueOf(tbDisCountBean.getCouponEndTime()));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mTvExpiryDate.setText("有效期至：" + tbDisCountBean.getCouponEndTime());
        mTvCoupon.setText(tbDisCountBean.getCouponInfo() + "元抵扣券");
        mTvGoodsVolume.setText(tbDisCountBean.getVolume() + "人已购买");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        buyGoodsPresenter.attachView(this);
        buyGoodsPresenter.onCreate(this);

    }

    @OnClick({R.id.rel_go_TB,R.id.rel_tikey,R.id.tx_TB,R.id.iv_goods_back,R.id.ll_more_picture,R.id.img_iscollect})
    public void onClick(View view){
        bool = checkPackage(this, "com.taobao.taobao");
        switch (view.getId()) {
            case R.id.rel_go_TB:

                if (bool) {
                    if (mLinkUrl.startsWith("//")) {
                        mLinkUrl = "https" + mLinkUrl;
                    }
                    goLink(mLinkUrl);
                    return;
                }

                break;

            case R.id.rel_tikey:

            case R.id.tx_TB:

//                buyGoodsPresenter.isExChange(tbDisCountBean.getCouponInfo(),ProApplication.SESSIONID(BuyGoodsActivity.this),tbDisCountBean.getNumIid(),tbDisCountBean.getCouponId());
                exReChangeSuccess("1001");
                break;

            case R.id.iv_goods_back:

                finish();

                break;

            case R.id.ll_more_picture:

                if (smallImageAdapter == null) {
                    smallImageAdapter = new SmallImageAdapter(this, (ArrayList) (tbDisCountBean.getSmallImages()));
                    recyclerView.setAdapter(smallImageAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    mUpAndDown.setImageResource(R.mipmap.ic_pic_detail_select);
                }else {
                    if (recyclerView != null && recyclerView.isShown()){
                        recyclerView.setVisibility(View.GONE);
                        mUpAndDown.setImageResource(R.mipmap.ic_pic_detail_unselect);

                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        mUpAndDown.setImageResource(R.mipmap.ic_pic_detail_select);
                    }
                }

                break;

            case R.id.img_iscollect:

                buyGoodsPresenter.collectGoods(tbDisCountBean.getNumIid(), ProApplication.SESSIONID(this));

                break;
        }
    }

    private void goLink(String paramString)
    {
        /*if (paramString.startsWith("//")){
            paramString = "https:"+paramString;
        }*/

       /* String u = tbDisCountBean.getCouponShareUrl().substring(tbDisCountBean.getCouponShareUrl().indexOf("app_pvid"));

        String url = "https://item.taobao.com/item.htm?id=" + tbDisCountBean.getNumIid() + "&ali_trackid=mm_253830059_229700115_64362000477&"+u;

        Intent localIntent = new Intent();
        localIntent.setAction("Android.intent.action.VIEW");
        localIntent.setData(Uri.parse(url));
        localIntent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
        startActivity(localIntent);*/

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("taobao:" + tbDisCountBean.getUrl() + "&unid=" + MainFragmentActivity.username));
        startActivity(intent);
    }

    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }


    @Override
    public void collectSuccess(CollectDeleteBean collectDeleteBean) {
        if (collectDeleteBean.getStatus() == 0){
            if (bool){
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("taobao:" + tbDisCountBean.getCouponShareUrl() + "&unid=0x151"));
                startActivity(intent);
            }else {

                Bundle bundle = new Bundle();
                bundle.putString("link",tbDisCountBean.getCouponShareUrl());
                UiHelper.launcherBundle(BuyGoodsActivity.this,CouponLinkActivity.class,bundle);
            }
        }else {
            toast(collectDeleteBean.getMessage());
        }
    }

    @Override
    public void collectFail(String msg) {
        toast(msg);
    }

    @Override
    public void exReChangeSuccess(String msg) {
        if (msg.equals("1000")){//没有领过
            isCheckPoint = false;
        }else if(msg.equals("1001")){//已经领过
            isCheckPoint = true;
        }

        if (isCheckPoint){
            if (bool){
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("taobao:" + tbDisCountBean.getCouponShareUrl() + "&unid=" + MainFragmentActivity.username));
                startActivity(intent);
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("link",tbDisCountBean.getCouponShareUrl());
                UiHelper.launcherBundle(BuyGoodsActivity.this,CouponLinkActivity.class,bundle);
            }
        }else {
            new AlertDialog.Builder(this).setMessage("您需要用" + tbDisCountBean.getCouponInfo() + "积分兑换").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    buyGoodsPresenter.setData(tbDisCountBean.getCouponInfo(), ProApplication.SESSIONID(BuyGoodsActivity.this), tbDisCountBean.getNumIid(), tbDisCountBean.getCouponId());

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    public void exReChangeFail(String msg) {
        toast(msg);
    }
}
