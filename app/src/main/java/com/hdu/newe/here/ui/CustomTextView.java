package com.hdu.newe.here.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * 自定义 TextView
 */
public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setIncludeFontPadding(false);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setIncludeFontPadding(false);
    }

    public void setDrawableTop(Context context, Integer res) {
        Drawable drawable = context.getDrawable(res);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, drawable, null, null);
        }
    }
}