package com.wlm.wlm.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.NumberFormat;

/**
 * Created by LG on 2019/8/30.
 */
public class PriceTextView extends AppCompatTextView {


    public PriceTextView(Context context) {
        super(context);
    }

    public PriceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PriceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        super.setText(numberFormat.format(Double.valueOf(text.toString())), type);

    }

}
