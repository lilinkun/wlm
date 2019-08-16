package com.wlm.wlm.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.FragmentsAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.contract.RushBuyContract;
import com.wlm.wlm.fragment.HomeFragment;
import com.wlm.wlm.fragment.MeFragment;
import com.wlm.wlm.fragment.PanicBuiedFragment;
import com.wlm.wlm.fragment.PanicBuyingFragment;
import com.wlm.wlm.presenter.RushBuyPresenter;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.LzyydUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/1/2.
 */

public class RushBuyActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_rushbuy)
    ViewPager vp_rushbuy;
    @BindView(R.id.ll_rushing)
    RelativeLayout ll_rushing;
    @BindView(R.id.ll_will_rushed)
    RelativeLayout ll_will_rushed;
    @BindView(R.id.tv_rushing)
    TextView tv_rushing;
    @BindView(R.id.tv_will_rushed)
    TextView tv_will_rushed;
    @BindView(R.id.view_rushing)
    View view_rushing;
    @BindView(R.id.view_rushed)
    View view_rushed;


    private final SparseArray<BaseFragment> sparseArray = new SparseArray<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_rushbuy;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());
        getMenusFragments();
        fragmentsAdapter.setFragments(sparseArray);

        vp_rushbuy.setAdapter(fragmentsAdapter);
        vp_rushbuy.setOnPageChangeListener(this);
        vp_rushbuy.setOffscreenPageLimit(4);
        vp_rushbuy.setCurrentItem(0);

    }

    @OnClick({R.id.ll_rushing,R.id.ll_will_rushed,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_rushing:
                vp_rushbuy.setCurrentItem(0);
//                setRushType(0);
                break;

            case R.id.ll_will_rushed:
                vp_rushbuy.setCurrentItem(1);
//                setRushType(1);
                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    private void setRushType(int type){
        if (type == 0) {
            tv_rushing.setTextColor(getResources().getColor(R.color.me_goods_text_red_bg));
            tv_will_rushed.setTextColor(getResources().getColor(R.color.pop_text_bg));
            view_rushing.setBackgroundColor(getResources().getColor(R.color.me_goods_text_red_bg));
            view_rushed.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            tv_rushing.setTextColor(getResources().getColor(R.color.pop_text_bg));
            tv_will_rushed.setTextColor(getResources().getColor(R.color.me_goods_text_red_bg));
            view_rushing.setBackgroundColor(getResources().getColor(R.color.white));
            view_rushed.setBackgroundColor(getResources().getColor(R.color.me_goods_text_red_bg));
        }
    }

    private void getMenusFragments() {
        sparseArray.put(0, new PanicBuyingFragment());
        sparseArray.put(1, new PanicBuiedFragment());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0){
                setRushType(0);
            }else if(position == 1){
                setRushType(1);
            }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
