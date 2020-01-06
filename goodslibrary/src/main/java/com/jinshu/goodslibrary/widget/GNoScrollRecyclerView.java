package com.jinshu.goodslibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Create on 2019/10/8 15:54 by bll
 */


public class GNoScrollRecyclerView extends RecyclerView {

    public GNoScrollRecyclerView(Context context) {
        super(context);
    }

    public GNoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expendSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expendSpec);
    }
}
