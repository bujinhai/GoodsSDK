package com.jinshu.goodslibrary.entity;

/**
 * Create on 2019-11-09 16:23 by bll
 */

public class GoodsDetailEntity {


    /**
     * data : {"id":"8a2f462a6ad58b08016ad59cceb8001a","goodsID":null,"shopID":"8a2f462a6c60db4e016c60e23cf60016","shopName":"氧寿旗舰店","name":"百岁友美容酒（旗袍）","showName":"百岁友美容酒（旗袍）","littleImage":"http://wj.haoju.me/6bc92aa9e9b74ac185dba4dd8c601047.jpg","largerImage":"http://wj.haoju.me/8c239dd0cd1d4e359356484c0aa19d09.jpg","priceUnit":"元","getPrice":450,"standPrice":1288,"showPrice":1288,"realPrice":644,"salesNumber":1111,"salesTotal":0,"stockNumber":0,"stockOut":0,"deliveryFee":0,"deliveryFeeRate":0,"description":"百岁友美容酒（旗袍）","termCode":"YS000001","collectID":null,"salesType":1,"pointPrice":644}
     */

    private DataInfo data;

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public static class DataInfo {
        /**
         * id : 8a2f462a6ad58b08016ad59cceb8001a
         * goodsID : null
         * shopID : 8a2f462a6c60db4e016c60e23cf60016
         * shopName : 氧寿旗舰店
         * name : 百岁友美容酒（旗袍）
         * showName : 百岁友美容酒（旗袍）
         * littleImage : http://wj.haoju.me/6bc92aa9e9b74ac185dba4dd8c601047.jpg
         * largerImage : http://wj.haoju.me/8c239dd0cd1d4e359356484c0aa19d09.jpg
         * priceUnit : 元
         * getPrice : 450
         * standPrice : 1288
         * showPrice : 1288
         * realPrice : 644
         * salesNumber : 1111
         * salesTotal : 0
         * stockNumber : 0
         * stockOut : 0
         * deliveryFee : 0
         * deliveryFeeRate : 0
         * description : 百岁友美容酒（旗袍）
         * termCode : YS000001
         * collectID : null
         * salesType : 1
         * pointPrice : 644
         */

        private String id;
        private String goodsID;
        private String shopID;
        private String shopName;
        private String name;
        private String showName;
        private String littleImage;
        private String largerImage;
        private String priceUnit;
        private double getPrice;
        private double standPrice;
        private double showPrice;
        private double realPrice;
        private int salesNumber;
        private int salesTotal;
        private int stockNumber;
        private int stockOut;
        private int deliveryFee;
        private int deliveryFeeRate;
        private String description;
        private String termCode;
        private String collectID;
        private int salesType;
        private int pointPrice;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsID() {
            return goodsID;
        }

        public void setGoodsID(String goodsID) {
            this.goodsID = goodsID;
        }

        public String getShopID() {
            return shopID;
        }

        public void setShopID(String shopID) {
            this.shopID = shopID;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getLittleImage() {
            return littleImage;
        }

        public void setLittleImage(String littleImage) {
            this.littleImage = littleImage;
        }

        public String getLargerImage() {
            return largerImage;
        }

        public void setLargerImage(String largerImage) {
            this.largerImage = largerImage;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public double getGetPrice() {
            return getPrice;
        }

        public void setGetPrice(double getPrice) {
            this.getPrice = getPrice;
        }

        public double getStandPrice() {
            return standPrice;
        }

        public void setStandPrice(double standPrice) {
            this.standPrice = standPrice;
        }

        public double getShowPrice() {
            return showPrice;
        }

        public void setShowPrice(int showPrice) {
            this.showPrice = showPrice;
        }

        public double getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(double realPrice) {
            this.realPrice = realPrice;
        }

        public int getSalesNumber() {
            return salesNumber;
        }

        public void setSalesNumber(int salesNumber) {
            this.salesNumber = salesNumber;
        }

        public int getSalesTotal() {
            return salesTotal;
        }

        public void setSalesTotal(int salesTotal) {
            this.salesTotal = salesTotal;
        }

        public int getStockNumber() {
            return stockNumber;
        }

        public void setStockNumber(int stockNumber) {
            this.stockNumber = stockNumber;
        }

        public int getStockOut() {
            return stockOut;
        }

        public void setStockOut(int stockOut) {
            this.stockOut = stockOut;
        }

        public int getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(int deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public int getDeliveryFeeRate() {
            return deliveryFeeRate;
        }

        public void setDeliveryFeeRate(int deliveryFeeRate) {
            this.deliveryFeeRate = deliveryFeeRate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermCode() {
            return termCode;
        }

        public void setTermCode(String termCode) {
            this.termCode = termCode;
        }

        public String getCollectID() {
            return collectID;
        }

        public void setCollectID(String collectID) {
            this.collectID = collectID;
        }

        public int getSalesType() {
            return salesType;
        }

        public void setSalesType(int salesType) {
            this.salesType = salesType;
        }

        public int getPointPrice() {
            return pointPrice;
        }

        public void setPointPrice(int pointPrice) {
            this.pointPrice = pointPrice;
        }
    }
}
