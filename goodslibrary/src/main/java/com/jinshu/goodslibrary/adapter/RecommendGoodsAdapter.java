package com.jinshu.goodslibrary.adapter;

import android.content.Context;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.entity.GoodsInfo;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GCommonRecycleViewAdapter;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GViewHolderHelper;


/**
 * reate on 2019/11/15 09:44 by bll
 */


public class RecommendGoodsAdapter extends GCommonRecycleViewAdapter<GoodsInfo> {


    public RecommendGoodsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(GViewHolderHelper helper, GoodsInfo info, int position) {
        helper.setText(R.id.tv_goods_name, info.getGoodsShopName());
        helper.setText(R.id.tv_goods_price, "¥" + info.getRealPrice());
        helper.setImageUrl(R.id.iv_goods_image, info.getLittleImage());
    }

}
