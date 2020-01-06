package com.jinshu.goodslibrary.ui.pay.alipay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * Create on 2019/11/19 18:18 by bll
 */

public class PayAsyncTask extends AsyncTask<String, Void, Map<String, String>> {

    private final Activity ACTIVITY;
    private final IAliPayResultListener LISTENER;
    //订单支付成功
    private static final String ALI_PAY_STATUS_SUCCESS = "9000";
    //订单支付中
    private static final String ALI_PAY_STATUS_PAYING = "8000";
    //订单支付失败
    private static final String ALI_PAY_STATUS_FAIL = "4000";
    //用户取消
    private static final String ALI_PAY_STATUS_CANCEL = "6001";
    //连接错误
    private static final String ALI_PAY_STATUS_CONNECT_ERROR = "6002";

    public PayAsyncTask(Activity activity, IAliPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected Map<String, String> doInBackground(String... strings) {
        String aliPaySign = strings[0];
        PayTask payTask = new PayTask(ACTIVITY);
        return payTask.payV2(aliPaySign, true);
    }

    @Override
    protected void onPostExecute(Map<String, String> map) {
        super.onPostExecute(map);

        PayResult payResult = new PayResult(map);
        String resultInfo = payResult.getResult();
        String resultStatus = payResult.getResultStatus();

        switch (resultStatus) {
            case ALI_PAY_STATUS_SUCCESS:
                if (LISTENER != null) {
                    LISTENER.onPaySuccess();
                }
                break;
            case ALI_PAY_STATUS_FAIL:
                if (LISTENER != null) {
                    LISTENER.onPayFail();
                }
                break;
            case ALI_PAY_STATUS_PAYING:
                if (LISTENER != null) {
                    LISTENER.onPaying();
                }
                break;
            case ALI_PAY_STATUS_CANCEL:
                if (LISTENER != null) {
                    LISTENER.onPayCancel();
                }
                break;
            case ALI_PAY_STATUS_CONNECT_ERROR:
                if (LISTENER != null) {
                    LISTENER.onPayConnectError();
                }
                break;
        }

    }
}
