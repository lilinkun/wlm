package com.wlm.wlm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.OpinionAdapter;
import com.wlm.wlm.adapter.OpinionListAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.OpinionContract;
import com.wlm.wlm.entity.ErrorBean;
import com.wlm.wlm.entity.OpinionBean;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.OpinionPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/21.
 */

public class OpinionActivity extends BaseActivity implements OnTitleBarClickListener,OpinionContract {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.rv_opinion)
    RecyclerView rv_opinion;
    @BindView(R.id.rl_opinion)
    RelativeLayout rl_opinion;

    OpinionPresenter opinionPresenter = new OpinionPresenter();
    private PopupWindow writePopupWindow;

    private ArrayList<ErrorBean> errorBeans;
    private ErrorBean errorBean;
    private OpinionListAdapter opinonAdapter;
    private EditText opinionEditText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_opinion;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        customTitleBar.SetOnTitleClickListener(this);

        opinionPresenter.onCreate(this,this);

        opinionPresenter.getErrorType(ProApplication.SESSIONID(this));

        opinionPresenter.getList("1","200","",ProApplication.SESSIONID(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_opinion.setLayoutManager(linearLayoutManager);
    }

    @OnClick({R.id.tv_opinion_write_btn})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.tv_opinion_write_btn:

                View view1 = LayoutInflater.from(this).inflate(R.layout.pop_opinion,null);
                RelativeLayout rl_opinion_pop = view1.findViewById(R.id.rl_opinion_pop);
                opinionEditText = view1.findViewById(R.id.et_opinion);
                TextView btn_commit = view1.findViewById(R.id.btn_commit);
                final TextView tv_choose_type = view1.findViewById(R.id.tv_choose_type);
                final RelativeLayout rl_error_type = view1.findViewById(R.id.rl_error_type);
                btn_commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (errorBean == null){
                            toast("请选择反馈类型");
                            return;
                        }

                        if (opinionEditText != null && opinionEditText.getText().toString().trim().isEmpty()){
                            toast("请填写意见");
                            return;
                        }

                        opinionPresenter.upload(errorBean.getId()+"",errorBean.getName(),opinionEditText.getText().toString(), ProApplication.SESSIONID(OpinionActivity.this));

                    }
                });

                rl_opinion_pop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (writePopupWindow != null && writePopupWindow.isShowing()) {
                            writePopupWindow.dismiss();
                        }
                    }
                });

                rl_error_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (errorBeans != null && errorBeans.size()>0) {

                            View errorView = LayoutInflater.from(OpinionActivity.this).inflate(R.layout.pop_layout, null);
                            RecyclerView recyclerView = errorView.findViewById(R.id.rv_groupon);

                            OpinionAdapter opinonAdapter = new OpinionAdapter(OpinionActivity.this, errorBeans);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OpinionActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            recyclerView.setLayoutManager(linearLayoutManager);

                            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                            });

                            recyclerView.setAdapter(opinonAdapter);

                            final PopupWindow popupWindow = new PopupWindow(errorView,
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

                            popupWindow.setOutsideTouchable(true);
                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                            popupWindow.setFocusable(true);
                            popupWindow.setOutsideTouchable(true);
                            popupWindow.setAnimationStyle(R.style.popwin_anim_style);


                            popupWindow.showAsDropDown(rl_error_type);


                            opinonAdapter.setOnItemClick(new OpinionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    errorBean = errorBeans.get(position);
                                    tv_choose_type.setText(errorBean.getName());
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    }
                });

                writePopupWindow = new PopupWindow(view1, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,true);
                writePopupWindow.setBackgroundDrawable(new BitmapDrawable());
                writePopupWindow.setFocusable(true);
                writePopupWindow.setOutsideTouchable(true);
                writePopupWindow.setAnimationStyle(R.style.popwin_anim_style);
                writePopupWindow.showAtLocation(rl_opinion, Gravity.CENTER | Gravity.CENTER, 0, 0);
                break;


        }
    }

    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void onUploadSuccess() {
        if (writePopupWindow != null && writePopupWindow.isShowing()){
            writePopupWindow.dismiss();
        }
        if (opinionEditText!= null && opinionEditText.getText().toString().length() > 0){
            opinionEditText.setText("");
        }
        toast("意见反馈成功");
        opinionPresenter.getList("1","200","",ProApplication.SESSIONID(this));
    }

    @Override
    public void onFail(String msg) {
        toast(msg);
    }

    @Override
    public void getTypeSuccess(ArrayList<ErrorBean> errorBeans) {
        this.errorBeans = errorBeans;
    }

    @Override
    public void getTypeFail(String msg) {

    }

    @Override
    public void getOpinionListSuccess(ArrayList<OpinionBean> opinionBeans) {
        if (opinonAdapter == null){
            opinonAdapter = new OpinionListAdapter(this,opinionBeans);
            rv_opinion.setAdapter(opinonAdapter);
        }else {
            opinonAdapter.setData(opinionBeans);
        }
    }

    @Override
    public void getOpinionListFail(String msg) {

    }
}
