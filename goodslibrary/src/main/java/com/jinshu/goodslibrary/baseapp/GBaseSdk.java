package com.jinshu.goodslibrary.baseapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.jinshu.goodslibrary.utils.SPUtils;

/**
 * Create on 2019/9/30 14:25 by bll
 */


public class GBaseSdk {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static Activity mActivity;

    public static void setAppContext(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static void setActivity(Activity activity) {
        mActivity = activity;
    }

    public static Activity getActivity() {
        return mActivity;
    }

    public static void setSessionID(String sessionID) {
        SPUtils.remove(GAppConstant.SESSION_ID);
        SPUtils.setSharedStringData(GAppConstant.SESSION_ID, sessionID);
    }

    public static void setMemberID(String memberID) {
        SPUtils.remove(GAppConstant.MEMBER_ID);
        SPUtils.setSharedStringData(GAppConstant.MEMBER_ID, memberID);
    }

}
