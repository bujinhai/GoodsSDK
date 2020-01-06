package com.jinshu.goodssdk;


import android.app.Application;

import com.jinshu.goodslibrary.entity.GBuilder;
import com.jinshu.settinglibrary.entity.Builder;
import com.jinshu.settinglibrary.utils.SDKUtils;

/**
 * Create on 2019/9/12 09:41 by bll
 */


public class AppApplication extends Application {

    private String siteID = "8a2f462a6aa17470016aa47bac8f0f32";
    private String applicationID = "8a2f462a6aa17470016aa47baa1c0f24";

    @Override
    public void onCreate() {
        super.onCreate();

        GBuilder.init(this).setSiteID(siteID).setApplicationID(applicationID).build();

        SDKUtils.init(new Builder(this).setSiteID(siteID).setApplicationID(applicationID).build());

    }
}
