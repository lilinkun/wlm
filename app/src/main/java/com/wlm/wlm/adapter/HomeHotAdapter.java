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
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/29.
 */
public class HomeHotAdapter extends RecyclerView.Adapter<HomeHotAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GoodsListBean> hotHomeBeans = null;
    private OnItemClickListener onItemClickListener;
    private GoodsListBean homeBean;
    private boolean isAdd_Integral = false;

    public HomeHotAdapter(Context context, ArrayList<GoodsListBean> homeHeadBean) {
        this.hotHomeBeans = homeHeadBean;
        this.context = context;
    }

    public void setData(ArrayList<GoodsListBean> hotHomeBeans){
        this.hotHomeBeans = hotHomeBeans;
        notifyDataSetChanged();
    }

    public void setAdd_Integral(){
        isAdd_Integral = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_home_hot, null);

        ViewHolder myViewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        if (hotHomeBeans != null) {

            homeBean = hotHomeBeans.get(position);

            holder.goodsTitleNameTv.setText(homeBean.getGoodsName());

            holder.goodsPriceTv.setText("" + homeBean.getPrice());
            holder.goodsBuyCountTv.setText(homeBean.getUseNumber() + "");

            holder.tv_add_integral.setText(homeBean.getIntegral()+"");

            holder.tv_old_price.setText("¥"+ WlmUtil.getPriceNum(homeBean.getMarketPrice()));
            holder.tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

            if (isAdd_Integral){
                holder.ll_add_integral.setVisibility(View.VISIBLE);
            }

            if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_POINT+"")){
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText("9.9尖货");
            }else if (homeBean.getGoodsType().equals(WlmUtil.GOODSTYPE_SECKILL+"")){
                holder.ll_hot.setVisibility(View.VISIBLE);
                holder.tv_hot.setText("限时秒杀");
            }

            if (homeBean.getGoodsImg() != null && !homeBean.getGoodsImg().isEmpty()) {
                Picasso.with(context).load(ProApplication.HEADIMG + homeBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsPicImg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return hotHomeBeans != null ? hotHomeBeans.size() : 0;
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
        private TextView goodsBuyCountTv;
        private CustomRoundAngleImageView goodsPicImg;
        private LinearLayout ll_add_integral;
        private TextView tv_add_integral;
        private TextView tv_old_price;
        private LinearLayout ll_hot;
        private TextView tv_hot;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsBuyCountTv = (TextView) itemView.findViewById(R.id.tv_buy_count);
            tv_add_integral = (TextView) itemView.findViewById(R.id.tv_add_integral);
            goodsTitleNameTv = (TextView) itemView.findViewById(R.id.tv_goods_title_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.tv_goods_price);
            goodsPicImg = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            ll_add_integral = (LinearLayout) itemView.findViewById(R.id.ll_add_integral);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_hot = (TextView) itemView.findViewById(R.id.tv_hot);
            ll_hot = (LinearLayout) itemView.findViewById(R.id.ll_hot);

        }
    }


}
