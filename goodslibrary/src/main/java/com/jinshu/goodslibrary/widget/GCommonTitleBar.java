package com.jinshu.goodslibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinshu.goodslibrary.R;

/**
 * Create on 2019/4/25 15:08 by bll
 */


public class GCommonTitleBar extends RelativeLayout {

    private ImageView ivBack, ivRightIcon;
    private TextView tvLeftText, tvTitle, tvRightText;
    private RelativeLayout rlCommonTitle;

    public GCommonTitleBar(Context context) {
        this(context, null);
    }

    public GCommonTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GCommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.g_layout_titlebar, this);
        ivBack = findViewById(R.id.iv_left_image);
        ivRightIcon = findViewById(R.id.iv_right_image);
        tvLeftText = findViewById(R.id.tv_left_text);
        tvTitle = findViewById(R.id.tv_title);
        tvRightText = findViewById(R.id.tv_right_text);
        rlCommonTitle = findViewById(R.id.common_title);
    }

    /**
     * 管理返回按钮
     */
    public void setBackVisibility(boolean visible) {
        if (visible) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    /**
     * 右图标
     */
    public void setRightImagVisibility(boolean visible) {
        ivRightIcon.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImagSrc(int id) {
        ivRightIcon.setVisibility(View.VISIBLE);
        ivRightIcon.setImageResource(id);
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        ivBack.setImageResource(id);
    }

    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRightText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTitle(String text) {
        tvRightText.setText(text);
    }

    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
        ivBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRightIcon.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRightText.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        rlCommonTitle.setBackgroundColor(color);
    }
}
