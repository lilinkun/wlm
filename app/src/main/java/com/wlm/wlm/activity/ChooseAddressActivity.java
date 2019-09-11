package com.wlm.wlm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.ChooseAddressAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.ChooseAddressContract;
import com.wlm.wlm.entity.AddressBean;
import com.wlm.wlm.entity.CollectDeleteBean;
import com.wlm.wlm.presenter.ChooseAddressPresenter;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/12/9.
 */

public class ChooseAddressActivity extends BaseActivity implements ChooseAddressContract, ChooseAddressAdapter.OnDeleteAddress, ChooseAddressAdapter.SetOnItemClickListener {

    @BindView(R.id.rv_choose_address)
    RecyclerView mChooseAddressRv;
    @BindView(R.id.ll_empty)
    LinearLayout mEmptyLayout;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    ChooseAddressPresenter chooseAddressPresenter = new ChooseAddressPresenter();
    private ChooseAddressAdapter chooseAddressAdapter;
    private int resultAddAddress = 0x1231;
    private ArrayList<AddressBean> addressBeans;
    private boolean isMe = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_address;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        chooseAddressPresenter.onCreate(this,this);

        if (getIntent() != null){
            if (getIntent().getBundleExtra(WlmUtil.TYPEID) != null){
                if (getIntent().getBundleExtra(WlmUtil.TYPEID).getString("type") != null  && !getIntent().getBundleExtra(WlmUtil.TYPEID).getString("type").isEmpty()){
                    if (getIntent().getBundleExtra(WlmUtil.TYPEID).getString("type").equals("me")) {
                        isMe = true;
                    }
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
//        ChooseAddressAdapter chooseAddressAdapter = new ChooseAddressAdapter(this,null,getLayoutInflater());

        mChooseAddressRv.setLayoutManager(linearLayoutManager);
//        mChooseAddressRv.setAdapter(chooseAddressAdapter);
        if (refreshLayout != null){
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(ChooseAddressActivity.this));
                }
            });
        }
    }

    @OnClick({R.id.tv_add_address,R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address:

                UiHelper.launcherForResult(this, AddAddressActivity.class, resultAddAddress);

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == resultAddAddress) {
                chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
            }
        }
    }

    @Override
    public void setDataSuccess(ArrayList<AddressBean> addressBeanArrayList) {
        if (refreshLayout != null && refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        this.addressBeans = addressBeanArrayList;
        if (addressBeanArrayList.size() > 0) {
            mEmptyLayout.setVisibility(View.GONE);
        }
        if (chooseAddressAdapter == null) {
            chooseAddressAdapter = new ChooseAddressAdapter(this, addressBeanArrayList, getLayoutInflater(), this);
            mChooseAddressRv.setAdapter(chooseAddressAdapter);
            chooseAddressAdapter.setOnItemclick(this);
        } else {
            chooseAddressAdapter.setData(addressBeanArrayList);
        }
    }

    @Override
    public void setDataFail(String msg) {

    }

    @Override
    public void deleteSuccess() {
        chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
    }

    @Override
    public void deleteFail(String msg) {

    }

    @Override
    public void isDefaultSuccess(String isDefault) {
        toast("设置默认成功");
        chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
    }

    @Override
    public void isDefaultFail(String msg) {

    }

    @Override
    public void delete(String userAddressId) {
        chooseAddressPresenter.deletAddress(userAddressId,ProApplication.SESSIONID(this));
    }

    @Override
    public void modify(int position) {
        AddressBean addressBean = addressBeans.get(position);
        if (addressBean.isDefault()) {
            String isDefault =  "1";
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("addressBean",addressBean);
        UiHelper.launcherForResultBundle(this,AddAddressActivity.class,resultAddAddress,bundle);

    }

    @Override
    public void isDefault(int addressId) {
        chooseAddressPresenter.isDefault(addressBeans.get(addressId).getAddressID(),ProApplication.SESSIONID(this));
    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            if (!isMe) {
                Intent intent = new Intent();
                AddressBean addressBean = addressBeans.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", addressBean);
                intent.putExtra(WlmUtil.TYPEID, bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
