package com.wlm.wlm.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.contract.ForgetPasswordContract;
import com.wlm.wlm.presenter.ForgetPasswordPresenter;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/27.
 */

public class ForgetThreePsdActivity extends BaseActivity implements ForgetPasswordContract{

    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_send_vcode)
    TextView tv_send_vcode;
    @BindView(R.id.et_vcode)
    EditText et_vcode;

    private String mobile = "";
    private String username = "";
    private String vcode = "";

    private ForgetPasswordPresenter forgetPasswordPresenter = new ForgetPasswordPresenter();
    private boolean isTrue = false;
    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_three;
    }

    @Override
    public void initEventAndData() {
        forgetPasswordPresenter.onCreate(this,this);

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        ActivityUtil.addActivity(this);
        mobile = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("mobile");
        username = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("username");
        tv_mobile.setText(PhoneFormatCheckUtils.phoneAddress(mobile));

    }

    @OnClick({R.id.tv_next,R.id.tv_send_vcode,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_next:

                if (isTrue) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile",mobile);
                    bundle.putString("username",username);
                    bundle.putString("vcode",et_vcode.getText().toString());
                    UiHelper.launcherBundle(this, ForgetFourPsdActivity.class,bundle);
                    isTrue = !isTrue;
                }else {
                    toast("请先点击获取验证码");
                }
                break;

            case R.id.tv_send_vcode:
                if (mobile != null && !mobile.isEmpty()) {
                    tv_send_vcode.setTextColor(getResources().getColor(R.color.search_edittext_bg));
                    myCountDownTimer.start();
                    forgetPasswordPresenter.getVcode(mobile);
                }else {
                    toast("获取手机失败");
                }
                break;

            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    public void getMobileSuccess(String mobile) {}

    @Override
    public void getMobileFail(String msg) {}

    @Override
    public void getVcodeSuccess(String vcode) {
        isTrue = true;
    }

    @Override
    public void getVcodeFail(String msg) {
        toast(msg);
        myCountDownTimer.cancel();

        //重新给Button设置文字
        tv_send_vcode.setText(R.string.register_send_vcoed);
        //设置可点击
        tv_send_vcode.setClickable(true);

        tv_send_vcode.setTextColor(getResources().getColor(R.color.register_vcode_bg));
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tv_send_vcode.setClickable(false);
            tv_send_vcode.setText(l/1000+"s");
            tv_send_vcode.setTextColor(getResources().getColor(R.color.line_bg));
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_send_vcode.setText(R.string.register_send_vcoed);
            //设置可点击
            tv_send_vcode.setClickable(true);

            tv_send_vcode.setTextColor(getResources().getColor(R.color.register_vcode_bg));
        }
    }
}
