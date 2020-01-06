package com.jinshu.goodslibrary.adapter;

import android.content.Context;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.entity.GoodsInfo;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GCommonRecycleViewAdapter;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GViewHolderHelper;

/**
 * Create on 2019/11/21 14:18 by bll
 */


public class HotGoodsAdapter extends GCommonRecycleViewAdapter<GoodsInfo> {
    public HotGoodsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(GViewHolderHelper helper, GoodsInfo goodsInfo, int position) {
        helper.setImageUrl(R.id.iv_goods_img, goodsInfo.getLittleImage());
        helper.setText(R.id.tv_goods_name, goodsInfo.getGoodsShopName());
        helper.setText(R.id.tv_goods_price, "¥" + goodsInfo.getRealPrice());
    }
}
