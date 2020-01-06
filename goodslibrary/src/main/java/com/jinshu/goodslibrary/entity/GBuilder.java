package com.jinshu.goodslibrary.entity;

import android.content.Context;

import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.baseapp.GBaseSdk;
import com.jinshu.goodslibrary.utils.SPUtils;


/**
 * Create on 2019/9/23 11:11 by bll
 */


public class GBuilder {

    public static GBuilder init(Context context) {
        GBaseSdk.setAppContext(context);
        return new GBuilder();
    }

    public GBuilder setSiteID(String siteID) {
        SPUtils.remove(GAppConstant.SITE_ID);
        SPUtils.setSharedStringData(GAppConstant.SITE_ID, siteID);
        return this;
    }

    public GBuilder setApplicationID(String applicationID) {
        SPUtils.remove(GAppConstant.APPLICATION_ID);
        SPUtils.setSharedStringData(GAppConstant.APPLICATION_ID, applicationID);
        return this;
    }

    public GBuilder build() {
        return this;
    }
}
