package com.wlm.wlm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.CategoryListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 左侧菜单ListView的适配器
 *
 * @author liguo
 */
public class MenuLeftAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    ArrayList<CategoryListBean<ArrayList<Object>>> homeCategoryBeans;

    public MenuLeftAdapter(Context context,ArrayList<CategoryListBean<ArrayList<Object>>> homeCategoryBeans) {
        this.homeCategoryBeans = homeCategoryBeans;
        this.context = context;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return homeCategoryBeans.size();
    }

    @Override
    public Object getItem(int arg0) {
        return homeCategoryBeans.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder = null;
        if (arg1 == null) {
            holder = new ViewHolder();
            arg1 = View.inflate(context, R.layout.item_menu, null);
            holder.tv_name = (TextView) arg1.findViewById(R.id.item_name);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        if (arg0 == selectItem) {
            holder.tv_name.setBackgroundColor(context.getResources().getColor(R.color.whitesmoke));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.setting_title_color));
        } else {
            holder.tv_name.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.pop_text_bg));
        }
        holder.tv_name.setText(homeCategoryBeans.get(arg0).getCat_name());
        return arg1;
    }

    static class ViewHolder {
        private TextView tv_name;
    }
}
