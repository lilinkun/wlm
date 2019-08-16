package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.UToast;

/**
 * Created by LG on 2018/11/13.
 */

public class CustomRegisterLayout extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Resources res;
    private TextView tv_send_vcode;
    private EditText mEtInputPhone,mEtInputPwd,mEtInputVcode,mEtInputAccount,mEtInputName;
    private ImageView mIvInputPsd;
    private LinearLayout mLlProtocol;
    private Button mOverBtn;
    private View linePhone,lineVcode,linePsd,lineAccount,lineName;

    private boolean isOpen = false;
    private OnRegisterListener onRegisterListener;
    private Handler handler;
    private boolean isRegister = false;

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

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
            mEtInputPwd.setHint(R.string.modify_new_pwd);
        }else {
            mOverBtn.setText(R.string.register);
            mEtInputPwd.setHint(R.string.register_input_pwd);
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
        mEtInputPhone = (EditText) view.findViewById(R.id.et_input_phone);
        mEtInputPwd = (EditText) view.findViewById(R.id.et_input_pwd);
        mEtInputVcode = (EditText) view.findViewById(R.id.et_input_vcode);
        mEtInputAccount = (EditText) view.findViewById(R.id.et_input_account);
        mEtInputName = (EditText) view.findViewById(R.id.et_input_name);
        mLlProtocol = (LinearLayout) view.findViewById(R.id.ll_protocol);
        mOverBtn = (Button)view.findViewById(R.id.btn_over);
        linePhone = (View) view.findViewById(R.id.line_phone);
        lineVcode = (View) view.findViewById(R.id.line_vcode);
        linePsd = (View) view.findViewById(R.id.line_psd);
        lineName = (View) view.findViewById(R.id.line_name);
        lineAccount = (View)view.findViewById(R.id.line_account);
        mIvInputPsd = (ImageView) view.findViewById(R.id.ic_input_psd);
        mIvInputPsd.setOnClickListener(this);
        tv_send_vcode.setOnClickListener(this);
        mOverBtn.setOnClickListener(this);
        mEtInputPhone.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linePhone.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                lineVcode.setBackgroundColor(getResources().getColor(R.color.line_bg));
                linePsd.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineAccount.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineName.setBackgroundColor(getResources().getColor(R.color.line_bg));
                return false;
            }
        });
        mEtInputPwd.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linePhone.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineVcode.setBackgroundColor(getResources().getColor(R.color.line_bg));
                linePsd.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                lineAccount.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineName.setBackgroundColor(getResources().getColor(R.color.line_bg));
                return false;
            }
        });
        mEtInputVcode.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linePhone.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineVcode.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                linePsd.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineAccount.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineName.setBackgroundColor(getResources().getColor(R.color.line_bg));
                return false;
            }
        });
        mEtInputAccount.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linePhone.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineVcode.setBackgroundColor(getResources().getColor(R.color.line_bg));
                linePsd.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineAccount.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                lineName.setBackgroundColor(getResources().getColor(R.color.line_bg));
                return false;
            }
        });
        mEtInputName.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linePhone.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineVcode.setBackgroundColor(getResources().getColor(R.color.line_bg));
                linePsd.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineAccount.setBackgroundColor(getResources().getColor(R.color.line_bg));
                lineName.setBackgroundColor(getResources().getColor(R.color.register_vcode_bg));
                return false;
            }
        });

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

            case R.id.ic_input_psd:

                if (!isOpen){
                    mIvInputPsd.setImageResource(R.mipmap.ic_open_psd);
                    mEtInputPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtInputPwd.setSelection(mEtInputPwd.getText().toString().length());
                    isOpen = !isOpen;
                }else{
                    mIvInputPsd.setImageResource(R.mipmap.ic_close_psd);
                    mEtInputPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtInputPwd.setSelection(mEtInputPwd.getText().toString().length());
                    isOpen = !isOpen;
                }
                break;

            case R.id.btn_over:

                if (mEtInputAccount == null || mEtInputAccount.getText().toString().isEmpty()){
                    UToast.show(context,R.string.prompt_account_invalid);
                    return;
                }

                if (mEtInputAccount == null || mEtInputAccount.getText().toString().length() < 6){
                    UToast.show(context,R.string.prompt_account_length_invalid);
                    return;
                }

                if (mEtInputName.getText().toString().isEmpty()){
                    UToast.show(context,R.string.prompt_name_invalid);
                    return;
                }

                if(mEtInputPhone == null || mEtInputPhone.getText().toString().isEmpty() || !PhoneFormatCheckUtils.isChinaPhoneLegal(mEtInputPhone.getText().toString())){
                    UToast.show(context,R.string.prompt_phone_number_invalid);
                    return;
                }

                if(mEtInputVcode == null || mEtInputVcode.getText().toString().isEmpty()){
                    UToast.show(context,R.string.register_vcode_invalid);
                    return;
                }

                if(mEtInputPwd == null || mEtInputPwd.getText().toString().isEmpty()){
                    UToast.show(context,R.string.register_psd_invalid);
                    return;
                }

                if (mEtInputPwd.getText().toString().length() < 6){
                    UToast.show(context,R.string.register_psd_length_invalid);
                    return;
                }

                if (isRegister) {
                    onRegisterListener.getOver(mEtInputAccount.getText().toString(),mEtInputPhone.getText().toString(), mEtInputVcode.getText().toString(), mEtInputPwd.getText().toString(),mEtInputName.getText().toString());
                }else {
                    onRegisterListener.getModify(mEtInputPhone.getText().toString(), mEtInputVcode.getText().toString(), mEtInputPwd.getText().toString());
                }

                break;
        }
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

    public interface OnRegisterListener{
        void getVcode(String phone);
        void getOver(String account, String phone, String vcode, String psd, String UserName);
        void getModify(String phone, String vcode, String psd);
    }
}
