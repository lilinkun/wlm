package com.wlm.wlm.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.MessageDetailAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.MessageContract;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.PageBean;
import com.wlm.wlm.presenter.MessagePresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.MessageType;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by LG on 2019/9/24.
 */
public class MessageDetitleActivity extends BaseActivity implements MessageContract, MessageDetailAdapter.OnItemClickListener {

    @BindView(R.id.rv_message_detail)
    RecyclerView rv_message_detail;
    @BindView(R.id.tv_headtop)
    TextView tv_headtop;
    @BindView(R.id.ll_customer_service_phone)
    LinearLayout ll_customer_service_phone;
    @BindView(R.id.tv_cs_phone)
    TextView tv_cs_phone;

    private MessagePresenter messagePresenter = new MessagePresenter();
    private Dialog dialog;
    private ArrayList<ArticleBean> articleBeans;

    @Override
    public int getLayoutId() {
        return R.layout.activity_messagedetail;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        messagePresenter.onCreate(this,this);
        String CategoryId = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("CategoryId");
        String title = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("title");

        /*if (title.equals(MessageType.values()[2].getTypeName())){
            ll_customer_service_phone.setVisibility(View.VISIBLE);
        }*/

        messagePresenter.getArticleList("1",WlmUtil.PAGE_COUNT,CategoryId, ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_message_detail.setLayoutManager(linearLayoutManager);

        tv_headtop.setText(title);
        tv_cs_phone.setText(ProApplication.PHONE);

    }

    @Override
    public void getArticleSuccess(ArrayList<ArticleBean> articleBeans, PageBean pageBean) {
        this.articleBeans = articleBeans;

        MessageDetailAdapter messageDetailAdapter = new MessageDetailAdapter(this,articleBeans);

        rv_message_detail.setAdapter(messageDetailAdapter);

        messageDetailAdapter.setItemClickListener(this);
    }

    @Override
    public void getArticleFail(String msg) {

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();

        bundle.putSerializable("ArticleBean",articleBeans.get(position));

        UiHelper.launcherBundle(this,ArticleActivity.class,bundle);
    }

    @OnClick({R.id.ll_back,R.id.ll_customer_service_phone})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;

            case R.id.ll_customer_service_phone:

                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_phone, null);

                dialog = new Dialog(this);

                //设置dialog的宽高为屏幕的宽高
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.setContentView(view1, layoutParams);
                dialog.show();
                TextView tv_dialog_phone = (TextView) view1.findViewById(R.id.tv_dialog_phone);
                TextView tv_exit = (TextView) view1.findViewById(R.id.tv_exit);
                TextView tv_call = (TextView) view1.findViewById(R.id.tv_call);
                tv_dialog_phone.setText(tv_cs_phone.getText().toString());

                tv_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                tv_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermission();
                    }
                });



                break;
        }
    }

    /**
     * 拨号方法
     */
    private void callPhone() {
        if (dialog != null) {
            dialog.cancel();
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tv_cs_phone.getText().toString()));
        startActivity(intent);
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    // Permission Denied
                    UToast.show(this, "CALL_PHONE Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public interface RequestPermissionType {

        /**
         * 请求打电话的权限码
         */
        int REQUEST_CODE_ASK_CALL_PHONE = 100;
    }
}
