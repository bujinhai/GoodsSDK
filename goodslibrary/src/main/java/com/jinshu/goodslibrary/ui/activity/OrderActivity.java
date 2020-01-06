package com.jinshu.goodslibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.adapter.OrderAdapter;
import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GApiService;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.base.GBaseActivity;
import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.basebean.GBaseResponse;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.entity.CartGoodsEntity;
import com.jinshu.goodslibrary.entity.CartGoodsInfo;
import com.jinshu.goodslibrary.entity.Configure;
import com.jinshu.goodslibrary.entity.GoodsDetailEntity;
import com.jinshu.goodslibrary.entity.GoodsOrderEntity;
import com.jinshu.goodslibrary.utils.BaseUtils;
import com.jinshu.goodslibrary.utils.ImageLoaderUtils;
import com.jinshu.goodslibrary.utils.JumpUtils;
import com.jinshu.goodslibrary.utils.StrUtils;
import com.jinshu.goodslibrary.utils.ToastUtil;
import com.jinshu.settinglibrary.activity.AddressActivity;
import com.jinshu.settinglibrary.activity.InvoiceListActivity;
import com.jinshu.settinglibrary.api.SApi;
import com.jinshu.settinglibrary.api.SHostType;
import com.jinshu.settinglibrary.app.SAppConstant;
import com.jinshu.settinglibrary.base.basebean.SBaseResponse;
import com.jinshu.settinglibrary.base.baserx.SRxHelper;
import com.jinshu.settinglibrary.base.baserx.SRxSchedulers;
import com.jinshu.settinglibrary.base.baserx.SRxSubscriber;
import com.jinshu.settinglibrary.entity.AddressDetailEntity;
import com.jinshu.settinglibrary.entity.InvoiceDetailEntity;
import com.jinshu.settinglibrary.utils.MasterUtils;
import com.jinshu.settinglibrary.utils.SystemUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create on 2019/11/13 10:46 by bll
 */


public class OrderActivity extends GBaseActivity implements View.OnClickListener {

    private Button btnAddress, btnSubmitOrder;
    private TextView tvDefault, tvSign, tvAddressName, tvDetailAddress, tvUserName, tvUserPhone;
    private RecyclerView mRv;
    private RelativeLayout rlInvoice, rlCoupon, rlFare;
    private TextView tvInvoice, tvCoupon, tvIntegral, tvGoodsAmount, tvFare, tvFinalPrice;
    private EditText etMessage;
    private LinearLayout llAddress;
    private RelativeLayout rlGoods;
    private ImageView iv_goods_img, ivBack;
    private TextView goodsName, tv_goods_price, tv_goods_number, tvTitle, tvFail;

    private OrderAdapter mAdapter;
    private double total = 0;
    private String orderType;
    private GoodsDetailEntity.DataInfo mGoodsInfo;
    private List<CartGoodsInfo> mCartInfos;
    private AddressDetailEntity.DataInfo addressInfo;
    private InvoiceDetailEntity.MemberInvoiceDefineDtoInfo invoiceInfo;
    private String memberAddressID;

    @Override
    public int getLayoutId() {
        return R.layout.g_activity_goods_order;
    }

    @Override
    public void initView(Intent intent, @Nullable Bundle savedInstanceState) {
        ivBack = findViewById(R.id.iv_left_image);
        tvTitle = findViewById(R.id.tv_title);
        llAddress = findViewById(R.id.ll_address);
        tvInvoice = findViewById(R.id.tv_receipt);
        btnAddress = findViewById(R.id.btn_select_address);
        btnSubmitOrder = findViewById(R.id.btn_submit_order);
        tvDefault = findViewById(R.id.tv_default);
        tvSign = findViewById(R.id.tv_sign);
        tvAddressName = findViewById(R.id.tv_address_name);
        tvDetailAddress = findViewById(R.id.tv_detail_address);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserPhone = findViewById(R.id.tv_user_phone);
        mRv = findViewById(R.id.recycler_view);
        rlInvoice = findViewById(R.id.rl_invoice);
        rlCoupon = findViewById(R.id.rl_coupon);
        rlFare = findViewById(R.id.rl_fare);
        tvCoupon = findViewById(R.id.tv_coupon);
        tvIntegral = findViewById(R.id.tv_integral);
        tvGoodsAmount = findViewById(R.id.tv_goods_amount);
        tvFare = findViewById(R.id.tv_fare);
        tvFinalPrice = findViewById(R.id.tv_goods_final_price);
        etMessage = findViewById(R.id.et_leave_message);

        rlGoods = findViewById(R.id.rl_goods);
        goodsName = findViewById(R.id.tv_goods_name);
        iv_goods_img = findViewById(R.id.iv_goods_img);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_number = findViewById(R.id.tv_goods_number);

        tvTitle.setText("填写订单");
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mAdapter = new OrderAdapter(this, R.layout.g_activity_adapter_item_order);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
        setListener();
    }

