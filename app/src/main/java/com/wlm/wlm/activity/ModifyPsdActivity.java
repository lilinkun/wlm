package com.wlm.wlm.activity;

import android.content.SharedPreferences;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.RegisterContract;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.RegisterPresenter;
import com.wlm.wlm.ui.CustomRegisterLayout;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;

/**
 * Created by LG on 2018/11/13.
 */

public class ModifyPsdActivity extends BaseActivity implements OnTitleBarClickListener,CustomRegisterLayout.OnRegisterListener,RegisterContract {

    @BindView(R.id.custom_title)
    CustomTitleBar customTitleBar;
    @BindView(R.id.custom_register)
    CustomRegisterLayout customRegisterLayout;

    private RegisterPresenter mRegisterPresenter = new RegisterPresenter();
    String psd = "";
    String mobile = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_psw;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);

        customRegisterLayout.setVcodeLisener(this);
        mRegisterPresenter.onCreate(this,this);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onSendVcodeSuccess() {

    }

    @Override
    public void onSendVcodeFail(String msg) {

    }

    @Override
    public void onRegisterSuccess() {
            toast("修改成功");
            SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);

            if (mobile.equals(sharedPreferences.getString("account",""))) {
                sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID(this))
                        .putString("password", psd).commit();
            }

        finish();
    }

    @Override
    public void onRegisterFail(String msg) {

    }

    @Override
    public void getVcode(String phoneNum) {
        mRegisterPresenter.SendSms(phoneNum,"");
    }


    @Override
    public void getOver(String account,String phone, String vcode) {
    }

    @Override
    public void getModify(String phone, String vcode, String psd) {
        mRegisterPresenter.modify(phone,vcode,psd,ProApplication.SESSIONID(this));
        this.mobile = phone;
        this.psd = psd;
    }
}
