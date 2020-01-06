package com.jinshu.goodslibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.base.GBaseActivity;
import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.entity.GoodsOrderEntity;
import com.jinshu.goodslibrary.ui.pay.alipay.FastPay;
import com.jinshu.goodslibrary.ui.pay.alipay.IAliPayResultListener;

/**
 * Create on 2019/11/18 10:35 by bll
 */


public class OrderPaymentActivity extends GBaseActivity implements View.OnClickListener, IAliPayResultListener {

    private TextView tvTitle, tvOrderCode, tvPrice;
    private ImageView ivBack;
    private RelativeLayout rlWechat, rlAlipay;
    private GoodsOrderEntity orderEntity;
    private String orderCode;
    private String memberOrderID;
    private Double orderPrice;

    @Override
    public int getLayoutId() {
        return R.layout.g_activity_order_payment;
    }

    @Override
    public void initView(Intent intent, @Nullable Bundle savedInstanceState) {
        ivBack = findViewById(R.id.iv_left_image);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("订单支付");
        tvOrderCode = findViewById(R.id.tv_order_code);
        tvPrice = findViewById(R.id.tv_goods_price);
        rlWechat = findViewById(R.id.rl_wechat_pay);
        rlAlipay = findViewById(R.id.rl_alipay);

        rlAlipay.setOnClickListener(this);
        rlWechat.setOnClickListener(this);

        setListener();
    }

    @Override
    protected void onInitData(Intent intent, @Nullable Bundle savedInstanceState) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            orderEntity = (GoodsOrderEntity) bundle.getSerializable(GAppConstant.GOODS_ORDER_INFO);
            orderCode = bundle.getString(GAppConstant.ORDER_CODE);
            orderPrice = bundle.getDouble(GAppConstant.ORDER_PRICE);
            memberOrderID = bundle.getString(GAppConstant.MEMBER_ORDER_ID);

            if (orderEntity != null) {
                tvOrderCode.setText(orderEntity.getOrderCode());
                String amount = "¥" + orderEntity.getAmount();
                tvPrice.setText(amount);
            } else {
                tvOrderCode.setText(orderCode);
                String amount = "¥" + orderPrice;
                tvPrice.setText(amount);
            }
        }
    }

    private void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_wechat_pay) {
            if (orderEntity != null) {//从订单页面进来
                getAppWeiXinPayInfo(orderEntity.getMemberPaymentID(), "");
            } else {//从订单列表进来
                getAppWeiXinPayInfo("", memberOrderID);
            }
        } else if (v.getId() == R.id.rl_alipay) {
            if (orderEntity != null) {//从订单页面进来
                getAppAliPayBody(orderEntity.getMemberPaymentID(), "");
            } else {//从订单列表进来
                getAppAliPayBody("", memberOrderID);
            }
        }
    }

    private void getAppAliPayBody(String memberPaymentID, String memberOrderID) {
        FastPay.create(mActivity)
                .setPayResultListener(OrderPaymentActivity.this)
                                .aliPay(memberPaymentID, memberOrderID);
    }

    private void getAppWeiXinPayInfo(String memberPaymentID, String memberOrderID) {
        FastPay.create(mActivity)
                .wechatPay(memberPaymentID, memberOrderID);
    }


    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
