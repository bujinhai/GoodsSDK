package com.jinshu.goodslibrary.recyclerview.baseadapter;

import android.content.Context;
import android.view.ViewGroup;

public abstract class GMultiItemRecycleViewAdapter<T> extends GCommonRecycleViewAdapter<T> {

    protected GMultiItemTypeSupport<T> mMultiItemTypeSupport;

    public GMultiItemRecycleViewAdapter(Context context, GMultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;

        if (mMultiItemTypeSupport == null)
            throw new IllegalArgumentException("the mMultiItemTypeSupport can not be null.");
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
        return super.getItemViewType(position);
    }

    @Override
    public GViewHolderHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiItemTypeSupport == null) return super.onCreateViewHolder(parent, viewType);

        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        GViewHolderHelper holder = GViewHolderHelper.get(mContext, null, parent, layoutId, -1);
//        setListener(parent, holder, viewType);
        return holder;
    }

}
