package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.ui.RoundImageView;
import com.wlm.wlm.util.MessageType;

/**
 * Created by LG on 2019/9/23.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private MessageType messageType;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        messageType = MessageType.values()[position];

        holder.riv_message.setImageResource(messageType.getDrawBg());
        holder.tv_title.setText(messageType.getTypeName());
        holder.tv_message_content.setText(messageType.getContent());
    }

    @Override
    public int getItemCount() {
        return MessageType.values().length;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RoundImageView riv_message;
        private TextView tv_title;
        private TextView tv_message_content;

        public ViewHolder(View itemView) {
            super(itemView);

            riv_message = itemView.findViewById(R.id.riv_message);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_message_content = itemView.findViewById(R.id.tv_message_content);
        }
    }
}
