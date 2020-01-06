package com.jinshu.goodslibrary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.adapter.AttachmentAdapter;
import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.base.GBaseActivity;
import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.basebean.GBaseResponse;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.entity.CartGoodsNumberEntity;
import com.jinshu.goodslibrary.entity.CollectEntity;
import com.jinshu.goodslibrary.entity.Configure;
import com.jinshu.goodslibrary.entity.GoodsAttachEntity;
import com.jinshu.goodslibrary.entity.GoodsAttachInfo;
import com.jinshu.goodslibrary.entity.GoodsDetailEntity;
import com.jinshu.goodslibrary.utils.BaseUtils;
import com.jinshu.goodslibrary.utils.GlideImageLoader;
import com.jinshu.goodslibrary.utils.JumpUtils;
import com.jinshu.goodslibrary.utils.StrUtils;
import com.jinshu.goodslibrary.utils.ToastUtil;
import com.jinshu.goodslibrary.widget.GNoScrollRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Create on 2019-11-02 17:23 by bll
 */

public class GoodsDetailActivity extends GBaseActivity implements View.OnClickListener {

    private ImageView ivBack, ivRight, ivCollection;
    private TextView goodsDescribe, goodsPrice, goodsName, tipNum, tvTitle, tvCollection;
    private LinearLayout llCollection, llAddCart, llBuy;
    private RelativeLayout llCart;
    private String shopGoodsID, memberID;
    private Banner mBanner;
    private static OnShopCartClickListener mListener;
    private boolean isCollect = false;
    private String collectID;
    private String objectDefineID = "8a2f462a626bdc5f01626bf9a87800bc";
    private List<String> images;

    private GoodsDetailEntity.DataInfo mDataInfo;
    private AttachmentAdapter mAdapter;
    private GNoScrollRecyclerView mRecycler;

    @Override
    public int getLayoutId() {
        return R.layout.g_activity_goods_detail;
    }

    public static void jumpActivity(Activity context, String shopGoodsID, String memberID) {
        Bundle bundle = new Bundle();
        bundle.putString(GAppConstant.SHOP_GOODS_ID, shopGoodsID);
        bundle.putString(GAppConstant.MEMBER_ID, memberID);
        JumpUtils.jumpActivity(context, GoodsDetailActivity.class, bundle);
    }

    @Override
    public void initView(Intent intent, @Nullable Bundle savedInstanceState) {
        ivBack = findViewById(R.id.iv_left_image);
        tvTitle = findViewById(R.id.tv_title);
        ivRight = findViewById(R.id.iv_right_image);
        mBanner = findViewById(R.id.banner);
        goodsDescribe = findViewById(R.id.tv_goods_describe);
        goodsPrice = findViewById(R.id.tv_goods_price);
        goodsName = findViewById(R.id.tv_goods_name);
        tipNum = findViewById(R.id.tv_tip_num);
        llCollection = findViewById(R.id.layout_collection);
        ivCollection = findViewById(R.id.iv_collection);
        tvCollection = findViewById(R.id.tv_collection);
        llAddCart = findViewById(R.id.layout_add_cart);
        llCart = findViewById(R.id.layout_cart);
        llBuy = findViewById(R.id.layout_buy);
        mRecycler = findViewById(R.id.recycler_view);
        images = new ArrayList<>();
        mAdapter = new AttachmentAdapter(mActivity, R.layout.g_activity_adapter_item_goods_attachment);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mRecycler.setHasFixedSize(true);
        mRecycler.setNestedScrollingEnabled(false);

        llCart.setOnClickListener(this);
        llCollection.setOnClickListener(this);
        llBuy.setOnClickListener(this);
        llAddCart.setOnClickListener(this);

        tvTitle.setText("商品详情");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onInitData(Intent intent, @Nullable Bundle savedInstanceState) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            shopGoodsID = bundle.getString(GAppConstant.SHOP_GOODS_ID);
            memberID = bundle.getString(GAppConstant.MEMBER_ID);
        }

