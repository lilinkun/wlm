package com.wlm.wlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.TBbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/11/14.
 */

public class MenuGridListAdapter extends BaseAdapter {
    private Context context;
    private List<TBbean> list = new ArrayList();

    public MenuGridListAdapter(Context paramContext, List<TBbean> paramList) {
        this.list = paramList;
        this.context = paramContext;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int paramInt) {
        return this.list.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        Holder viewHolder = null;
        if (paramView == null) {
            viewHolder = new Holder();
            paramView = LayoutInflater.from(this.context).inflate(R.layout.menu_gridview_item, null);
            viewHolder.tx_meua = ((TextView) paramView.findViewById(R.id.tx_meua));
            paramView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) paramView.getTag();
        }
        TBbean localTBbean = (TBbean) this.list.get(paramInt);
        viewHolder.tx_meua.setText(localTBbean.cate_name);

        return paramView;
    }

    public class Holder {
        public TextView tx_meua;

        public Holder() {
        }
    }
}
