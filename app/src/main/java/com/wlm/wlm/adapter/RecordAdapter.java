package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.ui.CustomRoundAngleImageView;
import com.wlm.wlm.util.WlmUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/11/21.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    List<CollectBean> collectBeans ;
    CollectBean collectBean;
    private OnItemClickListener mItemClickListener;
    private String collectId = "";

    public RecordAdapter(Context context, List<CollectBean> collectBeans){
        this.context = context;
        this.collectBeans = collectBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_hot_goods_grid,null);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setTag(position);

        if (collectBeans != null) {

            collectBean = collectBeans.get(position);

            holder.goodsTitleNameTv.setText(collectBean.getGoodsName());

            holder.goodsPriceTv.setText("" + collectBean.getPrice());
            holder.goodsBuyCountTv.setText(collectBean.getUseNumber() + "");

            holder.tv_add_integral.setText(collectBean.getIntegral()+"");
            holder.tv_old_price.setText("¥"+collectBean.getMarketPrice());
            holder.tv_old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

            if (collectBean.getGoodsImg() != null && !collectBean.getGoodsImg().isEmpty()) {
                Picasso.with(context).load(ProApplication.HEADIMG + collectBean.getGoodsImg()).error(R.mipmap.ic_adapter_error).into(holder.goodsPicImg);
            }
        }
    }


    @Override
    public int getItemCount() {
        return collectBeans != null ? collectBeans.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setData(List<CollectBean> collectBeans){
        this.collectBeans = collectBeans;
        notifyDataSetChanged();
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



    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView goodsTitleNameTv;
        private TextView goodsPriceTv;
        private TextView goodsBuyCountTv;
        private CustomRoundAngleImageView goodsPicImg;
        private LinearLayout ll_add_integral;
        private TextView tv_add_integral;
        private TextView tv_old_price;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsBuyCountTv = (TextView) itemView.findViewById(R.id.tv_buy_count);
            tv_add_integral = (TextView) itemView.findViewById(R.id.tv_add_integral);
            goodsTitleNameTv = (TextView) itemView.findViewById(R.id.tv_goods_title_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.tv_goods_price);
            goodsPicImg = (CustomRoundAngleImageView) itemView.findViewById(R.id.iv_goods_pic);
            ll_add_integral = (LinearLayout) itemView.findViewById(R.id.ll_add_integral);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);

        }
    }

}
