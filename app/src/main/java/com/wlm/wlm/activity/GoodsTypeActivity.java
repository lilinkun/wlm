package com.wlm.wlm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.GetRecordsPagerAdapter;
import com.wlm.wlm.base.BaseActivity;
import com.wlm.wlm.base.BaseFragment;
import com.wlm.wlm.entity.TBbean;
import com.wlm.wlm.fragment.TBAllFragment;
import com.wlm.wlm.interf.OnTitleBarClickListener;
import com.wlm.wlm.ui.CustomTitleBar;
import com.wlm.wlm.ui.MotionViewPager;
import com.wlm.wlm.ui.PagerSlidingTabStrip;
import com.wlm.wlm.util.ActivityUtil;
import com.wlm.wlm.util.Eyes;
import com.wlm.wlm.util.WlmUtil;
import com.wlm.wlm.util.UiHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2018/11/14.
 */

public class GoodsTypeActivity extends BaseActivity implements OnTitleBarClickListener {

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @BindView(R.id.vp_motion)
    MotionViewPager motionViewPager;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.view_4)
    View view4;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img1_offer)
    ImageView img1_offer;
    @BindView(R.id.img2_offer)
    ImageView img2_offer;
    @BindView(R.id.tx_price)
    TextView mTvPrice;
    @BindView(R.id.tx_pople)
    TextView mTvPople;
    @BindView(R.id.tx_top)
    TextView mTvTop;
    @BindView(R.id.tx_newest)
    TextView mTvNewest;
    @BindView(R.id.titlebar)
    CustomTitleBar customTitleBar;


    private List<TBbean> listTb = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();
    private int bottomLineVisible = 0;
    private boolean isView3 = false;
    private boolean isView4 = false;

    String[] strs = {"全部","男装", "女装", "美食", "母婴", "居家", "化妆品", "文体车品", "鞋包", "数码", "内衣"};
    int[] ints = {WlmUtil.GOODS_ALL_WEAR, WlmUtil.GOODS_MAN_WEAR, WlmUtil.GOODS_WOMAN_WEAR, WlmUtil.GOODS_FOOD, WlmUtil.GOODS_MOTHER, WlmUtil.GOODS_HOUSE, WlmUtil.GOODS_MAKEUP, WlmUtil.GOODS_MOTION, WlmUtil.GOODS_SHOES, WlmUtil.GOODS_DIGITAL, WlmUtil.GOODS_UNDERWEAR};
    private GetRecordsPagerAdapter getRecordsPagerAdapter;
    private TBAllFragment tbAllFragment;
    private int position = 0;
    public static int isMall = 0;
    private HashMap<String,BaseFragment> hashMap= new HashMap<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x110) {
                Log.v("TAG","hander");
                position = msg.getData().getInt("position");
                customTitleBar.setTileName(strs[msg.getData().getInt("position")]);
                tbAllFragment =(TBAllFragment) hashMap.get(strs[position]);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_goodstype;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.setting_title_color));
        position = getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("position");
        isMall = getIntent().getBundleExtra(WlmUtil.TYPEID).getInt("isMall");

        ActivityUtil.addActivity(this);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        textViewList.add(mTvPrice);
        textViewList.add(mTvPople);
        textViewList.add(mTvTop);
        textViewList.add(mTvNewest);

        if (position == 20){
            pagerSlidingTabStrip.setVisibility(View.GONE);
        }
        pagerSlidingTabStrip.setTitles(strs, position, handler);

