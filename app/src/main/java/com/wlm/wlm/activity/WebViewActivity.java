package com.wlm.wlm.activity;

import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.WebviewContract;
import com.wlm.wlm.presenter.WebviewPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/27.
 */

public class WebViewActivity extends BaseActivity implements WebviewContract{

    @BindView(R.id.wv_me)
    WebView webView;
    @BindView(R.id.titlebar)
    CustomTitleBar titleBar;

    private String title;
    private String ordersn;

    private WebviewPresenter webviewPresenter = new WebviewPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        webviewPresenter.onCreate(this,this);

        title = getIntent().getBundleExtra(WlmUtil.TYPEID).getString(WlmUtil.TYPE);
        if (title.equals("2")){
            titleBar.setTileName("服务协议");
        }else if (title.equals("3")){
            titleBar.setTileName("关于我们");
        }else if (title.equals("4")){
            titleBar.setTileName("VIP宝典");
        }else if(title.equals("5")){
            ordersn = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("ordersn");
            titleBar.setTileName("查看物流");
        }

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setTextZoom(100);
        webView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (title.equals("4")){
            webView.loadUrl(ProApplication.SERVIESVIP);
        }else if(title.equals("5")){
//            webView.loadUrl("http://192.168.0.147:88/wuliu.html");
            webView.loadUrl(ProApplication.LOGISTICSURL+"?OrderSn=" + ordersn);
        }else {
            if (ProApplication.REGISTERREQUIREMENTS != null && !ProApplication.REGISTERREQUIREMENTS.equals("")) {
                webView.loadUrl(ProApplication.REGISTERREQUIREMENTS);
            }
        }

//        webviewPresenter.getNewUrl(title, ProApplication.SESSIONID(this));

    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }

    protected void loadDataFromService(String paramString)
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });
        webView.loadUrl(paramString);
    }

    @Override
    public void onDataSuccess(String str) {

        loadDataFromService(str);
    }

    @Override
    public void onDataFail(String msg) {

    }
}
