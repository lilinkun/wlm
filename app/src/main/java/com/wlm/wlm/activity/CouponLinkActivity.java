package com.wlm.wlm.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;

/**
 * Created by LG on 2018/11/28.
 */

public class CouponLinkActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_link;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));

        this.webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webview.getSettings().setLoadWithOverviewMode(true);

        String mUrlLink = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("link");

        if (mUrlLink.startsWith("//")){
            mUrlLink = "https:" + mUrlLink;
        }
        loadDataFromService(mUrlLink);
    }

    protected void loadDataFromService(String paramString)
    {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });
        webview.loadUrl(paramString);
    }

}
