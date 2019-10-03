package com.wlm.wlm.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.BindCardActivity;
import com.wlm.wlm.activity.BrowseRecordsActivity;
import com.wlm.wlm.activity.ChooseAddressActivity;
import com.wlm.wlm.activity.CustomerServiceActivity;
import com.wlm.wlm.activity.IntegralActivity;
import com.wlm.wlm.activity.LoginActivity;
import com.wlm.wlm.activity.MainFragmentActivity;
import com.wlm.wlm.activity.MessageActivity;
import com.wlm.wlm.activity.MessageDetitleActivity;
import com.wlm.wlm.activity.MyFansActivity;
import com.wlm.wlm.activity.MyGrouponActivity;
import com.wlm.wlm.activity.MyQrCodeActivity;
import com.wlm.wlm.activity.OrderListActivity;
import com.wlm.wlm.activity.PersonalInfoActivity;
import com.wlm.wlm.activity.VipActivity;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MeContract;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.interf.OnScrollChangedListener;
import com.wlm.wlm.presenter.MePresenter;
import com.wlm.wlm.ui.CusPtrClassicFrameLayout;
import com.wlm.wlm.ui.CustomerPtrHandler;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.MessageType;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.wxapi.WXEntryActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Created by LG on 2018/11/10.
 */

public class MeFragment extends BaseFragment implements OnScrollChangedListener, MeContract {

    @BindView(R.id.iv_me_setting)
    ImageView mIvSetting;
    @BindView(R.id.ll_me_setting)
    LinearLayout mLlMeSetting;
    @BindView(R.id.tsv_me)
    TranslucentScrollView translucentScrollView;
    @BindView(R.id.tv_wlm_account)
    TextView mTvAccount;
    @BindView(R.id.riv_head_img)
    RoundImageView roundImageView;
    @BindView(R.id.iv_circle)
    ImageView iv_circle;
    @BindView(R.id.tv_shopping_balance)
    TextView tv_shopping_balance;
    @BindView(R.id.tv_balance_wait_income)
    TextView tv_balance_wait_income;
    @BindView(R.id.tv_integral_balance)
    TextView tv_integral_balance;
    @BindView(R.id.tv_me_vip)
    TextView tv_me_vip;
    @BindView(R.id.tv_open_vip)
    TextView tv_open_vip;
    @BindView(R.id.iv_me_vip)
    ImageView iv_me_vip;
    @BindView(R.id.ll_me_vip)
    LinearLayout ll_me_vip;
    @BindView(R.id.mPtrframe)
    CusPtrClassicFrameLayout mPtrFrame;


    private int result_int = 0x2121;
    private int result_person = 0x2212;
    private int result_recharge = 0x1121;
    private int result_coin = 0x1234;
    int mAlpha = 0;
    private Drawable drawable;
    private PersonalInfoBean personalInfoBean;
    private Dialog dialog;
    private BalanceBean balanceBean;

    private MePresenter mePresenter = new MePresenter();
    IWXAPI iwxapi = null;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(getActivity());
        translucentScrollView.init(this);
        mePresenter.onCreate(getActivity(),this);
        mePresenter.getBalance(ProApplication.SESSIONID(getActivity()));

        iwxapi = WXAPIFactory.createWXAPI(getActivity(),WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        WXEntryActivity.wxType(WlmUtil.WXTYPE_SHARED);
        initPtrFrame();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN, getActivity().MODE_PRIVATE);

        mTvAccount.setText(sharedPreferences.getString(WlmUtil.ACCOUNT,""));
        if (!sharedPreferences.getString(WlmUtil.HEADIMGURL,"").isEmpty()) {
            Picasso.with(getActivity()).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).error(R.mipmap.ic_adapter_error).into(roundImageView);
        }
