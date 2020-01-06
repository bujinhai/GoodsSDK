package com.jinshu.goodslibrary.api;


import com.jinshu.goodslibrary.basebean.GBaseResponse;
import com.jinshu.goodslibrary.entity.CartGoodsEntity;
import com.jinshu.goodslibrary.entity.CartGoodsNumberEntity;
import com.jinshu.goodslibrary.entity.CollectEntity;
import com.jinshu.goodslibrary.entity.GUserEntity;
import com.jinshu.goodslibrary.entity.GoodsAttachEntity;
import com.jinshu.goodslibrary.entity.GoodsDetailEntity;
import com.jinshu.goodslibrary.entity.GoodsEntity;
import com.jinshu.goodslibrary.entity.GoodsOrderEntity;
import com.jinshu.goodslibrary.ui.pay.entity.AliPayEntity;
import com.jinshu.goodslibrary.ui.pay.entity.WeChatEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Create on 2019/9/10 15:10 by bll
 */


public interface GApiService {

    /**
     * 登录
     */
    @GET("memberLogin.json")
    Observable<GBaseResponse<GUserEntity>> login(
            @Query("loginName") String loginName,
            @Query("password") String password,
            @QueryMap Map<String, String> map);

    /**
     * 获取一个导航文章分类的推荐商品列表的店铺商品信息
     */
    @GET("getGoodsRecommendList.json")
    Observable<GBaseResponse<GoodsEntity>> getGoodsRecommendList(
            @Query("sessionID") String sessionID,
            @Query("navigatorID") String navigatorID,
            @Query("currentPage") int currentPage,
            @Query("pageNumber") int pageNumber);


    /**
     * 获取购物车数量
     */
    @GET("getMemberCartGoodsNum.json")
    Observable<GBaseResponse<CartGoodsNumberEntity>> getMemberCartGoodsNum(
            @Query("sessionID") String sessionID);


    /**
     * 获取购物车商品
     */
    @GET("getCartGoodsList.json")
    Observable<GBaseResponse<CartGoodsEntity>> getCartGoodsList(
            @Query("sessionID") String sessionID,
            @Query("currentPage") int currentPage,
            @Query("pageNumber") int pageNumber);

    /**
     * 获取一个对象定义的全部附件列表
     */
    @GET("getObjectAttachmentList.json")
    Observable<GBaseResponse<GoodsAttachEntity>> getObjectAttachmentList(
            @Query("sessionID") String sessionID,
            @Query("objectDefineID") String objectDefineID,
            @Query("objectID") String objectID,
            @Query("sortTypeTime") String sortTypeTime);


    /**
     * 商品加入购物车
     */
    @GET("addGoodsShopToCart.json")
    Observable<GBaseResponse> addGoodsShopToCart(
            @Query("sessionID") String sessionID,
            @Query("goodsShopID") String goodsShopID);

    /**
     * 勾选购物车商品
     */
    @GET("selectCartGoods.json")
    Observable<GBaseResponse> selectCartGoods(
            @Query("sessionID") String sessionID,
            @Query("cartGoodsID") String cartGoodsID,
            @Query("isSelect") int isSelect);

    /**
     * 更改购物车商品数量
     */
    @GET("updateCartGoodsNumber.json")
    Observable<GBaseResponse> updateCartGoodsNumber(
            @Query("sessionID") String sessionID,
            @Query("cartGoodsID") String cartGoodsID,
            @Query("qty") int qty);

    /**
     * 删除购物车商品
     */
    @GET("removeCartGoods.json")
    Observable<GBaseResponse> removeCartGoods(
            @Query("sessionID") String sessionID,
            @Query("cartGoodsID") String cartGoodsID);

    /**
     * 获取商品详情
     */
    @GET("getGoodsShopDetail.json")
    Observable<GBaseResponse<GoodsDetailEntity>> getGoodsShopDetail(
            @Query("goodsShopID") String goodsShopID,
            @Query("type") String type,
            @Query("memberID") String memberID);

    /**
     * 提交一个对象的收藏
     */
    @GET("submitOneCollect.json")
    Observable<GBaseResponse<CollectEntity>> submitOneCollect(
            @Query("sessionID") String sessionID,
            @Query("objectDefineID") String objectDefineID,
            @Query("objectID") String objectID,
            @Query("objectName") String objectName);

    /**
     * 取消一个对象的收藏
     */
    @GET("deleteOneCollect.json")
    Observable<GBaseResponse> deleteOneCollect(
            @Query("sessionID") String sessionID,
            @Query("collectID") String collectID);


    /**
     * 生成订单
     */
    @GET("createMemberOrder.json")
    Observable<GBaseResponse<GoodsOrderEntity>> createMemberOrder(
            @Query("sessionID") String sessionID,
            @Query("type") int type,
            @Query("goodsShopID") String goodsShopID,
            @Query("cartID") String cartID,
            @Query("memberAddressID") String memberAddressID,
            @Query("contactName") String contactName,
            @Query("contactAddress") String contactAddress,
            @Query("phone") String phone,
            @Query("pointTotal") String pointTotal,
            @Query("memberInvoiceDefineID") String memberInvoiceDefineID,
            @Query("paywayID") String paywayID,
            @Query("sendType") String sendType,//3快递发货
            @Query("payFrom") String payFrom);//1钱包支付  2第三方支付


    /**
     * APP获取支付宝支付信息
     */
    @GET("getAppAliPayBody.json")
    Observable<GBaseResponse<AliPayEntity>> getAppAliPayBody(
            @Query("sessionID") String sessionID,
            @Query("payWayID") String payWayID,
            @Query("memberPaymentID") String memberPaymentID,
            @Query("memberOrderID") String memberOrderID,
            @Query("body") String body);

    /**
     * 获取app微信支付信息
     */
    @GET("getAppWeixinPayInfo.json")
    Observable<GBaseResponse<WeChatEntity>> getAppWeixinPayInfo(
            @Query("sessionID") String sessionID,
            @Query("payWayID") String payWayID,
            @Query("memberPaymentID") String memberPaymentID,
            @Query("memberOrderID") String memberOrderID,
            @Query("body") String body);

}
