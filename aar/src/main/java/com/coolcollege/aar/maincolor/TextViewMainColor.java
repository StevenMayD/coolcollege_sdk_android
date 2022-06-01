package com.coolcollege.aar.maincolor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import com.coolcollege.aar.R;

/**
 * Created by Evan_for on 2020/6/29
 */
public class TextViewMainColor extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewMainColor(Context context) {
        super(context);
    }

    public TextViewMainColor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewMainColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setTextColor(int color) {
        if (color == getResources().getColor(R.color.main_color)) {
            int mainColor = getResources().getColor(R.color.main_color);
            if (mainColor != 0) {
                super.setTextColor(mainColor);
            }else {
                super.setTextColor(color);
            }
        } else {
            super.setTextColor(color);
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        int currentTextColor = getCurrentTextColor();
        if (currentTextColor == getResources().getColor(R.color.main_color)) {
            int mainColor = getResources().getColor(R.color.main_color);
            if (mainColor != 0) {
                super.setTextColor(mainColor);
            }
        }
    }
}
