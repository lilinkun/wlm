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
import com.wlm.wlm.activity.BrowseRecordsActivity;
import com.wlm.wlm.activity.ChooseAddressActivity;
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
    @BindView(R.id.tv_phone)
    TextView tv_phone;

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
        mePresenter.attachView(this);
        mePresenter.onCreate(getActivity());
        mePresenter.getInfo(ProApplication.SESSIONID(getActivity()));
    }

    public void setPoint(){
        mePresenter.getInfo(ProApplication.SESSIONID(getActivity()));
    }

    @OnClick({R.id.iv_me_setting, R.id.rl_me_recharge, R.id.rl_phone, R.id.ll_integral, R.id.ll_shopping_balance, R.id.rl_about, R.id.rl_problem, R.id.rl_me_opinion, R.id.rl_me_recommend, R.id.ll_browse_records, R.id.ll_collection, R.id.ll_coupon, R.id.ll_me_order, R.id.riv_head_img})
    public void onClick(View v) {
        if (!ButtonUtils.isFastDoubleClick(v.getId())) {
            switch (v.getId()) {
                case R.id.iv_me_setting:
                    UiHelper.launcherForResult(this, SettingActivity.class, result_int);
                    break;
                case R.id.rl_me_recharge:
                    Bundle bundle6 = new Bundle();
                    bundle6.putString("imgUrl", personalInfoBean.getUser_data().getHeadPic());
                    UiHelper.launcherForResultBundle(this, RechargeActivity.class,result_recharge, bundle6);
                    break;
                case R.id.rl_me_opinion:
                    UiHelper.launcher(getActivity(), OpinionActivity.class);
                    break;
                case R.id.rl_me_recommend:
                    UiHelper.launcher(getActivity(), RecommendActivity.class);
                    break;
                case R.id.ll_browse_records:
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 1);
                    UiHelper.launcherBundle(getActivity(), BrowseRecordsActivity.class, bundle);
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

                case R.id.rl_about:

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("type", "3");
                    UiHelper.launcherBundle(getActivity(), WebViewActivity.class, bundle2);

                    break;

                case R.id.rl_problem:

                    Bundle bundle3 = new Bundle();
                    bundle3.putString("type", "2");
                    UiHelper.launcherBundle(getActivity(), WebViewActivity.class, bundle3);
                    break;

                case R.id.ll_integral:

                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("style", 0);
                    UiHelper.launcherBundle(getActivity(), IntegralActivity.class, bundle4);

                    break;
                case R.id.ll_shopping_balance:

                    Bundle bundle5 = new Bundle();
                    bundle5.putInt("style", 1);
                    UiHelper.launcherBundle(getActivity(), IntegralActivity.class, bundle5);

                    break;

                case R.id.rl_phone:

                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_phone, null);

                    dialog = new Dialog(getActivity());

                    //设置dialog的宽高为屏幕的宽高
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(view, layoutParams);
                    dialog.show();
                    TextView tv_dialog_phone = (TextView) view.findViewById(R.id.tv_dialog_phone);
                    TextView tv_exit = (TextView) view.findViewById(R.id.tv_exit);
                    TextView tv_call = (TextView) view.findViewById(R.id.tv_call);
                    tv_dialog_phone.setText(tv_phone.getText().toString());

                    tv_exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    tv_call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            requestPermission();
                        }
                    });

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
        /**  ScrollView 滚动动态改变标题栏 */
        // 滑动的最小距离（自行定义，you happy jiu ok）
        int minHeight = 10;
        // 滑动的最大距离（自行定义，you happy jiu ok）
        int maxHeight = 100;

        // 滑动距离小于定义得最小距离
        if (scrollView.getScrollY() <= minHeight) {
            mAlpha = 0;
        }
        // 滑动距离大于定义得最大距离
        else if (scrollView.getScrollY() > maxHeight) {
            mAlpha = 255;
        }
        // 滑动距离处于最小和最大距离之间
        else {
            // （滑动距离 - 开始变化距离）：最大限制距离 = mAlpha ：255
            mAlpha = (scrollView.getScrollY() - minHeight) * 255 / (maxHeight - minHeight);
        }
        // 初始状态 标题栏/导航栏透明等
        if (mAlpha <= 0) {
            setViewBackgroundAlpha(mLlMeSetting, 0);
            mLlMeSetting.setBackgroundColor(Color.argb(0, 252, 55, 125));

        }
        //  终止状态：标题栏/导航栏 不在进行变化
        else if (mAlpha >= 255) {
            setViewBackgroundAlpha(mLlMeSetting, 255);
            mLlMeSetting.setBackgroundColor(Color.argb(255, 252, 55, 125));

        }
        // 变化中状态：标题栏/导航栏随ScrollView 的滑动而产生相应变化
        else {
            setViewBackgroundAlpha(mLlMeSetting, mAlpha);
            mLlMeSetting.setBackgroundColor(Color.argb(y, 252, 55, 125));
        }
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
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == result_int) {
                if (!data.getBooleanExtra("loginout", false)) {
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
                }
            } else if (requestCode == result_person) {
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
            }else if (requestCode == RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE) {
                callPhone();
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
        mTvAccount.setText(personalInfoBean.getUser_data().getUser_name());
        MainFragmentActivity.username = personalInfoBean.getUser_data().getUser_name();

        final File file = new File(getActivity().getExternalCacheDir(), "crop.jpg");
        if (personalInfoBean.getUser_data().getHeadPic() != null) {
            if (file.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                drawable = new BitmapDrawable(bm);
                if (!personalInfoBean.getUser_data().getHeadPic().isEmpty()) {
                    Picasso.with(getActivity()).load(personalInfoBean.getUser_data().getHeadPic() + "").placeholder(drawable).error(R.color.line_bg).into(roundImageView);
                }
            } else {
                if (!personalInfoBean.getUser_data().getHeadPic().isEmpty()) {
                    Picasso.with(getActivity()).load(personalInfoBean.getUser_data().getHeadPic() + "").error(R.mipmap.ic_head).into(roundImageView);
                }
            }
        }

    }

    @Override
    public void getInfoFail(String msg) {
        UToast.show(getActivity(), msg + "");
    }

    /**
     * 拨号方法
     */
    private void callPhone() {
        if (dialog != null) {
            dialog.cancel();
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tv_phone.getText().toString()));
        startActivity(intent);
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    // Permission Denied
                    UToast.show(getActivity(), "CALL_PHONE Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public interface RequestPermissionType {

        /**
         * 请求打电话的权限码
         */
        int REQUEST_CODE_ASK_CALL_PHONE = 100;
    }
}