//        BadgeView badgeView = new BadgeView(getActivity());
//        badgeView.set
    }

    private void initPtrFrame() {
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_ptr_head,null);

        CustomerPtrHandler customerPtrHandler = new CustomerPtrHandler(getActivity());

        mPtrFrame.setHeaderView(customerPtrHandler);
        mPtrFrame.addPtrUIHandler(customerPtrHandler);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                mePresenter.getBalance(ProApplication.SESSIONID(getActivity()));

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    public void setPoint(){
        mePresenter.getBalance(ProApplication.SESSIONID(getActivity()));
    }

    @OnClick({R.id.iv_me_setting,  R.id.ll_integral, R.id.ll_collection,R.id.rl_me_tuan, R.id.ll_coupon, R.id.rl_my_all_order, R.id.riv_head_img,
            R.id.ll_bind_card,R.id.ll_customer_service,R.id.ll_wait_pay,R.id.ll_wait_deliver,R.id.ll_wait_receiver,R.id.rl_vip,R.id.ll_qrcode,
            R.id.ll_wlm_coin,R.id.rl_fans,R.id.ll_shared,R.id.ll_wlm_income_coin,R.id.rl_me_message})
    public void onClick(View v) {
        if (!ButtonUtils.isFastDoubleClick(v.getId())) {
            switch (v.getId()) {
                case R.id.iv_me_setting:
                    UiHelper.launcherForResult(this, PersonalInfoActivity.class, result_int);
                    break;

                case R.id.ll_collection:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("position", 2);
                    UiHelper.launcherBundle(getActivity(), BrowseRecordsActivity.class, bundle1);
                    break;
                case R.id.ll_coupon:
                    Bundle bundle7 = new Bundle();
                    bundle7.putString("type", "me");
                    UiHelper.launcherBundle(getActivity(), ChooseAddressActivity.class, bundle7);
                    break;

                case R.id.rl_my_all_order:

                    Bundle bundle = new Bundle();
                    bundle.putInt("position",0);
                    UiHelper.launcherBundle(getActivity(), OrderListActivity.class,bundle);

                    break;

                case R.id.riv_head_img:

                    UiHelper.launcherForResult(this, PersonalInfoActivity.class, result_person);
                    break;



                case R.id.ll_integral:

                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("style", 0);
                    bundle4.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
                    UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class,0x1987, bundle4);

                    break;

                case R.id.ll_bind_card:

                    UiHelper.launcher(getActivity(), BindCardActivity.class);

                    break;

                case R.id.ll_customer_service:

                    Bundle csBundle = new Bundle();
                    csBundle.putString("CategoryId", MessageType.values()[2].getCategoryId());
                    csBundle.putSerializable("title",MessageType.values()[2].getTypeName());
                    UiHelper.launcherBundle(getActivity(),MessageDetitleActivity.class,csBundle);

                    break;

                case R.id.rl_me_tuan:

                    UiHelper.launcher(getActivity(), MyGrouponActivity.class);

                    break;

                case R.id.ll_wait_pay:

                    Bundle bundle9 = new Bundle();
                    bundle9.putInt("position",1);
                    UiHelper.launcherBundle(getActivity(), OrderListActivity.class,bundle9);
                    break;

                case R.id.ll_wait_deliver:

                    Bundle bundle6 = new Bundle();
                    bundle6.putInt("position",2);
                    UiHelper.launcherBundle(getActivity(), OrderListActivity.class,bundle6);
                    break;
                case R.id.ll_wait_receiver:

                    Bundle bundle8 = new Bundle();
                    bundle8.putInt("position",3);
                    UiHelper.launcherBundle(getActivity(), OrderListActivity.class,bundle8);
                    break;

                case R.id.rl_vip:

                    UiHelper.launcher(getActivity(), VipActivity.class);

                    break;

                case R.id.ll_qrcode:

                    UiHelper.launcher(getActivity(), MyQrCodeActivity.class);

                    break;

                case R.id.ll_wlm_coin:

                    Bundle bundle5 = new Bundle();
                    bundle5.putInt("style", 1);
                    bundle5.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
                    UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class,result_coin, bundle5);

                    break;

                case R.id.ll_wlm_income_coin:

                    Bundle bundle10 = new Bundle();
                    bundle10.putInt("style", 2);
                    bundle10.putSerializable(WlmUtil.BALANCEBEAN,balanceBean);
                    UiHelper.launcherBundle(getActivity(), IntegralActivity.class, bundle10);


                    break;

                case R.id.rl_fans:

                    UiHelper.launcher(getActivity(), MyFansActivity.class);

                    break;

                case R.id.ll_shared:

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN,Context.MODE_PRIVATE);

                    WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
                    miniProgramObj.webpageUrl = ProApplication.SHAREDIMG; // 兼容低版本的网页链接
                    miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
                    miniProgramObj.userName = "gh_aa9e3dbf8fd0";     // 小程序原始id
                    miniProgramObj.path = "/pages/index/index?scene=" + sharedPreferences.getString(WlmUtil.USERNAME,"");
                    //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
                    WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
                    msg.title = "唯乐美商城";                    // 小程序消息title
                    msg.description = "唯乐美商城";               // 小程序消息desc

                    Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_shared_wx);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumbBmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                    msg.thumbData = baos.toByteArray();

