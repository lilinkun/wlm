package com.wlm.wlm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.entity.AddressBean;

import java.util.ArrayList;

/**
 * Created by LG on 2018/12/9.
 */

public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<AddressBean> addressBeans;
    private LayoutInflater layoutInflater;
    private OnDeleteAddress onDeleteAddress;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private Context context;
    private SetOnItemClickListener onItemClick;

    public ChooseAddressAdapter(Context context, ArrayList<AddressBean> addressBeans, LayoutInflater layoutInflater, OnDeleteAddress onDeleteAddress) {
        this.context = context;
        this.addressBeans = addressBeans;
        this.layoutInflater = layoutInflater;
        this.onDeleteAddress = onDeleteAddress;
    }

    public void setData(ArrayList<AddressBean> addressBeans) {
        this.addressBeans = addressBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.adapter_choose_address, null);

        ViewHolder viewHolder = new ViewHolder(v);

        v.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.tvAddress.setText(addressBeans.get(position).getAddressName() + addressBeans.get(position).getAddress());
        holder.tvName.setText(addressBeans.get(position).getName());

        String phone = addressBeans.get(position).getMobile();

        holder.tvPhone.setText(phone + "");

        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context).setMessage("删除确认").setMessage("您确定要删除改地址嘛？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteAddress.delete(addressBeans.get(position).getAddressID());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        holder.modifyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteAddress.modify(position);
            }
        });

        radioButtons.add(holder.radioButton);

        if (addressBeans.get(position).isDefault()) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < radioButtons.size(); i++) {
                    if (radioButtons.get(i) == (RadioButton) v) {
                        ((RadioButton) v).setChecked(true);
                        onDeleteAddress.isDefault(position);
                    } else {
                        radioButtons.get(i).setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClick != null) {
            onItemClick.onItemClick((Integer) v.getTag());
        }
    }

    public void setOnItemclick(SetOnItemClickListener onItemclick) {
        this.onItemClick = onItemclick;
    }

    public interface SetOnItemClickListener {
        public void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAddress, tvName, tvPhone;
        private TextView deleteAddress, modifyAddress;
        private RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_goods_address);
            tvName = (TextView) itemView.findViewById(R.id.tv_goods_consignee_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_goods_consignee_phone);
            deleteAddress = (TextView) itemView.findViewById(R.id.tv_delete);
            modifyAddress = (TextView) itemView.findViewById(R.id.tv_modify);
            radioButton = (RadioButton) itemView.findViewById(R.id.rb_addaddress);
        }
    }

    public interface OnDeleteAddress {
        public void delete(String userAddressId);

        public void modify(int position);

        public void isDefault(int addressId);
    }
}
