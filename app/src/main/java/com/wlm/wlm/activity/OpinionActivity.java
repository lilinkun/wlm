package com.wlm.wlm.activity;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.ChooseGrouponAdapter;
import com.wlm.wlm.adapter.OpinonAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.contract.OpinionContract;
import com.wlm.wlm.entity.ErrorBean;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.presenter.OpinionPresenter;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.GrouponType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/21.
 */

public class OpinionActivity extends BaseActivity implements OnTitleBarClickListener,OpinionContract {

    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;
    @BindView(R.id.et_opinion)
    EditText opinionEditText;
    @BindView(R.id.rl_error_type)
    RelativeLayout rl_error_type;
    @BindView(R.id.tv_choose_type)
    TextView tv_choose_type;

    OpinionPresenter opinionPresenter = new OpinionPresenter();

    private ArrayList<ErrorBean> errorBeans;
    private ErrorBean errorBean;

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
    }

    @OnClick({R.id.btn_commit,R.id.rl_error_type})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_commit:

                if (errorBean == null){
                    toast("请选择反馈类型");
                    return;
                }

                if (opinionEditText != null && opinionEditText.getText().toString().trim().isEmpty()){
                    toast("请填写意见");
                    return;
                }

                opinionPresenter.upload(errorBean.getId()+"",errorBean.getName(),opinionEditText.getText().toString(), ProApplication.SESSIONID(this));


                break;

            case R.id.rl_error_type:


//                PopupWindow popupWindow = new PopupWindow(this);
//                popupWindow = new PopupWindow(selfGoodsPopLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);

                if (errorBeans != null && errorBeans.size()>0) {

                    View errorView = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
                    RecyclerView recyclerView = errorView.findViewById(R.id.rv_groupon);

                    OpinonAdapter opinonAdapter = new OpinonAdapter(this, errorBeans);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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


                    opinonAdapter.setOnItemClick(new OpinonAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        errorBean = errorBeans.get(position);
                        tv_choose_type.setText(errorBean.getName());
                        popupWindow.dismiss();
                    }
                });
                }
                break;
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void onUploadSuccess() {
        opinionEditText.setText("");
        toast("意见反馈成功");
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
}
