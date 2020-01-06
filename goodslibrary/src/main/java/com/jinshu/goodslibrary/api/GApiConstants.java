package com.jinshu.goodslibrary.api;

/**
 * Create on 2019/9/10 15:11 by bll
 */


public class GApiConstants {

    /**
     * 服务器地址
     */
    private static final String BaseUrl = "https://www.haoju.me/interface-server/api/";
    private static final String Base_Cart_Url = "https://www.haoju.me/cart/";
    public static final String HostUrl = "https://www.haoju.me/goodsShop/";
    public static final String Order_Url = "https://www.haoju.me/memberOrder/";

    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case GHostType.BASE_URL:
                host = BaseUrl;
                break;
            case GHostType.BASE_CART_URL:
                host = Base_Cart_Url;
                break;
            case GHostType.HOST_URL:
                host = HostUrl;
                break;
            case GHostType.ORDER_URL:
                host = Order_Url;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
