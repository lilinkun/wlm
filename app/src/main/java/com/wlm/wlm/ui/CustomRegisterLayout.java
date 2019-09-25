package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.WebViewActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;

/**
 * Created by LG on 2018/11/13.
 */

public class CustomRegisterLayout extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Resources res;
    private TextView tv_send_vcode;
    private EditText mEtInputInvitation,mEtInputVcode,mEtInputPhone;
    private LinearLayout mLlProtocol,ll_input_phone,ll_input_invitation;
    private RelativeLayout rl_vcode,rl_input_invitation;
    private Button mOverBtn;

    private boolean isOpen = false;
    private OnRegisterListener onRegisterListener;
    private Handler handler;
    private boolean isRegister = false;

    public MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    public CustomRegisterLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomRegisterLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.register_layout);

        isRegister = a.getBoolean(R.styleable.register_layout_is_register,false);
        if (!isRegister){
            mLlProtocol.setVisibility(GONE);
            mOverBtn.setText(R.string.modify_sure);
            mEtInputInvitation.setHint(R.string.modify_new_pwd);
        }else {
            mOverBtn.setText(R.string.register);
            mEtInputInvitation.setHint(R.string.register_input_pwd);
        }

    }

    public CustomRegisterLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context){
        this.context = context;
        res = context.getResources();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_register_layout,null);

        if(!isRegister){

        }

        tv_send_vcode = (TextView) view.findViewById(R.id.tv_send_vcode);
        mEtInputInvitation = (EditText) view.findViewById(R.id.et_input_invitation);
        mEtInputVcode = (EditText) view.findViewById(R.id.et_input_vcode);
        mEtInputPhone = (EditText) view.findViewById(R.id.et_input_phone);
        mLlProtocol = (LinearLayout) view.findViewById(R.id.ll_protocol);
        rl_input_invitation = (RelativeLayout) view.findViewById(R.id.rl_input_invitation);
        rl_vcode = (RelativeLayout) view.findViewById(R.id.rl_vcode);
        ll_input_phone = (LinearLayout) view.findViewById(R.id.ll_input_phone);

        mOverBtn = (Button)view.findViewById(R.id.btn_over);
        tv_send_vcode.setOnClickListener(this);
        mOverBtn.setOnClickListener(this);

        ll_input_phone.getBackground().setAlpha(33);
        rl_input_invitation.getBackground().setAlpha(33);
        rl_vcode.getBackground().setAlpha(33);

        this.addView(view , new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

    }

    public void setVcodeLisener(OnRegisterListener vcodeLisener){
        onRegisterListener = vcodeLisener;
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_send_vcode:
                if(mEtInputPhone == null || mEtInputPhone.getText().toString().isEmpty() || !PhoneFormatCheckUtils.isChinaPhoneLegal(mEtInputPhone.getText().toString())){
                    UToast.show(context,R.string.prompt_phone_number_invalid);
                    return;
                }

                onRegisterListener.getVcode(mEtInputPhone.getText().toString());

                tv_send_vcode.setTextColor(getResources().getColor(R.color.search_edittext_bg));
                myCountDownTimer.start();

                break;

            case R.id.btn_over:


                if(mEtInputPhone == null || mEtInputPhone.getText().toString().isEmpty() || !PhoneFormatCheckUtils.isChinaPhoneLegal(mEtInputPhone.getText().toString())){
                    UToast.show(context,R.string.prompt_phone_number_invalid);
                    return;
                }

                if(mEtInputVcode == null || mEtInputVcode.getText().toString().isEmpty()){
                    UToast.show(context,R.string.register_vcode_invalid);
                    return;
                }

                if(mEtInputInvitation == null || mEtInputInvitation.getText().toString().isEmpty()){
//                    UToast.show(context,R.string.register_psd_invalid);
//                    return;
                }

                if (isRegister) {
                    onRegisterListener.getOver(mEtInputPhone.getText().toString(), mEtInputVcode.getText().toString(), mEtInputInvitation.getText().toString());
                }else {
                    onRegisterListener.getModify(mEtInputPhone.getText().toString(), mEtInputVcode.getText().toString(), mEtInputInvitation.getText().toString());
                }

                break;

            case R.id.ll_protocol:

                Bundle bundle = new Bundle();
                bundle.putString("type","2");
                UiHelper.launcherBundle(context, WebViewActivity.class,bundle);

                break;
        }
    }

    public void onFinish(){
        myCountDownTimer.cancel();
        //重新给Button设置文字
        tv_send_vcode.setText(R.string.register_send_vcoed);
        //设置可点击
        tv_send_vcode.setClickable(true);

        tv_send_vcode.setTextColor(getResources().getColor(R.color.white));

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

            tv_send_vcode.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public interface OnRegisterListener{
        void getVcode(String phone);
        void getOver( String phone, String vcode, String psd);
        void getModify(String phone, String vcode, String psd);
    }
}