//                msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = "";
                    req.message = msg;
                    req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
                    iwxapi.sendReq(req);

                    break;

                case R.id.rl_me_message:

                    UiHelper.launcher(getActivity(), MessageActivity.class);

                    break;

            }
        }
    }

    public static boolean checkPackage(Context paramContext, String paramString) {
        if ((paramString == null) || ("".equals(paramString))) {
            return false;
        }
        try {
            paramContext.getPackageManager().getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public void loadMore() {

    }


    /**
     * 设置View的背景透明度
     *
     * @param view
     * @param alpha
     */
    public void setViewBackgroundAlpha(View view, int alpha) {
        if (view == null) return;

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0x1987) {

                this.balanceBean = (BalanceBean) data.getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.BALANCEBEAN);
                getBalanceSuccess(balanceBean);

            } else if (requestCode == result_person) {
                if (drawable != null) {
                    final File file = new File(getActivity().getExternalCacheDir(), "crop.jpg");
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                        drawable = new BitmapDrawable(bm);
                        roundImageView.setImageDrawable(drawable);
                    }
                } else {
                    if (personalInfoBean.getUser_data().getPortrait() != null) {
                        Picasso.with(getActivity()).load(personalInfoBean.getUser_data().getPortrait()).into(roundImageView);
                    } else {
                        roundImageView.setImageResource(R.mipmap.ic_head);
                    }
                }
            }else if (requestCode == result_recharge){
            }else if (requestCode == result_coin){
                balanceBean = (BalanceBean) data.getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.BALANCEBEAN);
            }
        }
    }

    @Override
    public void getInfoSuccess(PersonalInfoBean personalInfoBean) {
        this.personalInfoBean = personalInfoBean;
//        mShoppingBalance.setText(personalInfoBean.getBank_data().getAmount() + "元");

        double point = personalInfoBean.getBank_data().getPoint();
        BigDecimal b = new BigDecimal(point);
        point = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        mIntegralBalance.setText(point + "");
        mTvAccount.setText(personalInfoBean.getUser_data().getUserName());
        MainFragmentActivity.username = personalInfoBean.getUser_data().getUserName();

        final File file = new File(getActivity().getExternalCacheDir(), "crop.jpg");
        if (personalInfoBean.getUser_data().getPortrait() != null) {
            if (file.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                drawable = new BitmapDrawable(bm);
                if (!personalInfoBean.getUser_data().getPortrait().isEmpty()) {
                    Picasso.with(getActivity()).load(personalInfoBean.getUser_data().getPortrait() + "").placeholder(drawable).error(R.color.line_bg).into(roundImageView);
                }
            } else {
                if (!personalInfoBean.getUser_data().getPortrait().isEmpty()) {
                    Picasso.with(getActivity()).load(personalInfoBean.getUser_data().getPortrait() + "").error(R.mipmap.ic_head).into(roundImageView);
                }
            }
        }

    }

    @Override
    public void getInfoFail(String msg) {
        UToast.show(getActivity(), msg + "");
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        this.balanceBean = balanceBean;
        tv_shopping_balance.setText(balanceBean.getMoney5Balance()+"");
        tv_balance_wait_income.setText(balanceBean.getMoney4Balance()+"");
        tv_integral_balance.setText(balanceBean.getMoney2Balance()+"");

        if (balanceBean.getUserLevel() <= 0){
            ll_me_vip.setVisibility(View.GONE);
        }else {
            tv_open_vip.setText("立即续费");
            tv_me_vip.setText(balanceBean.getUserLevelName());
            ll_me_vip.setVisibility(View.VISIBLE);
        }


        if (mPtrFrame != null && mPtrFrame.isRefreshing()) {
            mPtrFrame.refreshComplete();
        }
    }

    @Override
    public void getBalanceFail(String msg) {

    }


}
