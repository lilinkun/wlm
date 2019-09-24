package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.GoodsListBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.util.MallType;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/24.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GoodsListBean> goodsListBeans;
    private GoodsListBean goodsListBean;
    private OnItemClickListener onItemClickListener;

    public SearchAdapter(Context context,ArrayList<GoodsListBean> goodsListBeans){
        this.context = context;
        this.goodsListBeans = goodsListBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_search, parent,false);

        ViewHolder myViewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        if (goodsListBeans != null) {

            goodsListBean = goodsListBeans.get(position);

            holder.goodsTitleNameTv.setText(goodsListBean.getGoodsName());

            holder.goodsPriceTv.setText("" + goodsListBean.getPrice());

            holder.tv_add_integral.setText(goodsListBean.getIntegral()+"");
            holder.tv_old_price.setText("￥"+goodsListBean.getMarketPrice());
            holder.tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );


            holder.tv_goods_type.setText(MallType.getVipById(goodsListBean.getGoodsType()).getTypeName());

            holder.tv_goods_type.setBackgroundResource(MallType.getVipById(goodsListBean.getGoodsType()).getDrawBg());

            if (goodsListBean.getGoodsImg() != null && !goodsListBean.getGoodsImg().isEmpty()) {
                Picasso.with(context).load(ProApplication.HEADIMG + goodsListBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsPicImg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return goodsListBeans != null ? goodsListBeans.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null){
            onItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView goodsTitleNameTv;
        private TextView goodsPriceTv;
        private CustomRoundAngleImageView goodsPicImg;
        private LinearLayout ll_add_integral;
        private TextView tv_add_integral;
        private TextView tv_old_price;
        private TextView tv_goods_type;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_type = (TextView) itemView.findViewById(R.id.tv_goods_type);
            tv_add_integral = (TextView) itemView.findViewById(R.id.tv_add_integral);
            goodsTitleNameTv = (TextView) itemView.findViewById(R.id.tv_goods_title_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.tv_goods_price);
            goodsPicImg = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            ll_add_integral = (LinearLayout) itemView.findViewById(R.id.ll_add_integral);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);

        }
    }
}