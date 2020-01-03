package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.util.HomeGridRvEnum;
import com.wlm.wlm.util.WlmUtil;

/**
 * Created by LG on 2019/10/28.
 */
public class GridHomeAdapter extends RecyclerView.Adapter<GridHomeAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;

    public GridHomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_home, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        HomeGridRvEnum homeGridRvEnum = HomeGridRvEnum.values()[position];

//        holder.iv_home_grid.setImageResource(homeGridRvEnum.getSrcmsg());

        Picasso.with(context).load(ProApplication.HOMEADDRESS + homeGridRvEnum.getMsgAddress()).into(holder.iv_home_grid);

        holder.tv_home_grid.setText(homeGridRvEnum.getStatusMsg());

    }

    @Override
    public int getItemCount() {
        return HomeGridRvEnum.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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

        private ImageView iv_home_grid;
        private TextView tv_home_grid;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_home_grid = itemView.findViewById(R.id.iv_home_grid);
            tv_home_grid = itemView.findViewById(R.id.tv_home_grid);
        }
    }
}
