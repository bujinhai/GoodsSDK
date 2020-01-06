package com.jinshu.goodslibrary.entity;

import java.util.List;

/**
 * Create on 2019-11-11 11:28 by bll
 */

public class CartGoodsEntity {


    private List<DataInfo> data;

    public List<DataInfo> getData() {
        return data;
    }

    public void setData(List<DataInfo> data) {
        this.data = data;
    }

    public static class DataInfo {
        /**
         * shopName : 氧寿旗舰店
         * goodsList : [{"id":"8a2f462a6c45feb1016e5867739600de","cartID":"8a2f462a6c45feb1016c75de70130037","goodsID":null,"goodsName":"百岁友美容酒（旗袍）","listImage":"http://wj.haoju.me/6bc92aa9e9b74ac185dba4dd8c601047.jpg","goodsShopID":"8a2f462a6ad58b08016ad59cceb8001a","pointPrice":644,"priceStand":1288,"discountRate":0,"priceNow":644,"qty":1,"salesUnitQTY":0,"priceTotal":644,"objectFeatureItemID1":null,"objectFeatureItemName1":null,"objectFeatureItemID2":null,"objectFeatureItemName2":null,"faceImage":null,"objectFeatureItemID3":null,"objectFeatureItemName3":null,"isSelected":1},{"id":"8a2f462a6c45feb1016e5867f24e00df","cartID":"8a2f462a6c45feb1016c75de70130037","goodsID":null,"goodsName":"古方善用乳膏  ","listImage":"http://wj.haoju.me/5519565f84f543ab92270775b8efe978.jpg","goodsShopID":"8a2f462a6c7ef1cd016c8e5d95200046","pointPrice":15,"priceStand":50,"discountRate":0,"priceNow":35,"qty":1,"salesUnitQTY":0,"priceTotal":35,"objectFeatureItemID1":null,"objectFeatureItemName1":null,"objectFeatureItemID2":null,"objectFeatureItemName2":null,"faceImage":null,"objectFeatureItemID3":null,"objectFeatureItemName3":null,"isSelected":1}]
         */

        private String shopName;
        private List<CartGoodsInfo> goodsList;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public List<CartGoodsInfo> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<CartGoodsInfo> goodsList) {
            this.goodsList = goodsList;
        }
    }
}
