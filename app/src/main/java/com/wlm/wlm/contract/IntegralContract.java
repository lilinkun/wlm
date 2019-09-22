package com.wlm.wlm.contract;

import com.wlm.wlm.entity.AmountPriceBean;
import com.wlm.wlm.entity.BalanceBean;
import com.wlm.wlm.entity.BalanceDetailBean;
import com.wlm.wlm.entity.IntegralBean;
import com.wlm.wlm.entity.UserBankBean;
import com.wlm.wlm.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/29.
 */

public interface IntegralContract extends IView {
    public void getDataSuccess(ArrayList<BalanceDetailBean> amountPriceBean);
    public void getDataFail(String msg);


    public void getBankSuccess(UserBankBean userBankBean);
    public void getBankFail(String msg);


    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);
    public void getBalanceFail(String msg);
}
