package com.wlm.wlm.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.ArticleContract;
import com.wlm.wlm.contract.WebviewContract;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.ArticleDetailBean;
import com.wlm.wlm.presenter.ArticlePresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/23.
 */
public class ArticleActivity extends BaseActivity implements ArticleContract {

    @BindView(R.id.web_article)
    WebView web_article;
    @BindView(R.id.tv_headtop)
    TextView tv_headtop;

    private ArticlePresenter articlePresenter = new ArticlePresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        ArticleBean articleBean = (ArticleBean) getIntent().getBundleExtra(WlmUtil.TYPEID).getSerializable("url");
        articlePresenter.onCreate(this,this);

        articlePresenter.getArticleDetail(articleBean.getArticleId(), ProApplication.SESSIONID(this));

        web_article.getSettings().setJavaScriptEnabled(true);
        web_article.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_article.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web_article.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString) {
                return false;
            }
        });
        web_article.getSettings().setDisplayZoomControls(false);

    }


    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;

        }
    }

    @Override
    public void getDataSuccess(ArticleDetailBean articleDetailBean) {
        tv_headtop.setText(articleDetailBean.getTitle());
        if (articleDetailBean.getLink() != null && articleDetailBean.getLink().trim().length() > 0) {
            web_article.loadUrl(articleDetailBean.getLink());
        }
    }

    @Override
    public void getDataFail(String msg) {

    }
}
