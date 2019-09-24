package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.entity.JdCategoryInfoBean;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/24.
 */
public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<ArticleBean> articleBeans;
    private OnItemClickListener mItemClickListener;

    public MessageDetailAdapter(Context context,ArrayList<ArticleBean> articleBeans){
        this.context = context;
        this.articleBeans = articleBeans;
    }

    @Override
    public MessageDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_message_detail,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageDetailAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(position);
        holder.tv_message_detail.setText(articleBeans.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return articleBeans.size();
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

        TextView tv_message_detail;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_message_detail = itemView.findViewById(R.id.tv_message_detail);
        }
    }
}
