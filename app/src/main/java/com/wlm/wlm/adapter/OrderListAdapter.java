package com.wlm.wlm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.activity.StoreActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.BuyBean;
import com.wlm.wlm.entity.ChildListBean;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/15.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private Context context;
    private BuyBean buyBeans;
    private String goodsIds = "";
    private String storeIds = "";
    private String spec = "";
    private OnDataGetFare onDataGetFare;
    private int num = 0;
    private ArrayList<TextView> textViews = new ArrayList<>();
    private int point = 0;

    public OrderListAdapter(Context context, BuyBean buyBeans,OnDataGetFare onDataGetFare){
        this.context = context;
        this.buyBeans = buyBeans;
        this.onDataGetFare = onDataGetFare;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mShopName.setText(buyBeans.getStoremodel().get(position).getShop_name());

        if (buyBeans.getUsermodel().getPoint()  > buyBeans.getStoremodel().get(position).getMax_use_Point()) {
            holder.mIntegral.setText(buyBeans.getStoremodel().get(position).getMax_use_Point() + "");
        }else {

            BigDecimal b = new BigDecimal(buyBeans.getUsermodel().getPoint());
            double po = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holder.mIntegral.setText(po + "");
        }
//        holder.mFreight.setText(buyBeans.get(position).getStoremodel().getMax_use_Point());
        point += (int)(Double.parseDouble(holder.mIntegral.getText().toString()));
        Picasso.with(context).load(ProApplication.HEADIMG + buyBeans.getStoremodel().get(position).getShop_logo()).error(R.mipmap.ic_shop).into(holder.mShopIcon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<ChildListBean> childListBeans = buyBeans.getStoremodel().get(position).getGoodsList();

        if (storeIds.equals("")){
            storeIds = buyBeans.getStoremodel().get(position).getStore_id();
        }else {
            storeIds = storeIds + buyBeans.getStoremodel().get(position).getStore_id();
        }

        textViews.add(holder.mFreight);

        OrderShopAdapter orderShopAdapter = new OrderShopAdapter(context,childListBeans);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(orderShopAdapter);

        holder.ll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("storeid",storeIds);
                UiHelper.launcherBundle(context, StoreActivity.class,bundle);
            }
        });

        orderShopAdapter.setItemClickListener(new OrderShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int posId) {

//                Bundle bundle = new Bundle();
//                bundle.putString("goodsid",buyBeans.getStoremodel().get(position).getGoodsList().get(posId).getGoods_id() +"");
//                UiHelper.launcherBundle(context, SelfGoodsDetailActivity.class,bundle);
            }
        });
        for (ChildListBean childListBean : childListBeans){
            if (goodsIds.equals("")){
                goodsIds =  childListBean.getGoods_id()+"";
            }else {
                goodsIds = "," + childListBean.getGoods_id();
            }
            if (spec.equals("")) {
                spec = childListBean.getAttr_id();
            }else {
                spec = spec + "," + childListBean.getAttr_id();
            }

                num += childListBean.getNum();
        }

        if (position == buyBeans.getStoremodel().size()-1){
            onDataGetFare.onGetFare(goodsIds,storeIds,spec,num+"");
            onDataGetFare.onPoint(point);
        }

        holder.rl_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_edittext,null);
                final EditText editText = (EditText) view.findViewById(R.id.edit_num);
                editText.setText(holder.mIntegral.getText().toString());
                new AlertDialog.Builder(context).setMessage("修改使用积分").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().isEmpty()){
                            if (Integer.valueOf(editText.getText().toString()) > buyBeans.getUsermodel().getPoint()){
                                UToast.show(context, "超出可使用积分");
                            }else {
                                if (Integer.valueOf(editText.getText().toString()) > buyBeans.getStoremodel().get(position).getMax_use_Point()) {
                                    UToast.show(context, "超出最大使用积分");
                                } else {
                                    point += Integer.valueOf(editText.getText().toString()) - (int) (Double.parseDouble(holder.mIntegral.getText().toString()));
                                    holder.mIntegral.setText(editText.getText().toString() + "");
                                    onDataGetFare.onPoint(point);
                                }
                            }
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

    }

    public void setFreight(String price){
        textViews.get(0).setText(price);
    }

    @Override
    public int getItemCount() {
        return buyBeans.getStoremodel().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mShopName;
        ImageView mShopIcon;
        TextView mIntegral;
        TextView mFreight;
        RecyclerView recyclerView;
        RelativeLayout rl_point;
        LinearLayout ll_shop;
        public ViewHolder(View itemView) {
            super(itemView);
            mShopName = itemView.findViewById(R.id.tv_shop_name);
            mShopIcon = itemView.findViewById(R.id.iv_shop_icon);
            mIntegral = itemView.findViewById(R.id.tv_integral);
            mFreight = itemView.findViewById(R.id.tv_freight);
            recyclerView = itemView.findViewById(R.id.rv_child);
            rl_point = itemView.findViewById(R.id.rl_point);
            ll_shop = itemView.findViewById(R.id.ll_shop);
        }
    }

    public interface OnDataGetFare{
        public void onGetFare(String goodsids, String storeIds, String spec, String num);
        public void onPoint(int point);
    }
}
