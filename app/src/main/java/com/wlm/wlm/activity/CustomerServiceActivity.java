package com.wlm.wlm.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomerServiceActivity extends BaseActivity {

    @BindView(R.id.wv_customer)
    WebView wv_customer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));


        wv_customer.getSettings().setJavaScriptEnabled(true);
        wv_customer.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });

        wv_customer.loadUrl(ProApplication.CUSTOMERIMG);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }
}
