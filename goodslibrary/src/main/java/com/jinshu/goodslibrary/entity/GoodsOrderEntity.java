package com.jinshu.goodslibrary.entity;

import java.io.Serializable;

/**
 * Create on 2019/11/14 18:24 by bll
 */


public class GoodsOrderEntity implements Serializable{


    /**
     * amount : 1032
     * memberPaymentID : 8a2f462a6dc8fcb7016e7c4f0892009f
     * orderCode : D20191118101900024
     */

    private int amount;
    private String memberPaymentID;
    private String orderCode;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMemberPaymentID() {
        return memberPaymentID;
    }

    public void setMemberPaymentID(String memberPaymentID) {
        this.memberPaymentID = memberPaymentID;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
