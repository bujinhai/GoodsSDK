package com.jinshu.goodslibrary.adapter;

import android.content.Context;
import android.view.View;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.entity.CartGoodsInfo;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GCommonRecycleViewAdapter;
import com.jinshu.goodslibrary.recyclerview.baseadapter.GViewHolderHelper;
import com.jinshu.goodslibrary.utils.StrUtils;


/**
 * Create on 2019/10/8 10:56 by bll
 */


public class ShoppingCartAdapter extends GCommonRecycleViewAdapter<CartGoodsInfo> {

    public ShoppingCartAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(GViewHolderHelper helper, final CartGoodsInfo info, int position) {
        if (info.getIsSelected() == 1) {
            helper.setBackgroundRes(R.id.iv_select, R.drawable.g_select);
        } else if (info.getIsSelected() == 2) {
            helper.setBackgroundRes(R.id.iv_select, R.drawable.g_unselect);
        }
        helper.setImageUrl(R.id.iv_goods_img, info.getListImage());
        helper.setText(R.id.tv_goods_name, info.getGoodsName());
        helper.setText(R.id.tv_goods_price, StrUtils.format(info.getPriceTotal()));
        helper.setText(R.id.tv_goods_count, info.getQty() + "");

        helper.setOnClickListener(R.id.iv_select, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelect(info);
                }
            }
        });

        helper.setOnClickListener(R.id.iv_goods_minus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMinus(info);
                }
            }
        });

        helper.setOnClickListener(R.id.iv_goods_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAdd(info);
                }
            }
        });

        helper.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDelete(info);
                }
            }
        });
    }

    private OnCartItemClickListener mListener;

    public interface OnCartItemClickListener {
        void onSelect(CartGoodsInfo info);

        void onAdd(CartGoodsInfo info);

        void onMinus(CartGoodsInfo info);

        void onDelete(CartGoodsInfo info);
    }

    public void setOnCartItemClickListener(OnCartItemClickListener listener) {
        mListener = listener;
    }
}
