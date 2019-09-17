package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.BankBean;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/17.
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<BankBean> bankBeans;
    private OnItemClickListener mItemClickListener;

    public BankAdapter(Context context,ArrayList<BankBean> bankBeans){
        this.context = context;
        this.bankBeans = bankBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_choose_groupon,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tv_groupon.setText(bankBeans.get(position).getBankName());
    }

    @Override
    public int getItemCount() {
        return bankBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setOnItemClick(OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_groupon;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_groupon = itemView.findViewById(R.id.tv_groupon);
        }
    }
}
