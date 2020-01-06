package com.jinshu.goodslibrary.ui.pay.alipay;

/**
 * Create on 2019/11/19 17:26 by bll
 */


public interface IAliPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();

}
