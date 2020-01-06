package com.jinshu.goodslibrary.adapter;

import android.content.Context;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.entity.GoodsAttachInfo;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GCommonRecycleViewAdapter;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GViewHolderHelper;

/**
 * Create on 2019/11/15 16:10 by bll
 */


public class AttachmentAdapter extends GCommonRecycleViewAdapter<GoodsAttachInfo> {

    public AttachmentAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(GViewHolderHelper helper, GoodsAttachInfo goodsAttachInfo, int position) {
        helper.setImageUrl(R.id.iv_goods_attachment, goodsAttachInfo.getUrl());
    }
}
