package com.wlm.wlm.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.interf.IOrderChoosePayTypeListener;
import com.wlm.wlm.util.UToast;

/**
 * Created by LG on 2019/8/30.
 */
public class OrderPopupLayout extends PopupWindow {

    private Context context;

    private RelativeLayout rl_self, rl_wx;
    private TextView tv_price, tv_pay_self, tv_right_now_pay;
    private CountdownView tv_time;
    private IOrderChoosePayTypeListener iOrderChoosePayTypeListener;

    public OrderPopupLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void setListener(IOrderChoosePayTypeListener iOrderChoosePayTypeListener) {
        this.iOrderChoosePayTypeListener = iOrderChoosePayTypeListener;
    }

    public OrderPopupLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public OrderPopupLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.popup_order, null);

        ImageView imageView = view.findViewById(R.id.iv_right_delete);
        final CheckBox check_self = view.findViewById(R.id.check_self);
        final CheckBox check_wx = view.findViewById(R.id.check_wx);
        rl_self = view.findViewById(R.id.rl_self);
        rl_wx = view.findViewById(R.id.rl_wx);
        tv_price = view.findViewById(R.id.tv_price);
        tv_pay_self = view.findViewById(R.id.tv_pay_self);
        tv_time = view.findViewById(R.id.tv_time);
        tv_right_now_pay = view.findViewById(R.id.tv_right_now_pay);
        tv_right_now_pay.setText(context.getString(R.string.modify_sure));

        /*if(OrderActivity.pay_type_position == 1){
            check_self.setChecked(false);
            check_wx.setChecked(true);
        }else if(OrderActivity.pay_type_position == 2){
            check_self.setChecked(true);
            check_wx.setChecked(false);
        }*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        rl_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_self.setChecked(true);
                check_wx.setChecked(false);
            }
        });

        rl_wx.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                check_wx.setChecked(true);
                check_self.setChecked(false);
            }
        });

//
//        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT));
        tv_right_now_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_self.isChecked()) {
                    dismiss();
                    iOrderChoosePayTypeListener.chooseType(2);

                } else if (check_wx.isChecked()) {
                    dismiss();
                    iOrderChoosePayTypeListener.chooseType(1);
                } else {
                    UToast.show(context, "请选择支付方式");
                }
            }
        });


        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

    }


}
