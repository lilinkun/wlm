package com.wlm.wlm.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.entity.OpinionBean;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.WlmUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/25.
 */
public class OpinionListAdapter extends RecyclerView.Adapter<OpinionListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OpinionBean> opinionBeans;

    public OpinionListAdapter(Context context,ArrayList<OpinionBean> opinionBeans){
        this.context = context;
        this.opinionBeans = opinionBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_list_opinion,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(WlmUtil.LOGIN,context.MODE_PRIVATE);
        if (!sharedPreferences.getString(WlmUtil.HEADIMGURL,"").equals("")) {
            Picasso.with(context).load(sharedPreferences.getString(WlmUtil.HEADIMGURL, "")).into(holder.riv_opinion);
        }

        holder.tv_opinion_name.setText(opinionBeans.get(position).getNickName());
        holder.tv_retry_name.setText(opinionBeans.get(position).getReplyName());
        holder.tv_opinion_content.setText(opinionBeans.get(position).getQuestion());
        holder.tv_retry_content.setText(opinionBeans.get(position).getReplyContent());
        holder.tv_opinion_date.setText(opinionBeans.get(position).getCreateDate());


        if (opinionBeans.get(position).getStatus() == 0){
            holder.rl_retry.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return opinionBeans.size();
    }

    public void setData(ArrayList<OpinionBean> opinionBeans){
        this.opinionBeans = opinionBeans;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RoundImageView riv_opinion;
        private TextView tv_opinion_name;
        private TextView tv_opinion_date;
        private TextView tv_opinion_content;
        private TextView tv_retry_name;
        private TextView tv_retry_content;
        private RelativeLayout rl_retry;


        public ViewHolder(View itemView) {
            super(itemView);
            riv_opinion = itemView.findViewById(R.id.riv_opinion);
            tv_opinion_name = itemView.findViewById(R.id.tv_opinion_name);
            tv_opinion_date = itemView.findViewById(R.id.tv_opinion_date);
            tv_opinion_content = itemView.findViewById(R.id.tv_opinion_content);
            tv_retry_name = itemView.findViewById(R.id.tv_retry_name);
            tv_retry_content = itemView.findViewById(R.id.tv_retry_content);
            rl_retry = itemView.findViewById(R.id.rl_retry);
        }
    }
}
