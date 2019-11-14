package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.HaiWeiLoginContract;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.presenter.HaiWeiLoginPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/11/9.
 */
public class HaiWeiLoginActivity extends BaseActivity implements HaiWeiLoginContract {

    HaiWeiLoginPresenter haiWeiLoginPresenter = new HaiWeiLoginPresenter();

    @BindView(R.id.et_input_phone)
    EditText et_input_phone;
    @BindView(R.id.et_input_invitation)
    EditText et_input_invitation;
    @BindView(R.id.btn_over)
    Button btn_over;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hwlogin;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        haiWeiLoginPresenter.onCreate(this,this);

        ProApplication.isAudinLogin = true;

    }

    @OnClick({R.id.btn_over,R.id.tv_service_agreement})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_over:

                if (et_input_phone.getText().toString().isEmpty()){
                    toast("用户名不能为空");
                }

                if (et_input_invitation.getText().toString().isEmpty()){
                    toast("密码不能为空");
                }

                haiWeiLoginPresenter.login(et_input_phone.getText().toString(),et_input_invitation.getText().toString(), ProApplication.SESSIONID(this));

                break;

            case R.id.tv_service_agreement:
                Bundle bundle = new Bundle();
                bundle.putString("type","2");
                UiHelper.launcherBundle(this,WebViewActivity.class,bundle);

                break;
        }
    }

    @Override
    public void onLoginSuccess(LoginBean mLoginBean) {

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(WlmUtil.LOGIN,true)
                .putString(WlmUtil.ACCOUNT,mLoginBean.getNickName()).putString(WlmUtil.TELEPHONE,mLoginBean.getMobile())
                .putString(WlmUtil.USERNAME,mLoginBean.getUserName()).putString(WlmUtil.USERID,mLoginBean.getUserId())
                .putString(WlmUtil.VIPVALIDITY,mLoginBean.getVipValidity())
                .putString(WlmUtil.USERLEVEL,mLoginBean.getUserLevel()+"")
                .putString(WlmUtil.OPENID,mLoginBean.getOpenId())
                .putString(WlmUtil.USERLEVELNAME,mLoginBean.getUserLevelName()).commit();

        UiHelper.launcher(this, MainFragmentActivity.class);
        finish();
    }

    @Override
    public void onLoginFail(String msg) {

    }
}
