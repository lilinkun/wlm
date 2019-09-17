package com.wlm.wlm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.LoginContract;
import com.wlm.wlm.db.DBManager;
import com.wlm.wlm.entity.LoginBean;
import com.wlm.wlm.entity.WxUserInfo;
import com.wlm.wlm.interf.IWxLoginListener;
import com.wlm.wlm.interf.IWxResultListener;
import com.wlm.wlm.presenter.LoginPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.wxapi.WXEntryActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/13.
 */

public class LoginActivity extends BaseActivity implements LoginContract, IWxLoginListener {

    @BindView(R.id.ll_wx_login)
    LinearLayout ll_wx_login;

    private LoginPresenter loginPresenter = new LoginPresenter();
    IWXAPI iwxapi = null;
    WxUserInfo wxUserInfo = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));


        iwxapi = WXAPIFactory.createWXAPI(this,WlmUtil.APP_ID,true);
        iwxapi.registerApp(WlmUtil.APP_ID);

        WXEntryActivity.setLoginListener(this);

        loginPresenter.onCreate(this,this);

    }

    @OnClick({R.id.ll_wx_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wx_login:
//                UiHelper.launcher(this, RegisterActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
                if (sharedPreferences.getBoolean(WlmUtil.LOGIN,false)){
                    if (!sharedPreferences.getString(WlmUtil.OPENID,"").equals("")) {
                        loginPresenter.login(sharedPreferences.getString(WlmUtil.OPENID, ""), sharedPreferences.getString(WlmUtil.UNIONID, ""), "2", ProApplication.SESSIONID(this));
                    }else {
                        final SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_微信登录";
                        iwxapi.sendReq(req);
                    }
                }else {
//                    loginPresenter.login("o-Pjfvyivj1VphDd0OZYQH5Str3A","obdLtwjcQ-yXsVEhggAJnxrNu4A4","2",ProApplication.SESSIONID(this));
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_微信登录";
                    iwxapi.sendReq(req);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 0x1212){
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onStop();
    }

    @Override
    public void onLoginSuccess(LoginBean mLoginBean) {

        LoginBean loginBean = mLoginBean;
        /*String datalife = null;
        try {
            datalife = WlmUtil.serialize(loginBean);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid",ProApplication.SESSIONID(this)).putBoolean(WlmUtil.LOGIN,true)
                .putString(WlmUtil.ACCOUNT,mLoginBean.getNickName())
                .putString(WlmUtil.HEADIMGURL,wxUserInfo.getHeadimgurl()).commit();


        UiHelper.launcher(this, MainFragmentActivity.class);
        finish();
    }


    @Override
    public void onLoginFail(String msg) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("wxinfo",wxUserInfo);
        UiHelper.launcherBundle(this, RegisterActivity.class,bundle);
    }

    @Override
    public void showPromptMessage(int str) {
        toast(str);
    }

    @Override
    public void setWxLoginSuccess(WxUserInfo wxSuccess) {
        this.wxUserInfo = wxSuccess;

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("wxinfo",wxUserInfo);
//        UiHelper.launcherBundle(this, RegisterActivity.class,bundle);
        loginPresenter.login(wxSuccess.getOpenid(),wxSuccess.getUnionid(),"2",ProApplication.SESSIONID(this));
    }

    @Override
    public void setWxLoginFail(String msg) {
        toast(msg);
    }
}
