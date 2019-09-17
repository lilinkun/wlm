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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.BindCardActivity;
import com.wlm.wlm.activity.BrowseRecordsActivity;
import com.wlm.wlm.activity.ChooseAddressActivity;
import com.wlm.wlm.activity.CustomerServiceActivity;
import com.wlm.wlm.activity.IntegralActivity;
import com.wlm.wlm.activity.MainFragmentActivity;
import com.wlm.wlm.activity.MyGrouponActivity;
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
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.io.File;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

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


    private int result_int = 0x2121;
    private int result_person = 0x2212;
    private int result_recharge = 0x1121;
    int mAlpha = 0;
    private Drawable drawable;
    private PersonalInfoBean personalInfoBean;
    private Dialog dialog;

    private MePresenter mePresenter = new MePresenter();

    @Override
    public int getlayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(getActivity());
        translucentScrollView.init(this);
        mePresenter.onCreate(getActivity(),this);
        mePresenter.getInfo(ProApplication.SESSIONID(getActivity()));
        mePresenter.getBalance(ProApplication.SESSIONID(getActivity()));


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(WlmUtil.LOGIN, getActivity().MODE_PRIVATE);

        mTvAccount.setText(sharedPreferences.getString(WlmUtil.ACCOUNT,""));
        if (!sharedPreferences.getString(WlmUtil.HEADIMGURL,"").isEmpty()) {
            Picasso.with(getActivity()).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).error(R.mipmap.ic_adapter_error).into(roundImageView);
        }
//        BadgeView badgeView = new BadgeView(getActivity());
//        badgeView.set
    }

    public void setPoint(){
        mePresenter.getInfo(ProApplication.SESSIONID(getActivity()));
    }

    @OnClick({R.id.iv_me_setting,  R.id.ll_integral, R.id.ll_collection,R.id.rl_me_tuan, R.id.ll_coupon, R.id.ll_me_order, R.id.riv_head_img,R.id.ll_bind_card,
            R.id.ll_customer_service,R.id.ll_wait_pay,R.id.ll_wait_deliver,R.id.ll_wait_receiver,R.id.rl_vip})
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

                case R.id.ll_me_order:

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
                    UiHelper.launcherBundle(getActivity(), IntegralActivity.class, bundle4);

                    break;

                case R.id.ll_bind_card:

                    UiHelper.launcher(getActivity(), BindCardActivity.class);

                    break;

                case R.id.ll_customer_service:

                    UiHelper.launcher(getActivity(), CustomerServiceActivity.class);

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
            if (requestCode == result_int) {
                /*if (!data.getBooleanExtra("loginout", false)) {
                    if (drawable != null) {
                        final File file = new File(getActivity().getExternalCacheDir(), "crop.jpg");
                        if (file.exists()) {
                            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                            drawable = new BitmapDrawable(bm);
                            roundImageView.setImageDrawable(drawable);
                        }
                    } else {
                        if (personalInfoBean.getUser_data().getHeadPic() != null) {
                            Picasso.with(getActivity()).load(personalInfoBean.getUser_data().getHeadPic()).into(roundImageView);
                        } else {
                            roundImageView.setImageResource(R.mipmap.ic_head);
                        }
                    }
                } else {
                    getActivity().finish();
                }*/
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
                mePresenter.getInfo(ProApplication.SESSIONID(getActivity()));
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
        tv_shopping_balance.setText(balanceBean.getMoney5Balance()+"");
        tv_balance_wait_income.setText(balanceBean.getMoney4Balance()+"");
        tv_integral_balance.setText(balanceBean.getMoney2Balance()+"");

        tv_me_vip.setText(balanceBean.getUserLevelName());
        if (balanceBean.getUserLevel() <= 0){
            iv_me_vip.setVisibility(View.GONE);
        }else {
            tv_open_vip.setText("立即续费");
        }
    }

    @Override
    public void getBalanceFail(String msg) {

    }


}
