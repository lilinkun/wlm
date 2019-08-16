package com.wlm.wlm.contract;

import com.wlm.wlm.mvp.IView;

/**
 * Created by LG on 2018/12/27.
 */

public interface ForgetPasswordContract extends IView {
        public void getMobileSuccess(String mobile);
        public void getMobileFail(String msg);

        public void getVcodeSuccess(String vcode);
        public void getVcodeFail(String msg);
}
