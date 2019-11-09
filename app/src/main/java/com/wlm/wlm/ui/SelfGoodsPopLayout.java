package com.wlm.wlm.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.labels.LabelsView;
import com.google.gson.Gson;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsChooseBean;
import com.wlm.wlm.entity.GoodsDetailBean;
import com.wlm.wlm.entity.GoodsDetailInfoBean;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.wlm.wlm.entity.SelfOrderInfoBean;
import com.wlm.wlm.ui.Flow.TagAdapter;
import com.wlm.wlm.ui.Flow.TagFlowLayout;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UtilTool;
import com.wlm.wlm.util.UtilsLog;
import com.squareup.picasso.Picasso;
import com.wlm.wlm.util.WlmUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by LG on 2018/12/9.
 */

public class SelfGoodsPopLayout extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private LabelsView flowLayout;
    private LabelsView labael_size;
    private ImageView iv_goods_small_pic;
    private ImageView iv_goods_plus,iv_goods_subtraction,iv_popup_exit;
    private TextView tv_goods_count;
    private TextView tv_buy_goods;
    private TextView tv_goods_pop_price;
    private TextView tv_add_cart;
    private TextView tv_choose;
    private TextView tv_choose_size;
    private TextView tv_size,tv_money_icon;
    private TextView tv_spec1, tv_spec2;
    private int num = 1;
    private OnAddCart onAddCart;
    private GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean;
    private GoodsChooseBean goodsChooseBean;
    private LinearLayout mSpecLayout1,mSpecLayout2;
    private int mtype = 0;
    private HashMap<String,ArrayList> hashMap = new HashMap<>();
    private int spec1 = -1;
    private int spec2 = -1;
    private int position = 0;
    private TextView tv_stock;
    private int count = 0;
    private int Stock = 0;

    public SelfGoodsPopLayout(Context context) {
        super(context);
        init(context);
    }

    public SelfGoodsPopLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelfGoodsPopLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void init(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_goods_format,null);

        flowLayout = (LabelsView) view.findViewById(R.id.fl_goods_detail);

        labael_size= (LabelsView) view.findViewById(R.id.fl_goods_size);

        mSpecLayout1 = (LinearLayout) view.findViewById(R.id.ll_spec1);
        mSpecLayout2 = (LinearLayout) view.findViewById(R.id.ll_spec2);

        tv_spec1 = (TextView) view.findViewById(R.id.tv_spec1);
        tv_spec2 = (TextView) view.findViewById(R.id.tv_spec2);
        tv_money_icon = (TextView) view.findViewById(R.id.tv_money_icon);

        iv_goods_small_pic = (ImageView) view.findViewById(R.id.iv_goods_small_pic);

        iv_popup_exit = (ImageView) view.findViewById(R.id.iv_popup_exit);

        tv_goods_pop_price = (TextView) view.findViewById(R.id.tv_goods_pop_price);

        tv_add_cart = (TextView)view.findViewById(R.id.tv_add_cart);

        tv_choose = (TextView) view.findViewById(R.id.tv_choose);
        tv_choose_size = (TextView) view.findViewById(R.id.tv_choose_size);
        tv_size = (TextView) view.findViewById(R.id.tv_size);

        iv_goods_plus = (ImageView) view.findViewById(R.id.iv_goods_plus);
        iv_goods_subtraction = (ImageView) view.findViewById(R.id.iv_goods_subtraction);

        tv_goods_count= (TextView) view.findViewById(R.id.tv_goods_count);
        tv_buy_goods = (TextView) view.findViewById(R.id.tv_buy_goods);

        tv_stock = (TextView)view.findViewById(R.id.tv_stock);

        iv_goods_plus.setOnClickListener(this);
        iv_goods_subtraction.setOnClickListener(this);
        tv_add_cart.setOnClickListener(this);
        iv_popup_exit.setOnClickListener(this);
        tv_buy_goods.setOnClickListener(this);
        tv_goods_count.setOnClickListener(this);

        this.addView(view);
    }

    public void setPosition(int position){
        if (position == 1){
//            tv_add_cart.setBackgroundColor(getResources().getColor(R.color.setting_title_color));
            tv_add_cart.setText(R.string.modify_sure);
            tv_add_cart.setVisibility(VISIBLE);
            tv_buy_goods.setVisibility(GONE);
        }else if (position == 2){
            tv_buy_goods.setText(R.string.modify_sure);
            tv_buy_goods.setVisibility(VISIBLE);
            tv_add_cart.setVisibility(GONE);
        } else if (position == 3){
            tv_buy_goods.setVisibility(VISIBLE);
            tv_add_cart.setVisibility(VISIBLE);
//            tv_add_cart.setBackgroundColor(getResources().getColor(R.color.goods_car_bg));
            tv_buy_goods.setText(R.string.goods_buy_now);
            tv_add_cart.setText(R.string.goods_add_shoppingcar);
        }
    }

    public void setListener(OnAddCart listener){
       this.onAddCart = listener;
    }


    public void setData(final GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean, final int type){
        this.goodsDetailBean = goodsDetailBean;
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 15, 10, 10);
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }


        changeBgColor(type);
        Stock = Integer.valueOf(goodsDetailBean.getGoodsNumber());

        Picasso.with(context).load(ProApplication.HEADIMG + goodsDetailBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(iv_goods_small_pic);

        final ArrayList<GoodsChooseBean> goodsChooseBeans = new ArrayList<>();

        if (type == WlmUtil.GOODSTYPE_INTEGRAL){
            tv_goods_pop_price.setText(goodsDetailBean.getIntegral()+"积分 + " + goodsDetailBean.getPrice() +"元");
        }else {
            tv_goods_pop_price.setText("" + goodsDetailBean.getPrice());
        }

        tv_stock.setText(""+goodsDetailBean.getGoodsNumber());


        if (goodsDetailBean != null && goodsDetailBean.getAttr() != null && goodsDetailBean.getAttr().size() != 0) {
            Gson gson = new Gson();
            for (int i = 0; i < goodsDetailBean.getAttr().size(); i++) {
                String str = gson.toJson(goodsDetailBean.getAttr().get(i));
                GoodsChooseBean goodsChooseBean = gson.fromJson(str, GoodsChooseBean.class);
                goodsChooseBeans.add(goodsChooseBean);
            }

            if (type == WlmUtil.GOODSTYPE_INTEGRAL){
                tv_goods_pop_price.setText(goodsDetailBean.getIntegral()+"积分 + " + goodsDetailBean.getPrice() +"元");
            }else {
                tv_goods_pop_price.setText("" + goodsDetailBean.getPrice());
            }

            final ArrayList<GoodsChooseBean> goodsChooseBeans1 = new ArrayList<>();
            ArrayList<String> strings1 = new ArrayList<>();
            for (GoodsChooseBean goodsChooseBean : goodsChooseBeans) {
                HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
                if (!strings1.contains(goodsChooseBean.getSpec1())) {
                    strings1.add(goodsChooseBean.getSpec1());
                    goodsChooseBeans1.add(goodsChooseBean);
                }
            }


            final ArrayList<GoodsChooseBean> goodsChooseBeans2 = new ArrayList<>();
            ArrayList<String> strings2 = new ArrayList<>();
            for (GoodsChooseBean goodsChooseBean : goodsChooseBeans) {
                if (!strings2.contains(goodsChooseBean.getSpec2())) {
                    strings2.add(goodsChooseBean.getSpec2());
                    goodsChooseBeans2.add(goodsChooseBean);
                }
            }

            final ArrayList<String> firstStrings = new ArrayList<>();
            if (goodsDetailBean.getQty() == 1) {
                tv_choose.setText(goodsDetailBean.getGoodsSpec1());
                for (GoodsChooseBean goodsChooseBean : goodsChooseBeans1) {
                    if (goodsChooseBean.getAmount() > 0) {
                        firstStrings.add(goodsChooseBean.getSpec1() + "");
                    }
                }
            } else if (goodsDetailBean.getQty() == 2) {
                tv_choose.setText(goodsDetailBean.getGoodsSpec1());
                tv_choose_size.setText(goodsDetailBean.getGoodsSpec2());
                for (GoodsChooseBean goodsChooseBean : goodsChooseBeans1) {
                    ArrayList<String> colorStr = new ArrayList<>();
                    hashMap.put(goodsChooseBean.getSpec1(), colorStr);
                    for (GoodsChooseBean goodsChooseBean1 : goodsChooseBeans) {
                        if (goodsChooseBean1.getSpec1().equals(goodsChooseBean.getSpec1())) {
                            if (goodsChooseBean1.getAmount() > 0) {
                                colorStr.add(goodsChooseBean1.getSpec2());
                            }
                        }
                    }
                    hashMap.put(goodsChooseBean.getSpec1(), colorStr);
                }

                for (GoodsChooseBean goodsChooseBean : goodsChooseBeans2) {
                    ArrayList<String> colorStr = new ArrayList<>();
                    hashMap.put(goodsChooseBean.getSpec2(), colorStr);
                    for (GoodsChooseBean goodsChooseBean2 : goodsChooseBeans) {
                        if (goodsChooseBean2.getSpec2().equals(goodsChooseBean.getSpec2())) {
                            if (goodsChooseBean2.getAmount() > 0) {
                                colorStr.add(goodsChooseBean2.getSpec1());
                            }
                        }
                    }
                    hashMap.put(goodsChooseBean.getSpec2(), colorStr);
                }
            } else if (goodsDetailBean.getQty() == 0) {
                tv_choose.setText("数量");
            }

            if (goodsDetailBean.getQty() == 1) {
                mtype = 1;
                mSpecLayout1.setVisibility(VISIBLE);
                tv_spec1.setText(goodsDetailBean.getGoodsSpec1());
                flowLayout.setSelectType(LabelsView.SelectType.SINGLE);
                flowLayout.setLabels(goodsChooseBeans1, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                    @Override
                    public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {
                        return data.getSpec1();
                    }
                }, firstStrings);

                if (Integer.valueOf(goodsDetailBean.getGoodsNumber()) != 0) {

                    flowLayout.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
                        @Override
                        public void onLabelClick(TextView label, Object data, int position) {if (type == WlmUtil.GOODSTYPE_INTEGRAL){
                            tv_goods_pop_price.setText(((GoodsChooseBean) data).getIntegral()+"积分 + " + ((GoodsChooseBean) data).getPrice() +"元");
                        }else {
                            tv_goods_pop_price.setText("" + ((GoodsChooseBean) data).getPrice());
                        }
                            goodsChooseBean = (GoodsChooseBean) data;
                            tv_size.setText("已选 ");
                            tv_choose.setText(goodsChooseBean.getSpec1());
                            spec1 = position;
                            if (Integer.valueOf(goodsDetailBean.getQty()) == 2) {
                                ArrayList<String> specArray = hashMap.get(goodsChooseBean.getSpec1());

                            } else if (Integer.valueOf(goodsDetailBean.getQty()) == 1) {
                                goodsChooseBean = (GoodsChooseBean) data;
                                tv_stock.setText(goodsChooseBean.getAmount() + "");
                            }

                        }
                    });
                }
            } else {

                if (!goodsDetailBean.getGoodsSpec1().isEmpty()) {
                    mtype = 1;
                    mSpecLayout1.setVisibility(VISIBLE);
                    tv_spec1.setText(goodsDetailBean.getGoodsSpec1());
                    flowLayout.setSelectType(LabelsView.SelectType.SINGLE);
                    flowLayout.setLabels(goodsChooseBeans1, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {
                            return data.getSpec1();
                        }
                    });

                    if (Integer.valueOf(goodsDetailBean.getGoodsNumber()) != 0) {

                        flowLayout.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
                            @Override
                            public void onLabelClick(TextView label, Object data, int position) {
                                if (type == WlmUtil.GOODSTYPE_INTEGRAL){
                                    tv_goods_pop_price.setText(((GoodsChooseBean) data).getIntegral()+"积分 + " + ((GoodsChooseBean) data).getPrice() +"元");
                                }else {
                                    tv_goods_pop_price.setText("" + ((GoodsChooseBean) data).getPrice());
                                }
                                goodsChooseBean = (GoodsChooseBean) data;
                                tv_size.setText("已选 ");

                                if (spec1 == position) {
                                    if (tv_choose_size.getText().toString().equals("")){
                                        tv_size.setText("");
                                    }
                                    tv_choose.setText("");
                                    spec1 = -1;
                                }else {
                                    spec1 = position;
                                    tv_choose.setText(goodsChooseBean.getSpec1());
                                }

                                if (Integer.valueOf(goodsDetailBean.getQty()) == 2) {
                                    ArrayList<String> specArray = hashMap.get(goodsChooseBean.getSpec1());
                                    if (spec1 == -1) {
                                        labael_size.setLabels(goodsChooseBeans2, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                                            @Override
                                            public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {

                                                if (spec2 != -1) {
                                                    labael_size.setSelects(spec2);
                                                }

                                                for (GoodsChooseBean goodsChooseBean : goodsChooseBeans) {
                                                    if (tv_choose.getText().toString().equals(goodsChooseBean.getSpec1()) && tv_choose_size.getText().toString().equals("  " + goodsChooseBean.getSpec2())) {
                                                        SelfGoodsPopLayout.this.goodsChooseBean = goodsChooseBean;
                                                    }
                                                }

                                                tv_stock.setText(goodsChooseBean.getAmount() + "");
                                                return data.getSpec2();
                                            }
                                        });
                                    }else {
                                        labael_size.setLabels(goodsChooseBeans2, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                                            @Override
                                            public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {

                                                if (spec2 != -1) {
                                                    labael_size.setSelects(spec2);
                                                }

                                                for (GoodsChooseBean goodsChooseBean : goodsChooseBeans) {
                                                    if (tv_choose.getText().toString().equals(goodsChooseBean.getSpec1()) && tv_choose_size.getText().toString().equals("  " + goodsChooseBean.getSpec2())) {
                                                        SelfGoodsPopLayout.this.goodsChooseBean = goodsChooseBean;
                                                    }
                                                }

                                                tv_stock.setText(goodsChooseBean.getAmount() + "");
                                                return data.getSpec2();
                                            }
                                        }, specArray);
                                    }
                                } else if (Integer.valueOf(goodsDetailBean.getQty()) == 1) {
                                    goodsChooseBean = (GoodsChooseBean) data;
                                    tv_stock.setText(goodsChooseBean.getAmount() + "");
                                }

                            }
                        });
                    }
                }

                if (!goodsDetailBean.getGoodsSpec2().isEmpty()) {
                    mtype = 2;
                    tv_spec2.setText(goodsDetailBean.getGoodsSpec2());
                    mSpecLayout2.setVisibility(VISIBLE);

                    labael_size.setSelectType(LabelsView.SelectType.SINGLE);
                    labael_size.setLabels(goodsChooseBeans2, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {
                            return data.getSpec2();
                        }
                    });
                    labael_size.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
                        @Override
                        public void onLabelClick(TextView label, Object data, int position) {
                            if (type == WlmUtil.GOODSTYPE_INTEGRAL){
                                tv_goods_pop_price.setText(((GoodsChooseBean) data).getIntegral()+"积分 + " + ((GoodsChooseBean) data).getPrice() +"元");
                            }else {
                                tv_goods_pop_price.setText("" + ((GoodsChooseBean) data).getPrice());
                            }
                            goodsChooseBean = (GoodsChooseBean) data;
                            tv_size.setText("已选 ");
                            if (spec2 == position) {
                                if (tv_choose.getText().toString().equals("颜色")){
                                    tv_size.setText("");
                                }
                                tv_choose_size.setText("");
                                spec2 = -1;
                            }else {
                                spec2 = position;
                                tv_choose_size.setText("  " + goodsChooseBean.getSpec2());
                            }

                            ArrayList<String> specArray = hashMap.get(goodsChooseBean.getSpec2());
                            if (spec2 == -1){
                                flowLayout.setLabels(goodsChooseBeans1, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                                    @Override
                                    public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {
                                        if (spec1 != -1) {
                                            flowLayout.setSelects(spec1);
                                        }
//                                goodsChooseBean =data;
                                        tv_stock.setText(goodsChooseBean.getAmount() + "");

                                        for (GoodsChooseBean goodsChooseBean : goodsChooseBeans) {
                                            if (tv_choose.getText().toString().equals(goodsChooseBean.getSpec1()) && tv_choose_size.getText().toString().equals("  " + goodsChooseBean.getSpec2())) {
                                                SelfGoodsPopLayout.this.goodsChooseBean = goodsChooseBean;
                                            }
                                        }
                                        tv_stock.setText(goodsChooseBean.getAmount() + "");

                                        return data.getSpec1();
                                    }
                                });
                            }else {
                                flowLayout.setLabels(goodsChooseBeans1, new LabelsView.LabelTextProvider<GoodsChooseBean>() {
                                    @Override
                                    public CharSequence getLabelText(TextView label, int position, GoodsChooseBean data) {
                                        if (spec1 != -1) {
                                            flowLayout.setSelects(spec1);
                                        }
//                                goodsChooseBean =data;
                                        tv_stock.setText(goodsChooseBean.getAmount() + "");

                                        for (GoodsChooseBean goodsChooseBean : goodsChooseBeans) {
                                            if (tv_choose.getText().toString().equals(goodsChooseBean.getSpec1()) && tv_choose_size.getText().toString().equals("  " + goodsChooseBean.getSpec2())) {
                                                SelfGoodsPopLayout.this.goodsChooseBean = goodsChooseBean;
                                            }
                                        }
                                        tv_stock.setText(goodsChooseBean.getAmount() + "");

                                        return data.getSpec1();
                                    }
                                }, specArray);
                            }
                        }
                    });
                }
            }
        }


    }

    private void changeBgColor(int type){

        if (type == WlmUtil.GOODSTYPE_INTEGRAL){

            tv_money_icon.setVisibility(GONE);
            ColorStateList colorStateList = getResources().getColorStateList(R.color.integral_label_text_color);
            labael_size.setLabelTextColor(colorStateList);
            labael_size.setLabelBackgroundDrawable(getResources().getDrawable(R.drawable.integral_label_bg));

            flowLayout.setLabelTextColor(colorStateList);
            flowLayout.setLabelBackgroundDrawable(getResources().getDrawable(R.drawable.integral_label_bg));

            tv_buy_goods.setBackgroundColor(getResources().getColor(R.color.integral_bg));

            tv_add_cart.setBackgroundColor(getResources().getColor(R.color.integral_add_cart));

            tv_goods_pop_price.setTextColor(getResources().getColor(R.color.integral_bg));

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_goods_plus:
                num++;
                if (goodsChooseBean != null){
                    Stock = goodsChooseBean.getAmount();
                }
                if (num > Stock){
                    UToast.show(context,"超出库存");
                    num--;
                }else {
                    tv_goods_count.setText(num + "");
                }
                break;

            case R.id.iv_goods_subtraction:
                if (num > 1) {
                    num--;
                    tv_goods_count.setText(num+"");
                }
            break;

            case R.id.tv_add_cart:

                if (goodsDetailBean.getQty() == 1) {
                    if (goodsDetailBean == null || goodsChooseBean == null) {
                        UToast.show(context, "请选择规格");
                    } else {
                        if (flowLayout.getSelectLabelDatas().size() > 0) {
//                            onAddCart.addShopCart(selfGoodsBean, goodsChooseBean, num);
                        } else {
                            UToast.show(context,"请选择完整规格");
                        }
                    }
                }else if (goodsDetailBean.getQty() == 2) {
                    if (goodsDetailBean == null || goodsChooseBean == null){
                        UToast.show(context,"请选择规格");

                    }else {
                        if (flowLayout.getSelectLabelDatas().size() > 0  && labael_size.getSelectLabelDatas().size() > 0){
                            onAddCart.addShopCart(goodsDetailBean, goodsChooseBean,num);
                        }else {
                            UToast.show(context,"请选择完整规格");
                        }
                    }
                }else {
//                    onAddCart.addShopCart(selfGoodsBean, goodsChooseBean,num);
                }

                break;

            case R.id.iv_popup_exit:

                onAddCart.delete();

                break;

            case R.id.tv_buy_goods:

                if (goodsDetailBean.getQty() == 1) {
                    if (goodsDetailBean == null || goodsChooseBean == null) {
                        UToast.show(context, "请选择规格");
                    } else {
                        if (flowLayout.getSelectLabelDatas().size() > 0 ){
//                            onAddCart.mRightNowBuy(selfGoodsBean, goodsChooseBean, num);
                        }else {
                            UToast.show(context,"请完整规格");
                        }
                    }
                }else if (goodsDetailBean.getQty() == 2) {
                    if (goodsDetailBean == null || goodsChooseBean == null){
                        UToast.show(context,"请选择规格选择");

                    }else {
                        if (flowLayout.getSelectLabelDatas().size() > 0  && labael_size.getSelectLabelDatas().size() > 0){
                            onAddCart.mRightNowBuy(goodsDetailBean, goodsChooseBean, num);
                        }else {
                            UToast.show(context,"请选择完整规格");
                        }
                    }
                }else {
                        onAddCart.mRightNowBuy(goodsDetailBean,null,num);
                 }


                break;

            case R.id.tv_goods_count:

                showDialog(tv_goods_count);

                break;
        }
    }

    private void showDialog(final View showCountView){

        if (goodsChooseBean != null){
            Stock = goodsChooseBean.getAmount();
        }

        final AlertDialog.Builder alertDialog_Builder=new AlertDialog.Builder(context);
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_change_num,null);
        final AlertDialog dialog=alertDialog_Builder.create();
        dialog.setView(view);
        count=Integer.valueOf(tv_goods_count.getText().toString());
        final EditText numEt= (EditText) view.findViewById(R.id.dialog_num);
        numEt.setText(count+"");
        //自动弹出键盘
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                UtilTool.showKeyboard(context,showCountView);
            }
        });
        final TextView increase= (TextView) view.findViewById(R.id.dialog_increaseNum);
        final TextView DeIncrease=(TextView)view.findViewById(R.id.dialog_reduceNum);
        final TextView pButton= (TextView) view.findViewById(R.id.dialog_Pbutton);
        final TextView nButton= (TextView) view.findViewById(R.id.dialog_Nbutton);
        nButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int number= Integer.parseInt(numEt.getText().toString().trim());
                if(number==0){
                    dialog.dismiss();
                }else{
                    UtilsLog.i("数量="+number+"");
                    if (number > Stock){
                        UToast.show(context,"库存不足");
                    }else {
                        numEt.setText(String.valueOf(number));
                        ((TextView)showCountView).setText(numEt.getText().toString());
                        dialog.dismiss();
                    }
                }
            }
        });
        increase.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.valueOf(numEt.getText().toString());
                count++;
                numEt.setText(String.valueOf(count));
            }
        });
        DeIncrease.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.valueOf(numEt.getText().toString());
                if(count>1){
                    count--;
                    numEt.setText(String.valueOf(count));
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                num = Integer.valueOf(numEt.getText().toString());
            }
        });

        dialog.show();
    }

    public interface OnAddCart{
        public void addShopCart(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean, GoodsChooseBean goodsChooseBean, int num);
        public void delete();
        public void mRightNowBuy(GoodsDetailInfoBean selfGoodsBean, GoodsChooseBean goodsChooseBean, int num);
    }
}
