package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.ArticleBean;
import com.wlm.wlm.ui.RoundImageView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/9/23.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<ArticleBean> articleBeans;
    private OnItemClickListener mItemClickListener;

    public MessageAdapter(Context context,ArrayList<ArticleBean> articleBeans){
        this.context = context;
        this.articleBeans = articleBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_message,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (position == 0 || position == 1){
            holder.riv_message.setImageResource(R.mipmap.ic_news_2);
        }else if (position == 2){
            holder.riv_message.setImageResource(R.mipmap.ic_news_3);
        }else if (position == 3){
            holder.riv_message.setImageResource(R.mipmap.ic_news_4);
        }

        holder.tv_title.setText(articleBeans.get(position).getTitle());
        holder.tv_message_content.setText(articleBeans.get(position).getCategoryName());
        holder.tv_message_date.setText(articleBeans.get(position).getCreateDate());
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

        private RoundImageView riv_message;
        private TextView tv_title;
        private TextView tv_message_date;
        private TextView tv_message_content;

        public ViewHolder(View itemView) {
            super(itemView);

            riv_message = itemView.findViewById(R.id.riv_message);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_message_date = itemView.findViewById(R.id.tv_message_date);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
        }
    }
}
