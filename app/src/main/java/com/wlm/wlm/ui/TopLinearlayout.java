package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlm.wlm.R;
import com.wlm.wlm.interf.IGoodsTypeListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by LG on 2019/8/31.
 */
public class TopLinearlayout extends LinearLayout implements View.OnClickListener {
    private Context context;
    private TextView tx_moren,tx_host,tx_price,tx_top;
    private LinearLayout ll_top;
    private RelativeLayout rl_host;
    private ImageView iv_host_arrow;
    private ArrayList<TextView> textViewList = new ArrayList<>();
    private ImageView img1,img2,img3,img4;
    private boolean isView3 = false;
    private boolean isView4 = false;
    private int arrow_up;
    private int arrow_down;
    private int textcolor;
    private int textnormalcolor;
    private int type = 1;
    private IGoodsTypeListener iGoodsTypeListener = null;


    public TopLinearlayout(Context context) {
        super(context);
    }

    public TopLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public TopLinearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs){

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TopLayout);
        textcolor = a.getColor(R.styleable.TopLayout_textcolor,getResources().getColor(R.color.main_app_color));
        textnormalcolor = a.getColor(R.styleable.TopLayout_textnormalcolor,getResources().getColor(R.color.text_question));
        arrow_up = a.getResourceId(R.styleable.TopLayout_up_arrow,R.mipmap.ic_store_dot_up);
        arrow_down = a.getResourceId(R.styleable.TopLayout_down_arrow,R.mipmap.ic_store_dot_down);
        type = a.getInteger(R.styleable.TopLayout_type,1);


        View view = LayoutInflater.from(context).inflate(R.layout.layout_top,this,false);

        tx_moren = view.findViewById(R.id.tx_moren);
        tx_host = view.findViewById(R.id.tx_host);
        tx_price = view.findViewById(R.id.tx_price);
        tx_top = view.findViewById(R.id.tx_top);
        ll_top = view.findViewById(R.id.ll_top);
        rl_host = view.findViewById(R.id.rl_host);
        iv_host_arrow = view.findViewById(R.id.iv_host_arrow);

        tx_moren.setOnClickListener(this);
        rl_host.setOnClickListener(this);
        tx_price.setOnClickListener(this);
        tx_top.setOnClickListener(this);

        textViewList.add(tx_moren);
        textViewList.add(tx_host);
        textViewList.add(tx_price);
        textViewList.add(tx_top);

        if (type == 2){
            tx_host.setText(getResources().getString(R.string.groupon_all));
            iv_host_arrow.setVisibility(VISIBLE);
        }else if (type == 3){
            rl_host.setVisibility(GONE);
        }else if (type == 4){
            tx_host.setText(getResources().getString(R.string.search_all_mall));
            iv_host_arrow.setVisibility(VISIBLE);
        }

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);

        addView(view);

        bottomLine(tx_moren);

    }

    public void setText(String str){
        tx_host.setText(str);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tx_moren:

                bottomLine(tx_moren);
                iGoodsTypeListener.getSortType(1);

                break;
            case R.id.rl_host:

                bottomLine(tx_host);
                iGoodsTypeListener.getSortType(2);

                break;
            case R.id.tx_price:

                bottomLine(tx_price);

                break;
            case R.id.tx_top:

                bottomLine(tx_top);

                break;
        }
    }

    private void bottomLine(TextView paramView) {

        if (tx_top == paramView) {
            if (isView3) {
                img1.setImageResource(R.mipmap.j_1);
                img2.setImageResource(arrow_down);
                isView3 = !isView3;

                iGoodsTypeListener.getSortType(4);
            } else {
                img1.setImageResource(arrow_up);
                img2.setImageResource(R.mipmap.j_2);
                isView3 = !isView3;

                iGoodsTypeListener.getSortType(3);
            }
        } else {
            img1.setImageResource(R.mipmap.j_1);
            img2.setImageResource(R.mipmap.j_2);
            isView3 = false;
        }

        if (tx_price == paramView) {
            if (isView4) {
                img3.setImageResource(R.mipmap.j_1);
                img4.setImageResource(arrow_down);
                isView4 = !isView4;
                iGoodsTypeListener.getSortType(6);
            } else {
                img3.setImageResource(arrow_up);
                img4.setImageResource(R.mipmap.j_2);
                isView4 = !isView4;
                iGoodsTypeListener.getSortType(5);
            }
        } else {
            img3.setImageResource(R.mipmap.j_1);
            img4.setImageResource(R.mipmap.j_2);
            isView4 = false;
        }

        Object textObject = textViewList.iterator();
        while (((Iterator) textObject).hasNext()) {
            TextView itemView = (TextView) ((Iterator) textObject).next();
            if (itemView == paramView) {
                itemView.setTextColor(textcolor);
            } else {
                itemView.setTextColor(textnormalcolor);
            }
        }

    }

    public void setListener(IGoodsTypeListener listener){
        iGoodsTypeListener = listener;
    }
}
