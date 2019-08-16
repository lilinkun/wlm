package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.PointListBean;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/29.
 */

public class IntegralAdapter extends RecyclerView.Adapter<IntegralAdapter.ViewHolder> {

    private ArrayList<PointListBean> integralBeans;
    private Context mContext;
    private int type = 0;

    public IntegralAdapter(Context mContext,ArrayList<PointListBean> integralBeans,int type){
        this.mContext = mContext;
        this.integralBeans = integralBeans;
        this.type = type;
    }

    public void setData(ArrayList<PointListBean> integralBeans){
        this.integralBeans = integralBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_integral,null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(integralBeans.get(position).getTradeTypeName());
        holder.tv_date.setText(integralBeans.get(position).getCretateTime());
        BigDecimal c = new BigDecimal(integralBeans.get(position).getExpenditureMoney());
        double expenditureMoney = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (integralBeans.get(position).getReceiptsOrOut() == -1) {
            if (type == 1) {
                holder.tv_price.setText("-" + expenditureMoney + "元");
            }else {
                holder.tv_price.setText("-" + expenditureMoney );
            }
            holder.tv_price.setTextColor(mContext.getResources().getColor(R.color.red_text));
        }else {
            if (type == 1) {
                holder.tv_price.setText("+" + expenditureMoney + "元");
            }else {
                holder.tv_price.setText("+" + expenditureMoney );
            }
            holder.tv_price.setTextColor(mContext.getResources().getColor(R.color.login_title_text));
        }

//        if (type == 1){
            BigDecimal b = new BigDecimal(integralBeans.get(position).getBalance());
            double balance = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holder.tv_total.setText("总额：" + balance);
//        }else {
//            holder.tv_total.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return integralBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;
        private TextView tv_date;
        private TextView tv_price;
        private TextView tv_total;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_total = (TextView) itemView.findViewById(R.id.tv_total);
        }
    }

}
