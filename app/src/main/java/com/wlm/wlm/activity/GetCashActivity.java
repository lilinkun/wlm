package com.wlm.wlm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.GetCashContract;
import com.wlm.wlm.entity.UserBankBean;
import com.wlm.wlm.interf.OnPasswordInputFinish;
import com.wlm.wlm.presenter.GetCashPresenter;
import com.wlm.wlm.ui.PasswordView;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/22.
 */
public class GetCashActivity extends BaseActivity implements GetCashContract {

    @BindView(R.id.tv_wlm_coin)
    TextView tv_wlm_coin;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.et_wlm_coin)
    EditText et_wlm_coin;

    GetCashPresenter getCashPresenter = new GetCashPresenter();
    private UserBankBean userBankBean;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_getcash;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        getCashPresenter.onCreate(this, this);

        userBankBean = (UserBankBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable(WlmUtil.USERBANKBEAN);
        String wlmCoin = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.WLMCOIN);
        tv_wlm_coin.setText(wlmCoin);
        String bankStr = userBankBean.getBankNo().substring(userBankBean.getBankNo().length() - 4, userBankBean.getBankNo().length());
        tv_bank_name.setText(userBankBean.getBankNameDesc() + "(" + bankStr + ")");
    }

    @OnClick({R.id.tv_all_getcash, R.id.tv_getcash, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all_getcash:

                et_wlm_coin.setText(tv_wlm_coin.getText().toString());

                break;


            case R.id.tv_getcash:

                if (Double.valueOf(et_wlm_coin.getText().toString()) > Double.valueOf(tv_wlm_coin.getText().toString())) {
                    toast("提现金额大于余额，不能提现");
                    return;
                }

                final int coinAmount = Integer.valueOf(et_wlm_coin.getText().toString());

                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);
                LinearLayout linearLayout = view1.findViewById(R.id.ll_cash);
                linearLayout.setVisibility(View.VISIBLE);

                popupWindow = new PopupWindow(this);

                popupWindow.setContentView(view1);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                popupWindow.showAsDropDown(tv_wlm_coin);

                final PasswordView passwordView = view1.findViewById(R.id.pwd_view);

                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setGroupingUsed(false);
                final String s = numberFormat.format(coinAmount * userBankBean.getRate());
                passwordView.gettext(coinAmount - coinAmount * userBankBean.getRate(), Double.valueOf(s));
                passwordView.setOnFinishInput(new OnPasswordInputFinish() {
                    @Override
                    public void inputFinish() {
                        getCashPresenter.getCash(coinAmount + "", Double.valueOf(s) + "", passwordView.getStrPassword() + "", ProApplication.SESSIONID(GetCashActivity.this));
                    }

                    @Override
                    public void outfo() {
                        popupWindow.dismiss();
                    }

                    @Override
                    public void forgetPwd() {
                        UiHelper.launcher(GetCashActivity.this, ModifyPayActivity.class);
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                });

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void getCashSuccess() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getCashFail(String msg) {
        toast(msg);
    }
}
