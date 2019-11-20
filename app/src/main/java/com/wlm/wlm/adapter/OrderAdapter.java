package com.wlm.wlm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.entity.OrderDetailAddressBean;
import com.wlm.wlm.util.UiHelper;

/**
 * Created by LG on 2018/12/21.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OrderDetailAddressBean orderDetailBeans;
    private OnItemClickListener mItemClickListener;

    public OrderAdapter(Context context, OrderDetailAddressBean orderDetailBeans) {
        this.mContext = context;
        this.orderDetailBeans = orderDetailBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.parent_item, null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setTag(position);

        OrderChildAdapter orderChildAdapter = new OrderChildAdapter(mContext, orderDetailBeans.getOrderDetail());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        orderChildAdapter.setItemClickListener(new OrderChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positionId) {
                Bundle bundle = new Bundle();
                bundle.putString("goodsid", orderDetailBeans.getOrderDetail().get(position).getGoodsId());
                UiHelper.launcherBundle(mContext, SelfGoodsDetailActivity.class, bundle);
            }
        });

        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(orderChildAdapter);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_child);
        }
    }

}
