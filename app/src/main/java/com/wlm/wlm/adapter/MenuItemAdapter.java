package com.wlm.wlm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.activity.SelfGoodsDetailActivity;
import com.wlm.wlm.activity.SelfGoodsTypeActivity;
import com.wlm.wlm.base.ProApplication;
import com.wlm.wlm.entity.CategoryBean;
import com.wlm.wlm.entity.HomeCategoryBean;
import com.wlm.wlm.util.ButtonUtils;
import com.wlm.wlm.util.UToast;
import com.wlm.wlm.util.UiHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * author：liguo
 * date： 2018/12/7 19:15
 * desctiption：
 * ""
 */

public class MenuItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HomeCategoryBean> homeCategoryBeans;

    public MenuItemAdapter(Context context, ArrayList<HomeCategoryBean> homeCategoryBeans) {
        this.context = context;
        this.homeCategoryBeans = homeCategoryBeans;
    }

    public void setData(ArrayList<HomeCategoryBean> homeCategoryBeans){
        this.homeCategoryBeans = homeCategoryBeans;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (homeCategoryBeans != null) {
            return homeCategoryBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return homeCategoryBeans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (ImageView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(homeCategoryBeans.get(position).getCat_name());
        Picasso.with(context).load(ProApplication.BANNERIMG + homeCategoryBeans.get(position).getCat_indeximg()).into(viewHold.iv_icon);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ButtonUtils.isFastDoubleClick()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putSerializable("home", homeCategoryBeans.get(position));
                    UiHelper.launcherBundle(context, SelfGoodsTypeActivity.class, bundle);
                }
            }
        });


        return convertView;

    }

    private static class ViewHold {
        private TextView tv_name;
        private ImageView iv_icon;
    }

}
