package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.BalanceDetailBean;
import com.wlm.wlm.entity.PointListBean;
import com.wlm.wlm.util.WlmUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by LG on 2018/12/29.
 */

public class IntegralAdapter extends RecyclerView.Adapter<IntegralAdapter.ViewHolder> {

    private ArrayList<BalanceDetailBean> balanceDetailBeans;
    private Context mContext;
    private int type = 0;

    public IntegralAdapter(Context mContext, ArrayList<BalanceDetailBean> balanceDetailBeans, int type){
        this.mContext = mContext;
        this.balanceDetailBeans = balanceDetailBeans;
        this.type = type;
    }

    public void setData(ArrayList<BalanceDetailBean> integralBeans){
        this.balanceDetailBeans = integralBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_integral,null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(balanceDetailBeans.get(position).getTradeSay());
        holder.tv_date.setText(balanceDetailBeans.get(position).getCretateTime());
        BigDecimal c = new BigDecimal(balanceDetailBeans.get(position).getExpenditureMoney());
        double expenditureMoney = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (balanceDetailBeans.get(position).getReceiptsOrOut() == -1) {
            if (type == 1) {
                holder.tv_price.setText("-" + expenditureMoney + "元");
            }else {
                holder.tv_price.setText("-" + expenditureMoney );
            }
            holder.tv_price.setTextColor(mContext.getResources().getColor(R.color.login_title_text));
        }else {
            if (type == 1 ) {
                holder.tv_price.setText("+" + expenditureMoney + "元");
            }else {
                holder.tv_price.setText("+" + expenditureMoney );
            }
            holder.tv_price.setTextColor(mContext.getResources().getColor(R.color.setting_title_color));
        }

        if (type == 2){
            holder.tv_total.setVisibility(View.GONE);
        }

//        if (type == 1){

            String balance = WlmUtil.getPriceNum(balanceDetailBeans.get(position).getBalance());

            holder.tv_total.setText("剩余：" + balance);
//        }else {
//            holder.tv_total.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return balanceDetailBeans.size();
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
