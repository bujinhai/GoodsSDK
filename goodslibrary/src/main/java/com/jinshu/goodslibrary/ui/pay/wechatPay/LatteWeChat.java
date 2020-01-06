package com.jinshu.goodslibrary.ui.pay.wechatPay;

import android.content.Context;

import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.baseapp.GBaseSdk;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by bll on 2019/11/20
 */

public class LatteWeChat {
    public static final String APP_ID = GAppConstant.WE_CHAT_APP_ID;
    public static final String APP_SECRET = GAppConstant.WE_CHAT_APP_SECRET;

    private final IWXAPI WXAPI;

    private static final class Holder {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private LatteWeChat() {
        final Context context = GBaseSdk.getAppContext();
        WXAPI = WXAPIFactory.createWXAPI(context, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }


}
