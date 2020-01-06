package com.jinshu.goodslibrary.utils;


import com.jinshu.goodslibrary.baseapp.GAppConstant;

public class BaseUtils {


    public static String getSiteID() {
        String siteID = SPUtils.getSharedStringData(GAppConstant.SITE_ID);
        if (siteID != null) {
            return siteID;
        }
        return null;
    }

    public static String getApplicationID() {
        String applicationID = SPUtils.getSharedStringData(GAppConstant.APPLICATION_ID);
        if (applicationID != null) {
            return applicationID;
        }
        return null;
    }


    public static String getSessionID() {
        String sessionID = SPUtils.getSharedStringData(GAppConstant.SESSION_ID);
        if (sessionID != null) {
            return sessionID;
        }
        return null;
    }

    public static String getMemberID() {
        String memberID = SPUtils.getSharedStringData(GAppConstant.MEMBER_ID);
        if (memberID != null) {
            return memberID;
        }
        return null;
    }
}
