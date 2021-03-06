package com.wlm.wlm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MessageAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.MessageType;
import com.wlm.wlm.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/23.
 */
public class MessageActivity extends BaseActivity implements MessageAdapter.OnItemClickListener {

    @BindView(R.id.rv_message)
    RecyclerView rv_message;


    private MessageAdapter messageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        rv_message.setLayoutManager(linearLayoutManager);

        messageAdapter = new MessageAdapter(this);
        rv_message.setAdapter(messageAdapter);
        messageAdapter.setItemClickListener(this);
    }


    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;

        }
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("CategoryId", MessageType.values()[position].getCategoryId());
        bundle.putSerializable("title", MessageType.values()[position].getTypeName());
        UiHelper.launcherBundle(this, MessageDetitleActivity.class, bundle);
    }
}
