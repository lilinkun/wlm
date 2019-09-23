package com.wlm.wlm.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.BankAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.BindCardContract;
import com.wlm.wlm.entity.BankBean;
import com.wlm.wlm.presenter.BindCardPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.PhoneFormatCheckUtils;
import com.wlm.wlm.util.WlmUtil;

import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class BindCardActivity extends BaseActivity implements BindCardContract {

    @BindView(R.id.ll_bank_name)
    LinearLayout ll_bank_name;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.iv_pop_show)
    ImageView iv_pop_show;
    @BindView(R.id.et_bank_name)
    EditText et_bank_name;
    @BindView(R.id.tv_bank_phone)
    TextView tv_bank_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_bank_card_id)
    EditText et_bank_card_id;
    @BindView(R.id.et_bank_card_num)
    EditText et_bank_card_num;
    @BindView(R.id.tv_first_bank)
    EditText tv_first_bank;
    @BindView(R.id.tv_code)
    TextView tv_code;

    ArrayList<BankBean> bankBeans = null;
    BankBean bankBean = null;
    private BindCardPresenter bindCardPresenter = new BindCardPresenter();
    private PopupWindow popupWindow;
    private boolean isTrue = false;


    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_card;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        bindCardPresenter.onCreate(this,this);


        bindCardPresenter.getBankInfo(ProApplication.SESSIONID(this));

        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN,MODE_PRIVATE);

        tv_bank_phone.setText(PhoneFormatCheckUtils.phoneAddress(sharedPreferences.getString(WlmUtil.TELEPHONE,"")));


    }

    @OnClick({R.id.ll_back,R.id.ll_bank_name,R.id.tv_bind_card,R.id.tv_code})
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.ll_back:

                finish();

                break;


            case R.id.ll_bank_name:
                if (bankBeans != null) {
                    if (popupWindow != null) {
                        popupWindow.showAsDropDown(ll_bank_name);
                        iv_pop_show.setVisibility(View.VISIBLE);
                    }
                }else {
                    isTrue = true;
                    bindCardPresenter.getBankInfo(ProApplication.SESSIONID(this));
                }

                break;

            case R.id.tv_bind_card:

                if (et_bank_name.getText().toString().trim().length() == 0){
                    toast("请填写真实姓名");
                    return;
                }

                if (et_code.getText().toString().trim().length() == 0){
                    toast("请填写验证码");
                    return;
                }

//                try {
                    if (et_bank_card_id.getText().toString().trim().length() == 15 || et_bank_card_id.getText().toString().trim().length() == 18) {
                    }else {
                        toast(R.string.input_bank_card);
                        return;
                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }


                if (et_bank_card_num.getText().toString().trim().length() == 0 ){
//                if (et_bank_card_num.getText().toString().trim().length() == 0 || !PhoneFormatCheckUtils.checkBankCard(et_bank_card_num.getText().toString())){
                    toast("请输入银行卡号码");
                    return;
                }

                if (tv_bank_name.getText().toString().trim().length() == 0){
                    toast("请填写开户银行");
                    return;
                }


                bindCardPresenter.upBankInfo(bankBean.getId()+"",tv_first_bank.getText().toString(),et_bank_name.getText().toString(),
                        et_bank_card_num.getText().toString(),et_code.getText().toString(),et_bank_card_id.getText().toString(),ProApplication.SESSIONID(this));

                break;

            case R.id.tv_code:


                bindCardPresenter.SendSms(ProApplication.SESSIONID(this));

                myCountDownTimer.start();


                break;
        }
    }

    @Override
    public void getBankInfoSuccess(ArrayList<BankBean> bankBeans) {
        this.bankBeans = bankBeans;
        if (popupWindow == null){
            initPop();
        }
        if (isTrue){
            if (popupWindow != null) {
                popupWindow.showAsDropDown(ll_bank_name);
            }
        }
    }

    @Override
    public void getBankInfoFail(String msg) {
        isTrue = false;
    }

    @Override
    public void upBankInfoSuccess(String info) {
        toast("绑定成功");
        finish();
    }

    @Override
    public void upBankInfoFail(String msg) {
        toast(msg);
    }

    @Override
    public void onSendVcodeSuccess() {
        toast("已发送验证码");
    }

    @Override
    public void onSendVcodeFail(String str) {

    }

    public void initPop(){
        View rootview = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
        RecyclerView recyclerView = rootview.findViewById(R.id.rv_groupon);

        BankAdapter bankAdapter = new BankAdapter(this, bankBeans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        });

        recyclerView.setAdapter(bankAdapter);

        popupWindow = new PopupWindow(rootview,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        bankAdapter.setOnItemClick(new BankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tv_bank_name.setText(bankBeans.get(position).getBankName());
                bankBean = bankBeans.get(position);
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(iv_pop_show.isShown()){
                    iv_pop_show.setVisibility(View.GONE);
                }
            }
        });


//                new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                tv_bank_name.setText(GrouponType.values()[position].getTypeName());
//
//                popupWindow.dismiss();
//            }
//        });
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
            tv_code.setClickable(false);
            tv_code.setText(l/1000+"s");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_code.setText(R.string.register_send_vcoed);
            //设置可点击
            tv_code.setClickable(true);

            tv_code.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
