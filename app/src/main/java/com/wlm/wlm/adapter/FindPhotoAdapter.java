package com.wlm.wlm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.util.DensityUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2019/10/24.
 */
public class FindPhotoAdapter extends RecyclerView.Adapter<FindPhotoAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<String> photoList;
    private OnItemClickListener mItemClickListener;

    public FindPhotoAdapter(Context context, ArrayList<String> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_find_photo, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(position);

        ViewGroup.LayoutParams layoutParams = holder.iv_find_adapter.getLayoutParams();
        layoutParams.height = layoutParams.width = DensityUtil.getScreenWidth(context) / 3 - 80;
        holder.iv_find_adapter.setLayoutParams(layoutParams);

        Picasso.with(context).load(ProApplication.BANNERIMG + photoList.get(position)).error(R.mipmap.ic_adapter_error).into(holder.iv_find_adapter);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return photoList.size();
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

        private ImageView iv_find_adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_find_adapter = itemView.findViewById(R.id.iv_find_adapter);
        }
    }


}
