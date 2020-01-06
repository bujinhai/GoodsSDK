package com.jinshu.goodslibrary.wxapi;

import android.widget.Toast;

import com.jinshu.goodslibrary.ui.pay.wechatPay.BaseWXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;

/**
 * Create on 2019/11/20 19:27 by bll
 */


public class WXPayEntryActivity extends BaseWXPayEntryActivity {

    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
