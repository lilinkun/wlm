package com.wlm.wlm.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.adapter.RecordAdapter;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.SelfGoodsBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/22.
 */

public class SelfRecommendAdapter extends RecyclerView.Adapter<SelfRecommendAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<SelfGoodsBean> selfGoodsBeans ;
    private Context context;
    private RecordAdapter.OnItemClickListener mItemClickListener;

    public SelfRecommendAdapter(Context context,ArrayList<SelfGoodsBean> selfGoodsBeans){
        this.context = context;
        this.selfGoodsBeans = selfGoodsBeans;
    }

    public void setData(ArrayList<SelfGoodsBean> selfGoodsBeans){
        this.selfGoodsBeans = selfGoodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_self_goods,null);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        holder.tvself_title.setText(selfGoodsBeans.get(position).getGoods_name());
        holder.tvself_price.setText( "¥"+selfGoodsBeans.get(position).getShop_price());

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

        public ViewHolder(View itemView) {
            super(itemView);
            tvself_price = (TextView) itemView.findViewById(R.id.tv_self_price);
            tvself_title = (TextView) itemView.findViewById(R.id.tv_self_goods_title);
            ivself_goods = (ImageView) itemView.findViewById(R.id.iv_goods_pic);
        }
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(RecordAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }


}