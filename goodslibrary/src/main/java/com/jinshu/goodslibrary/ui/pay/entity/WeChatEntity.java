package com.jinshu.goodslibrary.ui.pay.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Create on 2019/11/20 11:55 by bll
 */


public class WeChatEntity {


    /**
     * package : Sign=WXPay
     * appid : wx81bbd7f464a84491
     * sign : 351849C8B3DEE7B4EA5A0ED74E4AF68C
     * partnerid : 1367106502
     * prepayid : wx2015583152620943a458b3d61812683300
     * noncestr : 3690155831
     * timestamp : 1574236711
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
