package com.wlm.wlm.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.WebviewContract;
import com.wlm.wlm.presenter.WebviewPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;

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

    private WebviewPresenter webviewPresenter = new WebviewPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        webviewPresenter.onCreate(this,this);

        title = getIntent().getBundleExtra(LzyydUtil.TYPEID).getString("type");
        if (title.equals("2")){
            titleBar.setTileName("常见问题");
        }else if (title.equals("3")){
            titleBar.setTileName("关于我们");
        }

        webviewPresenter.getNewUrl(title, ProApplication.SESSIONID(this));

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