//        pagerSlidingTabStrip.setLineheight(15);
        if (position == 20){
            String searchName = getIntent().getBundleExtra(WlmUtil.TYPEID).getString("str");
            TBbean tBbean = new TBbean();
            tBbean.setId("1");
            tBbean.setCate_name(searchName);
            tBbean.setCate_icon("");
            listTb.add(tBbean);
            getRecordsPagerAdapter = new GetRecordsPagerAdapter(getSupportFragmentManager(),listTb,true,searchName, new GetRecordsPagerAdapter.OnPageChange() {
                @Override
                public void getChange(BaseFragment baseFragment, String str) {
//                    if (str.equals(searchName).getString("str"))) {
                        tbAllFragment = (TBAllFragment) baseFragment;
//                    }
                    hashMap.put(str, baseFragment);
                }
            });
        }else {
            for (int i = 0; i < strs.length; i++) {
                TBbean tBbean = new TBbean();
                tBbean.setId(ints[i] + "");
                tBbean.setCate_name(strs[i]);
                tBbean.setCate_icon("");
                listTb.add(tBbean);
            }
            getRecordsPagerAdapter = new GetRecordsPagerAdapter(getSupportFragmentManager(), listTb, new GetRecordsPagerAdapter.OnPageChange() {
                @Override
                public void getChange(BaseFragment baseFragment, String str) {
                    if (str.equals(strs[position])) {
                        tbAllFragment = (TBAllFragment) baseFragment;
                    }

                    hashMap.put(str, baseFragment);
                }
            });
        }
        motionViewPager.setAdapter(getRecordsPagerAdapter);
        pagerSlidingTabStrip.setViewPager(motionViewPager);
        customTitleBar.SetOnTitleClickListener(this);
        if (position == 20){
            customTitleBar.setTileName("淘宝");
        }else {
            customTitleBar.setTileName(strs[position]);
        }

    }

    @OnClick({R.id.tx_top, R.id.tx_newest, R.id.tx_pople, R.id.tx_price,R.id.iv_head_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_top:
                bottomLine(view2, mTvTop);
                break;
            case R.id.tx_newest:
                bottomLine(view4, mTvNewest);
                break;
            case R.id.tx_pople:
                bottomLine(view1, mTvPople);
                break;
            case R.id.tx_price:
                bottomLine(view3, mTvPrice);
                break;
            case R.id.iv_head_right:
                Bundle bundle = new Bundle();
                bundle.putString("search","taobao");
                UiHelper.launcherBundle(this,SearchActivity.class,bundle);
                break;
        }
    }

    private void bottomLine(View paramView, TextView paramTextView) {

        if (view3 == paramView) {
            if (isView3) {
                img1.setImageResource(R.mipmap.j_1);
                img2.setImageResource(R.mipmap.j_2_1);
                isView3 = !isView3;
                tbAllFragment.onPageChange(2);
            } else {
                img1.setImageResource(R.mipmap.j_1_1);
                img2.setImageResource(R.mipmap.j_2);
                isView3 = !isView3;
                tbAllFragment.onPageChange(3);
            }
        } else {
            img1.setImageResource(R.mipmap.j_1);
            img2.setImageResource(R.mipmap.j_2);
            isView3 = false;
        }

        if (view4 == paramView) {
            if (isView4) {
                img1_offer.setImageResource(R.mipmap.j_1);
                img2_offer.setImageResource(R.mipmap.j_2_1);
                isView4 = !isView4;
                tbAllFragment.onPageChange(4);
            } else {
                img1_offer.setImageResource(R.mipmap.j_1_1);
                img2_offer.setImageResource(R.mipmap.j_2);
                isView4 = !isView4;
                tbAllFragment.onPageChange(5);
            }
        } else {
            img1_offer.setImageResource(R.mipmap.j_1);
            img2_offer.setImageResource(R.mipmap.j_2);
            isView4 = false;
        }
        if (view1 == paramView){
            tbAllFragment.onPageChange(0);
        }else if(view2 == paramView){
            tbAllFragment.onPageChange(1);
        }

        Object localObject = viewList.iterator();
        while (((Iterator) localObject).hasNext()) {
            View localView = (View) ((Iterator) localObject).next();
            if (localView == paramView) {
                paramView.setVisibility(View.GONE);
            } else {
                localView.setVisibility(View.GONE);
            }
        }

        Object textObject = textViewList.iterator();
        while (((Iterator) textObject).hasNext()) {
            TextView itemView = (TextView) ((Iterator) textObject).next();
            if (itemView == paramTextView) {
                itemView.setTextColor(getResources().getColor(R.color.main_app_color));
            } else {
                itemView.setTextColor(getResources().getColor(R.color.goods_coupon_text));
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){

            setResult(RESULT_OK);
            finish();

            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackClick() {
        setResult(RESULT_OK);
        finish();
    }

}