        getGoodsShopDetail();
        getMemberCartGoodsNum();
    }

    private void getGoodsShopDetail() {
        GApi.getDefault(GHostType.HOST_URL)
                .getGoodsShopDetail(shopGoodsID, "2", memberID)
                .compose(GRxHelper.<GoodsDetailEntity>handleResult())
                .compose(GRxSchedulers.<GoodsDetailEntity>io_main())
                .subscribe(new GRxSubscriber<GoodsDetailEntity>(mContext) {
                    @Override
                    protected void _onNext(GoodsDetailEntity entity) {
                        if (entity.getData() == null) {
                            return;
                        }
                        mDataInfo = entity.getData();
                        goodsName.setText(mDataInfo.getName());
                        if (StrUtils.isNotEmpty(mDataInfo.getDescription())) {
                            goodsDescribe.setText(mDataInfo.getDescription());
                        } else {
                            goodsDescribe.setVisibility(View.GONE);
                        }

                        if (entity.getData().getRealPrice() == 0) {
                            String realPrice = "¥" + String.valueOf(mDataInfo.getRealPrice())
                                    + " + 积分：" + String.valueOf(mDataInfo.getPointPrice());
                            goodsPrice.setText(realPrice);
                        } else {
                            String price = StrUtils.format(mDataInfo.getRealPrice());
                            SpannableString spannableString = StrUtils.changTVsize(price);
                            String realPrice = "¥" + spannableString + " + 积分：" + String.valueOf(mDataInfo.getPointPrice());
                            goodsPrice.setText(realPrice);
                        }
                        getGoodsAttach();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void getGoodsAttach() {
        GApi.getDefault(GHostType.BASE_URL)
                .getObjectAttachmentList(BaseUtils.getSessionID(), objectDefineID, mDataInfo.getId(), "1")
                .compose(GRxHelper.<GoodsAttachEntity>handleResult())
                .compose(GRxSchedulers.<GoodsAttachEntity>io_main())
                .subscribe(new GRxSubscriber<GoodsAttachEntity>(mContext, false) {
                    @Override
                    protected void _onNext(GoodsAttachEntity goodsAttachEntity) {
                        if (goodsAttachEntity.getData() == null || goodsAttachEntity.getData().getRows() == null) {
                            return;
                        }
                        mAdapter.replaceAll(goodsAttachEntity.getData().getRows());

                        initBanner(goodsAttachEntity.getData().getRows());
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void initBanner(List<GoodsAttachInfo> infoList) {
        for (GoodsAttachInfo info : infoList) {
            images.add(info.getUrl());
        }

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CENTER);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_collection) {
            if (!isCollect) {
                submitOneCollect();
                isCollect = true;
            } else {
                removeOneCollect();
                isCollect = false;
            }
        } else if (v.getId() == R.id.layout_cart) {
            if (mListener != null) {
                mListener.onShopCartClick();
            }
        } else if (v.getId() == R.id.layout_add_cart) {
            addGoodsShopToCart();
        } else if (v.getId() == R.id.layout_buy) {
            Bundle bundle = new Bundle();
            bundle.putString(GAppConstant.ORDER_TYPE, Configure.FROM_GOODS_DETAIL.name());
            bundle.putString(GAppConstant.SHOP_GOODS_ID, shopGoodsID);
            bundle.putString(GAppConstant.MEMBER_ID, memberID);
            JumpUtils.jumpActivity(mActivity, OrderActivity.class, bundle);
        }
    }

    private void addGoodsShopToCart() {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .addGoodsShopToCart(BaseUtils.getSessionID(), shopGoodsID)
                .compose(GRxSchedulers.<GBaseResponse>io_main())
                .subscribe(new GRxSubscriber<GBaseResponse>(mContext, false) {
                    @Override
                    protected void _onNext(GBaseResponse baseResponse) {
                        if (baseResponse.header.code != 0) {
                            ToastUtil.showShort(baseResponse.header.msg);
                            return;
                        }
                        getMemberCartGoodsNum();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void getMemberCartGoodsNum() {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .getMemberCartGoodsNum(BaseUtils.getSessionID())
                .compose(GRxHelper.<CartGoodsNumberEntity>handleResult())
                .compose(GRxSchedulers.<CartGoodsNumberEntity>io_main())
                .subscribe(new GRxSubscriber<CartGoodsNumberEntity>(mContext, false) {
                    @Override
                    protected void _onNext(CartGoodsNumberEntity entity) {
                        tipNum.setVisibility(View.VISIBLE);
                        tipNum.setText(entity.getNum());
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    public interface OnShopCartClickListener {
        void onShopCartClick();
    }

    public static void setOnShopCartClickListener(OnShopCartClickListener listener) {
        mListener = listener;
    }

    private void submitOneCollect() {
        GApi.getDefault(GHostType.BASE_URL)
                .submitOneCollect(BaseUtils.getSessionID(), objectDefineID, mDataInfo.getId(), mDataInfo.getName())
                .compose(GRxHelper.<CollectEntity>handleResult())
                .compose(GRxSchedulers.<CollectEntity>io_main())
                .subscribe(new GRxSubscriber<CollectEntity>(mContext, false) {
                    @Override
                    protected void _onNext(CollectEntity collectEntity) {
                        collectID = collectEntity.getCollectID();
                        ivCollection.setImageResource(R.drawable.g_collection);
                        tvCollection.setText("已收藏");
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void removeOneCollect() {
        GApi.getDefault(GHostType.BASE_URL)
                .deleteOneCollect(BaseUtils.getSessionID(), collectID)
                .compose(GRxSchedulers.<GBaseResponse>io_main())
                .subscribe(new GRxSubscriber<GBaseResponse>(mContext, false) {
                    @Override
                    protected void _onNext(GBaseResponse response) {
                        if (!response.success()) {
                            ToastUtil.showShort(response.header.msg);
                        }
                        ivCollection.setImageResource(R.drawable.g_no_collection);
                        tvCollection.setText("收藏");
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    /**
     * 轮播图，以下方法必须要写
     */
    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    /**
     * 轮播图，以下方法必须要写
     */
    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }
}
