package com.wlm.wlm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlm.wlm.R;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.OnClick;

/**
 * Created by LG on 2019/8/15.
 */

public class CustomSortLayout extends LinearLayout implements View.OnClickListener{

    private Context context;
    private int textcolor;
    private int textnormalcolor;
    private int arrow_up;
    private int arrow_down;

    private TextView tx_moren,tx_host,tx_price,tx_top;
    private ImageView img1,img2,img3,img4;
    private RecyclerView recyclerView;
    private boolean isView3 = false;
    private boolean isView4 = false;
    private ArrayList<TextView> textViewList = new ArrayList<>();

    public CustomSortLayout(Context context) {
        super(context);
    }

    public CustomSortLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomSortLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context,AttributeSet attrs){

        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomSort);
        textcolor = a.getColor(R.styleable.CustomSort_textcolor,getResources().getColor(R.color.main_app_color));
        textnormalcolor = a.getColor(R.styleable.CustomSort_textnormalcolor,getResources().getColor(R.color.text_question));
        arrow_up = a.getResourceId(R.styleable.CustomSort_up_arrow,R.mipmap.ic_store_dot_up);
        arrow_down = a.getResourceId(R.styleable.CustomSort_down_arrow,R.mipmap.ic_store_dot_down);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_sort,null);

        tx_moren = view.findViewById(R.id.tx_moren);
        tx_host = view.findViewById(R.id.tx_host);
        tx_price = view.findViewById(R.id.tx_price);
        tx_top = view.findViewById(R.id.tx_top);

        tx_moren.setOnClickListener(this);
        tx_host.setOnClickListener(this);
        tx_price.setOnClickListener(this);
        tx_top.setOnClickListener(this);

        textViewList.add(tx_moren);
        textViewList.add(tx_host);
        textViewList.add(tx_price);
        textViewList.add(tx_top);

        recyclerView = view.findViewById(R.id.rv_goods);

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);


        addView(view);

    }

    /**
     * 界面点击typeid
     * @param position
     */
    public void setType(int position){

    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.tx_moren:

                bottomLine(tx_moren);

                break;
            case R.id.tx_host:

                bottomLine(tx_host);

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
            } else {
                img1.setImageResource(arrow_up);
                img2.setImageResource(R.mipmap.j_2);
                isView3 = !isView3;
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
            } else {
                img3.setImageResource(arrow_up);
                img4.setImageResource(R.mipmap.j_2);
                isView4 = !isView4;
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



}
