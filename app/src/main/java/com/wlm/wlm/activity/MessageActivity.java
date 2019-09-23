package com.wlm.wlm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MessageAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MessageContract;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.MessagePresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/23.
 */
public class MessageActivity extends BaseActivity implements MessageContract, MessageAdapter.OnItemClickListener {

    @BindView(R.id.rv_message)
    RecyclerView rv_message;


    private MessagePresenter messagePresenter = new MessagePresenter();
    private MessageAdapter messageAdapter;
    private ArrayList<ArticleBean> articleBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        messagePresenter.onCreate(this,this);
        messagePresenter.getArticleList("1", WlmUtil.PAGE_COUNT, ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        rv_message.setLayoutManager(linearLayoutManager);

    }


    @Override
    public void getArticleSuccess(ArrayList<ArticleBean> articleBeans, PageBean pageBean) {
        this.articleBeans = articleBeans;
        if (messageAdapter == null){
            messageAdapter = new MessageAdapter(this,articleBeans);
            rv_message.setAdapter(messageAdapter);
            messageAdapter.setItemClickListener(this);
        }
    }

    @Override
    public void getArticleFail(String msg) {

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
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("url",articleBeans.get(position));
        UiHelper.launcherBundle(this,ArticleActivity.class,bundle);
    }
}
