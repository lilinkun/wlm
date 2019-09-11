package com.wlm.wlm.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.BindCardActivity;
import com.wlm.wlm.activity.BrowseRecordsActivity;
import com.wlm.wlm.activity.ChooseAddressActivity;
import com.wlm.wlm.activity.CustomerServiceActivity;
import com.wlm.wlm.activity.IntegralActivity;
import com.wlm.wlm.activity.LoginActivity;
import com.wlm.wlm.activity.MainFragmentActivity;
import com.wlm.wlm.activity.MyCouponActivity;
import com.wlm.wlm.activity.OpinionActivity;
import com.wlm.wlm.activity.OrderListActivity;
import com.wlm.wlm.activity.PersonalInfoActivity;
import com.wlm.wlm.activity.RechargeActivity;
import com.wlm.wlm.activity.RecommendActivity;
import com.wlm.wlm.activity.SettingActivity;
import com.wlm.wlm.activity.WebViewActivity;
import com.wlm.wlm.adapter.GetPagerAdapter;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MeContract;
import com.wlm.wlm.entity.PersonalInfoBean;
import com.wlm.wlm.interf.OnScrollChangedListener;
import com.wlm.wlm.presenter.MePresenter;
import com.wlm.wlm.ui.BadgeView;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.ui.TranslucentScrollView;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
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
    @BindView(R.id.tv_shopping_balance)
    TextView mShoppingBalance;
    @BindView(R.id.tv_integral_balance)
    TextView mIntegralBalance;
    @BindView(R.id.tv_lzy_account)
    TextView mTvAccount;
    @BindView(R.id.riv_head_img)
    RoundImageView roundImageView;
    @BindView(R.id.iv_circle)
    ImageView iv_circle;

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

//        BadgeView badgeView = new BadgeView(getActivity());
//        badgeView.set
    }

    public void setPoint(){
        mePresenter.getInfo(ProApplication.SESSIONID(getActivity()));
    }

    @OnClick({R.id.iv_me_setting,  R.id.ll_integral, R.id.ll_collection, R.id.ll_coupon, R.id.ll_me_order, R.id.riv_head_img,R.id.ll_bind_card,R.id.ll_customer_service})
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

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), OrderListActivity.class);
                    startActivity(intent);

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
        mShoppingBalance.setText(personalInfoBean.getBank_data().getAmount() + "元");

        double point = personalInfoBean.getBank_data().getPoint();
        BigDecimal b = new BigDecimal(point);
        point = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mIntegralBalance.setText(point + "");
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


}
