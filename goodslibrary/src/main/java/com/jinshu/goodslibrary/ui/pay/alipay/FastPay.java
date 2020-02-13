package com.jinshu.goodslibrary.ui.pay.alipay;

import android.app.Activity;

import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.ui.pay.entity.AliPayEntity;
import com.jinshu.goodslibrary.ui.pay.entity.WeChatEntity;
import com.jinshu.goodslibrary.ui.pay.wechatPay.LatteWeChat;
import com.jinshu.settinglibrary.utils.MasterUtils;
import com.jinshu.settinglibrary.utils.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Create on 2019/11/19 17:26 by bll
 */


public class FastPay {

    private IAliPayResultListener mIAliPayResultListener = null;
    private Activity mActivity = null;
    private final String body = "购买商品";

    public FastPay(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public static FastPay create(Activity activity) {
        return new FastPay(activity);
    }

    //支付宝
    public void aliPay(String memberPaymentID, String memberOrderID) {
        GApi.getDefault(GHostType.BASE_URL)
                .getAppAliPayBody(MasterUtils.addSessionID(), GAppConstant.ALIPAY_PAYWAY_ID, memberPaymentID, memberOrderID, body)
                .compose(GRxHelper.<AliPayEntity>handleResult())
                .compose(GRxSchedulers.<AliPayEntity>io_main())
                .subscribe(new GRxSubscriber<AliPayEntity>(mActivity) {
                    @Override
                    protected void _onNext(AliPayEntity aliPayEntity) {

//                        PayAsyncTask task = new PayAsyncTask(mActivity, mIAliPayResultListener);
//                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    //微信
    public void wechatPay(String memberPaymentID, String memberOrderID) {
        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        iwxapi.registerApp(GAppConstant.WE_CHAT_APP_ID);

        GApi.getDefault(GHostType.BASE_URL)
                .getAppWeixinPayInfo(MasterUtils.addSessionID(), GAppConstant.WECHAT_PAYWAY_ID, memberPaymentID, memberOrderID, body)
                .compose(GRxHelper.<WeChatEntity>handleResult())
                .compose(GRxSchedulers.<WeChatEntity>io_main())
                .subscribe(new GRxSubscriber<WeChatEntity>(mActivity) {
                    @Override
                    protected void _onNext(WeChatEntity weChatEntity) {
                        String prepayid = weChatEntity.getPrepayid();
                        String packageValue = weChatEntity.getPackageX();
                        String partnerid = weChatEntity.getPartnerid();
                        String timestamp = weChatEntity.getTimestamp();
                        String sign = weChatEntity.getSign();
                        String noncestr = weChatEntity.getNoncestr();

                        final PayReq payReq = new PayReq();
                        payReq.prepayId = prepayid;
                        payReq.packageValue = packageValue;
                        payReq.partnerId = partnerid;
                        payReq.timeStamp = timestamp;
                        payReq.sign = sign;
                        payReq.nonceStr = noncestr;

                        iwxapi.sendReq(payReq);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });


    }

    public FastPay setPayResultListener(IAliPayResultListener listener) {
        mIAliPayResultListener = listener;
        return this;
    }
}
