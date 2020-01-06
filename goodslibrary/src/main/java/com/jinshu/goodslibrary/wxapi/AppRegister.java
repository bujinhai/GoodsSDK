package com.jinshu.goodslibrary.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Create on 2019/11/20 19:29 by bll
 */


public class AppRegister extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, GAppConstant.WE_CHAT_APP_ID, true);
        api.registerApp(GAppConstant.WE_CHAT_APP_ID);
    }
}