    @Override
    protected void onInitData(Intent intent, @Nullable Bundle savedInstanceState) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            orderType = bundle.getString(GAppConstant.ORDER_TYPE);
            String shopGoodsID = bundle.getString(GAppConstant.SHOP_GOODS_ID);
            String memberID = bundle.getString(GAppConstant.MEMBER_ID);
            if (Configure.FROM_GOODS_DETAIL.name().equals(orderType)) {
                getGoodsShopDetail(shopGoodsID, memberID);
                rlGoods.setVisibility(View.VISIBLE);
                mRv.setVisibility(View.GONE);
            } else if (Configure.FROM_CART.name().equals(orderType)) {
                getCartGoodsList();
                rlGoods.setVisibility(View.GONE);
                mRv.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getGoodsShopDetail(String shopGoodsID, String memberID) {
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
                        mGoodsInfo = entity.getData();
                        ImageLoaderUtils.display(mContext, iv_goods_img, entity.getData().getLargerImage());
                        goodsName.setText(entity.getData().getName());
                        tv_goods_number.setText("x1");
                        String price = StrUtils.format(entity.getData().getRealPrice());
                        SpannableString spannableString = StrUtils.changTVsize(price);
                        String amount = "¥" + spannableString;
                        tv_goods_price.setText(amount);
                        tvGoodsAmount.setText(amount);
                        tvFinalPrice.setText(amount);
                        tvIntegral.setText(String.valueOf(entity.getData().getPointPrice()));
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void getCartGoodsList() {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .getCartGoodsList(BaseUtils.getSessionID(), 1, 10000)
                .compose(GRxHelper.<CartGoodsEntity>handleResult())
                .compose(GRxSchedulers.<CartGoodsEntity>io_main())
                .subscribe(new GRxSubscriber<CartGoodsEntity>(mContext, false) {

                    @Override
                    protected void _onNext(CartGoodsEntity entity) {
                        if (entity.getData() == null || entity.getData().size() == 0) {
                            return;
                        }
                        mCartInfos = entity.getData().get(0).getGoodsList();
                        mAdapter.replaceAll(entity.getData().get(0).getGoodsList());

                        for (CartGoodsInfo goodsInfo : entity.getData().get(0).getGoodsList()) {
                            total = getTotalPrice(goodsInfo.getPriceTotal());
                        }
                        SpannableString spannableString = StrUtils.changTVsize(StrUtils.format(total));
                        String amount = "¥" + spannableString;
                        tvGoodsAmount.setText(amount);
                        tvFinalPrice.setText(amount);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private double getTotalPrice(double price) {
        total += price;
        return total;
    }

    private void setListener() {
        btnAddress.setOnClickListener(this);
        btnSubmitOrder.setOnClickListener(this);
        rlCoupon.setOnClickListener(this);
        rlInvoice.setOnClickListener(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_select_address) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(SAppConstant.IS_GOODS, true);
            JumpUtils.jumpActivityForResult(mActivity, AddressActivity.class, GAppConstant.GOODS_ADDRESS_CODE, bundle);
        } else if (v.getId() == R.id.rl_coupon) {

        } else if (v.getId() == R.id.rl_invoice) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(SAppConstant.IS_GOODS, true);
            JumpUtils.jumpActivityForResult(mActivity, InvoiceListActivity.class, GAppConstant.GOODS_INVOICE_CODE, bundle);
        } else if (v.getId() == R.id.btn_submit_order) {
            if (addressInfo == null) {
                ToastUtil.showShort("请选择收获地址");
                return;
            }

            if (Configure.FROM_GOODS_DETAIL.name().equals(orderType)) {
                createMemberOrder(1);
            } else if (Configure.FROM_CART.name().equals(orderType)) {
                createMemberOrder(2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case GAppConstant.GOODS_ADDRESS_CODE:
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    llAddress.setVisibility(View.VISIBLE);
                    btnAddress.setVisibility(View.GONE);
                    memberAddressID = bundle.getString(SAppConstant.MEMBER_ADDRESS_ID);
                    getAddressDetail(memberAddressID);
                }
                break;
            case GAppConstant.GOODS_INVOICE_CODE:
                Bundle args = data.getExtras();
                if (args != null) {
                    String memberInvoiceDefineID = args.getString(SAppConstant.MEMBER_INVOICE_DEFINE_ID);
                    getInvoiceDetail(memberInvoiceDefineID);
                }
                break;
        }
    }

    private void getAddressDetail(String memberAddressID) {
        SApi.getDefault(SHostType.BASE_URL)
                .getOneMemberAddressDetail(MasterUtils.addSessionID(), memberAddressID)
                .compose(SRxSchedulers.<SBaseResponse<AddressDetailEntity>>io_main())
                .subscribe(new SRxSubscriber<SBaseResponse<AddressDetailEntity>>(mContext) {
                    @Override
                    protected void onSuccess(SBaseResponse<AddressDetailEntity> baseResponse) {
                        if (baseResponse.header.code != 0) {
                            com.jinshu.settinglibrary.utils.ToastUtil.showShort(baseResponse.header.msg);
                            return;
                        }
                        addressInfo = baseResponse.body.getData();
                        if (addressInfo != null) {
                            tvUserName.setText(addressInfo.getContactName());
                            tvUserPhone.setText(addressInfo.getPhone());
                            tvDetailAddress.setText(addressInfo.getName());
                            String shengName = addressInfo.getShengName();
                            String shiName = com.jinshu.settinglibrary.utils.StrUtils.isEmpty(addressInfo.getShiName()) ? "" : addressInfo.getShiName();
                            String xianName = com.jinshu.settinglibrary.utils.StrUtils.isEmpty(addressInfo.getXianName()) ? "" : addressInfo.getXianName();
                            String zhenName = com.jinshu.settinglibrary.utils.StrUtils.isEmpty(addressInfo.getZhenName()) ? "" : addressInfo.getZhenName();
                            String area = shengName + shiName + xianName + zhenName;
                            tvAddressName.setText(area);
                            if (addressInfo.getIsDefault() == 1) {
                                tvDefault.setVisibility(View.VISIBLE);
                            } else {
                                tvDefault.setVisibility(View.GONE);
                            }

                            if (addressInfo.getAddType() == 1) {
                                tvSign.setVisibility(View.VISIBLE);
                                tvSign.setText("家");
                            } else if (addressInfo.getAddType() == 2) {
                                tvSign.setVisibility(View.VISIBLE);
                                tvSign.setText("公司");
                            } else if (addressInfo.getAddType() == 3) {
                                tvSign.setVisibility(View.VISIBLE);
                                tvSign.setText("学校");
                            } else {
                                tvSign.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    protected void onFail(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void getInvoiceDetail(String memberInvoiceDefineID) {
        SApi.getDefault(SHostType.BASE_URL)
                .getMemberInvoiceDefineDetail(MasterUtils.addSessionID(), memberInvoiceDefineID)
                .compose(SRxHelper.<InvoiceDetailEntity>handleResult())
                .compose(SRxSchedulers.<InvoiceDetailEntity>io_main())
                .subscribe(new SRxSubscriber<InvoiceDetailEntity>(mContext) {
                    @Override
                    protected void onSuccess(InvoiceDetailEntity entity) {
                        if (entity == null || entity.getMemberInvoiceDefineDto() == null) {
                            return;
                        }
                        invoiceInfo = entity.getMemberInvoiceDefineDto();
                        tvInvoice.setText(invoiceInfo.getName());
                    }

                    @Override
                    protected void onFail(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void createMemberOrder(int type) {
        GApiService service = GApi.getDefault(GHostType.ORDER_URL);
        Observable<GBaseResponse<GoodsOrderEntity>> observable;
        if (type == 1) {
            observable = service.createMemberOrder(BaseUtils.getSessionID(), type, mGoodsInfo.getId(), "",
                    memberAddressID, addressInfo.getContactName(), addressInfo.getName(),
                    addressInfo.getPhone(), tvIntegral.getText().toString(), invoiceInfo == null ? "" : invoiceInfo.getMemberInvoiceDefineID(),
                    GAppConstant.ALIPAY_PAYWAY_ID, "3", "2");
        } else {
            observable = service.createMemberOrder(BaseUtils.getSessionID(), type, "", mCartInfos.get(0).getCartID(),
                    memberAddressID, addressInfo.getContactName(), addressInfo.getName(),
                    addressInfo.getPhone(), tvIntegral.getText().toString(), invoiceInfo == null ? "" : invoiceInfo.getMemberInvoiceDefineID(),
                    GAppConstant.ALIPAY_PAYWAY_ID, "3", "2");
        }
        observable.compose(GRxHelper.<GoodsOrderEntity>handleResult())
                .compose(GRxSchedulers.<GoodsOrderEntity>io_main())
                .subscribe(new GRxSubscriber<GoodsOrderEntity>(mContext) {

                    @Override
                    protected void _onNext(GoodsOrderEntity entity) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(GAppConstant.GOODS_ORDER_INFO, entity);
                        SystemUtils.jumpActivity(mActivity, OrderPaymentActivity.class, bundle);

                        mRxManager.post(GAppConstant.HAS_CREATE_ORDER, "");
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }
}
