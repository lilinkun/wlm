package com.wlm.wlm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.AllOrderActivity;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.OrderDetailBean;
import com.wlm.wlm.util.UiHelper;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/21.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<OrderDetailBean> orderDetailBeans;
    private OnItemClickListener mItemClickListener;

    public OrderAdapter(Context context, ArrayList<OrderDetailBean> orderDetailBeans){
        this.mContext = context;
        this.orderDetailBeans = orderDetailBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.parent_item,null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        holder.itemView.setTag(position);
        holder.mShopName.setText(orderDetailBeans.get(position).getStoreModel().getShop_name());
        if (orderDetailBeans.get(position).getShipping_fee() == 0){
            holder.mFreight.setText("免邮");
        }else {
            holder.mFreight.setText(orderDetailBeans.get(position).getShipping_fee() + "");
        }
        holder.mIntegral.setText(orderDetailBeans.get(position).getUseIntegral()+"");

        OrderChildAdapter orderChildAdapter = new OrderChildAdapter(mContext,orderDetailBeans.get(position).getOrderGoodsItem());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        orderChildAdapter.setItemClickListener(new OrderChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positionId) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsid",orderDetailBeans.get(position).getOrderGoodsItem().get(positionId).getGoodsId());
                UiHelper.launcherBundle(mContext,SelfGoodsDetailActivity.class,bundle);
            }
        });

        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(orderChildAdapter);

        Picasso.with(mContext).load(ProApplication.HEADIMG + orderDetailBeans.get(position).getStoreModel().getShop_logo()).into(holder.mShopIcon);

    }

    @Override
    public int getItemCount() {
        return orderDetailBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mShopName;
        ImageView mShopIcon;
        TextView mIntegral;
        TextView mFreight;
        RecyclerView recyclerView;
        RelativeLayout rl_point;
        public ViewHolder(View itemView) {
            super(itemView);
            mShopName = itemView.findViewById(R.id.tv_shop_name);
            mShopIcon = itemView.findViewById(R.id.iv_shop_icon);
            mIntegral = itemView.findViewById(R.id.tv_integral);
            mFreight = itemView.findViewById(R.id.tv_freight);
            recyclerView = itemView.findViewById(R.id.rv_child);
            rl_point = itemView.findViewById(R.id.rl_point);
        }
    }

}
