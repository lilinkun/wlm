package com.wlm.wlm.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/23.
 */
public class ArticleActivity extends BaseActivity  {

    @BindView(R.id.web_article)
    WebView web_article;
    @BindView(R.id.tv_headtop)
    TextView tv_headtop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        ArticleBean articleBean = (ArticleBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable("ArticleBean");

        web_article.getSettings().setJavaScriptEnabled(true);
        web_article.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_article.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web_article.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                return false;
            }
        });
        web_article.getSettings().setDisplayZoomControls(false);
        if (articleBean.getLink() != null && articleBean.getLink().trim().length() > 0) {
            web_article.loadUrl(articleBean.getLink());
        }

        tv_headtop.setText(articleBean.getTitle());
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
