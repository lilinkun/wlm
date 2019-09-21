package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.SelfOrderInfoBean;
import com.squareup.picasso.Picasso;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/19.
 */

public class SelfOrderListAdapter extends RecyclerView.Adapter<SelfOrderListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<SelfOrderInfoBean> selfOrderInfoBeans;

    public SelfOrderListAdapter(Context context,ArrayList<SelfOrderInfoBean> selfOrderInfoBeans){
        this.context = context;
        this.selfOrderInfoBeans = selfOrderInfoBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_list_self_goods,null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_goods_count.setText("X" + selfOrderInfoBeans.get(position).getGoodsNumber());
        holder.tv_goods_title.setText("" + selfOrderInfoBeans.get(position).getGoodsName());
        holder.tv_goods_price.setText("¥" + selfOrderInfoBeans.get(position).getPrice());
        holder.tv_goods_spec1.setText(selfOrderInfoBeans.get(position).getAttrOne());
        holder.tv_goods_spec2.setText(selfOrderInfoBeans.get(position).getAttrTwo());
        Picasso.with(context).load(ProApplication.HEADIMG + selfOrderInfoBeans.get(position).getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.iv_goods_pic);

    }

    @Override
    public int getItemCount() {
        return selfOrderInfoBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_goods_count;
        private TextView tv_goods_title;
        private ImageView iv_goods_pic;
        private TextView tv_goods_price;
        private TextView tv_goods_spec1;
        private TextView tv_goods_spec2;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_count = (TextView) itemView.findViewById(R.id.tv_goods_count);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            iv_goods_pic = (ImageView) itemView.findViewById(R.id.iv_goods_pic);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_spec1 = (TextView) itemView.findViewById(R.id.tv_goods_spec1);
            tv_goods_spec2 = (TextView) itemView.findViewById(R.id.tv_goods_spec2);
        }
    }
}
