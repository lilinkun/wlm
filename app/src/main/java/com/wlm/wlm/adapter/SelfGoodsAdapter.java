package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.TextViewBeforeTextChangeEvent;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/12.
 */

public class SelfGoodsAdapter extends RecyclerView.Adapter<SelfGoodsAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<SelfGoodsBean> selfGoodsBeans ;
    private Context context;
    private OnItemClickListener mItemClickListener;
    private int num =0;

    public SelfGoodsAdapter(Context context,ArrayList<SelfGoodsBean> selfGoodsBeans,int num){
        this.context = context;
        this.selfGoodsBeans = selfGoodsBeans;
        this.num = num;
    }

    public void setData(ArrayList<SelfGoodsBean> selfGoodsBeans){
        this.selfGoodsBeans = selfGoodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (num == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_self_goods, null);
        }else if (num == 3){
            view =  LayoutInflater.from(context).inflate(R.layout.adapter_self_recommend, null);
        }

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        holder.tvself_title.setText(selfGoodsBeans.get(position).getGoods_name());
        holder.tvself_price.setText( "¥"+selfGoodsBeans.get(position).getShop_price());

        holder.tv_goods_volume.setText(selfGoodsBeans.get(position).getUse_number());
        if (num == 2){
            holder.tv_integral.setVisibility(View.VISIBLE);
            holder.tv_integral.setText("可用积分抵扣" + selfGoodsBeans.get(position).getGive_integral());
        }

        Picasso.with(context).load(ProApplication.HEADIMG+selfGoodsBeans.get(position).getGoods_img()).error(R.color.white).into(holder.ivself_goods);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return selfGoodsBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvself_price;
        private TextView tvself_title;
        private ImageView ivself_goods;
        private TextView tv_goods_volume;
        private TextView tv_integral;

        public ViewHolder(View itemView) {
            super(itemView);
            tvself_price = (TextView) itemView.findViewById(R.id.tv_self_price);
            tvself_title = (TextView) itemView.findViewById(R.id.tv_self_goods_title);
            ivself_goods = (ImageView) itemView.findViewById(R.id.iv_goods_pic);
            tv_goods_volume = (TextView) itemView.findViewById(R.id.tv_goods_volume);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral);
        }
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


}
