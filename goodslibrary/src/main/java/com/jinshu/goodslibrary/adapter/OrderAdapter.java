package com.jinshu.goodslibrary.adapter;

import android.content.Context;
import android.text.SpannableString;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.entity.CartGoodsInfo;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GCommonRecycleViewAdapter;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GViewHolderHelper;
import com.jinshu.goodslibrary.utils.StrUtils;

/**
 * Create on 2019/11/14 10:14 by bll
 */


public class OrderAdapter extends GCommonRecycleViewAdapter<CartGoodsInfo> {

    public OrderAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(GViewHolderHelper helper, CartGoodsInfo info, int position) {
        helper.setImageUrl(R.id.iv_goods_img, info.getListImage());
        helper.setText(R.id.tv_goods_name, info.getGoodsName());
        String total = StrUtils.format(info.getPriceTotal());
        SpannableString price = StrUtils.changTVsize(total);
        helper.setText(R.id.tv_goods_price, "¥" + price);
        helper.setText(R.id.tv_goods_number, "x" + info.getQty());
    }
}
