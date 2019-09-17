package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.HomeCategoryBean;
import com.wlm.wlm.entity.TBbean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/11/15.
 */

public class HomeFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<HomeCategoryBean> tBbeans;

    public HomeFragmentAdapter(Context context, List<HomeCategoryBean> tBbeans) {
        this.context = context;
        this.tBbeans = tBbeans;
    }

    public void setData( List<HomeCategoryBean> tBbeans){
        this.tBbeans = tBbeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tBbeans.size();
    }

    @Override
    public Object getItem(int position) {
        return tBbeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 8;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.home_gridview_item, null);

            viewHolder.tbTypeImg = (ImageView) convertView.findViewById(R.id.iv_grid_item);
            viewHolder.tbTypeName = (TextView) convertView.findViewById(R.id.tv_grid_item);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.tbTypeImg.setImageResource(Integer.valueOf(tBbeans.get(position).getCate_icon()));
//        viewHolder.tbTypeName.setText(tBbeans.get(position).getCate_name());
//        viewHolder.tbTypeImg.setImageResource(ints[position]);
        viewHolder.tbTypeName.setText(tBbeans.get(position).getCategoryName());

//        Picasso.with(context).load(ProApplication.BANNERIMG + tBbeans.get(position).getCat_icon()).into(viewHolder.tbTypeImg);
        Picasso.with(this.context).load(ProApplication.BANNERIMG + tBbeans.get(position).getIsLink()).resize(200, 200).centerCrop().config(Bitmap.Config.RGB_565).into(viewHolder.tbTypeImg);

        return convertView;
    }

    class ViewHolder {
        ImageView tbTypeImg;
        TextView tbTypeName;
    }
}
