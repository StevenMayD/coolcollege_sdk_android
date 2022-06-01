package com.coolcollege.aar.maincolor;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.coolcollege.aar.R;


public class TitleBar extends LinearLayout {

    ImageView ivLeft;
    TextView tvTitle;
    ImageView ivRight;
    TextView btmLine;
    TextView tvRight;
    LinearLayout llRoot;
    private View view;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);

        boolean isShowRight = ta.getBoolean(R.styleable.TitleBar_is_show_right_view, false);
        boolean isShowLeft = ta.getBoolean(R.styleable.TitleBar_is_show_left_view, false);
        boolean isShowBtmLine = ta.getBoolean(R.styleable.TitleBar_is_show_btm_line, false);
        Drawable leftPic = ta.getDrawable(R.styleable.TitleBar_left_pic);
        Drawable rightPic = ta.getDrawable(R.styleable.TitleBar_right_pic);
        String title = ta.getString(R.styleable.TitleBar_center_text);

        isShowLeft(isShowLeft);
        isShowRightPic(isShowRight);
        isShowBtmLine(isShowBtmLine);
        setLeftPic(leftPic);
        setRightPic(rightPic);
        setTitle(title);

        ta.recycle();
    }

    private void initView() {
        view = View.inflate(getContext(), R.layout.layout_title_bar, this);
        ivLeft = view.findViewById(R.id.iv_left);
        tvTitle = view.findViewById(R.id.tv_title);
        ivRight = view.findViewById(R.id.iv_right);
        btmLine = view.findViewById(R.id.view_line);
        tvRight = view.findViewById(R.id.tv_right);
        llRoot = view.findViewById(R.id.ll_root);
    }

    public void setTitle(CharSequence text) {
        if (!TextUtils.isEmpty(text)) tvTitle.setText(text);
    }

    public void setTitleColor(int colorId) {
        tvTitle.setTextColor(getContext().getResources().getColor(colorId));
    }

    public void setLeftPic(int picId) {
        if (picId > 0) {
            ivLeft.setImageResource(picId);
        }
    }

    public void setRightPic(int picId) {
        if (picId > 0) {
            ivRight.setImageResource(picId);
        }
    }

    public void setLeftPic(Drawable drawable) {
        if (drawable != null) {
            ivLeft.setImageDrawable(drawable);
        }
    }

    public void setRightPic(Drawable drawable) {
        if (drawable != null) {
            ivRight.setImageDrawable(drawable);
        }
    }

    public void setTitleRightPic(int picId) {
        if (picId > 0) {
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, picId, 0);
        }
    }

    public void setTitleRightPic(Drawable drawable) {
        if (drawable != null) {
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }


    public void setRightText(String text) {
        tvRight.setText(text);
    }

    public void setRightTextColor(int colorId) {
        tvRight.setTextColor(getContext().getResources().getColor(colorId));
    }

    public void setRootViewBg(int colorId) {
        llRoot.setBackgroundColor(getContext().getResources().getColor(colorId));
    }

    public void isShowRightPic(boolean isShow) {
        if (isShow) {
            ivRight.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.GONE);
        } else {
            ivRight.setVisibility(View.INVISIBLE);
        }
    }

    public void isShowRightText(boolean isShow) {
        if (isShow) {
            ivRight.setVisibility(View.GONE);
            tvRight.setVisibility(View.VISIBLE);
        } else {
            ivRight.setVisibility(View.INVISIBLE);
            tvRight.setVisibility(View.GONE);
        }
    }


    public void isShowLeft(boolean isShow) {
        if (isShow) {
            ivLeft.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setVisibility(View.INVISIBLE);
        }
    }

    public void isShowBtmLine(boolean isShow) {
        if (isShow) {
            btmLine.setVisibility(View.VISIBLE);
        } else {
            btmLine.setVisibility(View.GONE);
        }
    }

    public void setTitleBarBg(int colorId) {
        llRoot.setBackgroundColor(getContext().getResources().getColor(colorId));
    }

    public void setBackgroundMainColor() {
        int mainColor = getResources().getColor(R.color.main_color);
        llRoot.setBackgroundColor(mainColor);
    }

    public void setBackgroundMainColor(int color) {
        llRoot.setBackgroundColor(color);
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        ivLeft.setOnClickListener(listener);
    }

    public void setOnRightPicClickListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextClickListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    public void setOnTitleClickListener(OnClickListener listener) {
        tvTitle.setOnClickListener(listener);
    }


    public void setOnTitleBarClickListener(OnClickListener listener) {
        view.setOnClickListener(listener);
    }
}
