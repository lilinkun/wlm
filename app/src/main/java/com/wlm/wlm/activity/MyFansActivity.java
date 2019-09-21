package com.wlm.wlm.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MyFansAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MyFansContract;
import com.wlm.wlm.entity.FansBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.MyFansPresenter;
import com.wlm.wlm.util.Eyes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/20.
 */
public class MyFansActivity extends BaseActivity implements MyFansContract {

    @BindView(R.id.rv_myfans)
    RecyclerView rv_myfans;
    @BindView(R.id.et_search)
    EditText et_search;

    private MyFansPresenter myFansPresenter = new MyFansPresenter();
    private MyFansAdapter myFansAdapter = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_fans;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        myFansPresenter.onCreate(this,this);
        myFansPresenter.getFansData("1","20", "",ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        rv_myfans.setLayoutManager(linearLayoutManager);


        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setSingleLine();
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MyFansActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (et_search.getText().toString().isEmpty()) {
                        toast("搜索栏不能为空！");
                    } else {
                        //搜索
                        doSearch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }

    }

    private void doSearch(){
        myFansPresenter.getFansData("1","20", et_search.getText().toString(),ProApplication.SESSIONID(this));
    }

    @Override
    public void getFansSuccess(ArrayList<FansBean> fansBeans, PageBean pagebean) {
        if (myFansAdapter == null) {
            myFansAdapter = new MyFansAdapter(this, fansBeans);
            rv_myfans.setAdapter(myFansAdapter);
        }else {

            if (pagebean.getPageIndex() == 1) {
                myFansAdapter.setData(fansBeans);
            }

        }
    }

    @Override
    public void getFansFail(String msg) {

    }
}
