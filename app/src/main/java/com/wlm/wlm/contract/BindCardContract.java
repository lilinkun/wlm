package com.wlm.wlm.contract;

import com.wlm.wlm.entity.BankBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/17.
 */
public interface BindCardContract extends IView {

    public void getBankInfoSuccess(ArrayList<BankBean> bankBeans);
    public void getBankInfoFail(String msg);

    public void upBankInfoSuccess(String info);
    public void upBankInfoFail(String msg);


    public void onSendVcodeSuccess();
    public void onSendVcodeFail(String str);
}
