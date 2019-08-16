package com.wlm.wlm.entity;

/**
 * Created by LG on 2018/12/3.
 */
public class PersonalInfoBean {
    private LoginBean user_data;
    private CountBean bank_data;

    public LoginBean getUser_data() {
        return user_data;
    }

    public void setUser_data(LoginBean user_data) {
        this.user_data = user_data;
    }

    public CountBean getBank_data() {
        return bank_data;
    }

    public void setBank_data(CountBean bank_data) {
        this.bank_data = bank_data;
    }
}
