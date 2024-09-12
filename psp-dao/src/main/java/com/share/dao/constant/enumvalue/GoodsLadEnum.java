package com.share.dao.constant.enumvalue;

/**
 * 商品标签
 * @auther ning
 */
public enum GoodsLadEnum {

    NEW_PRODUCT_LAUNCH("NEW_PRO_LAUNCH","新品首发"),
    GOOD_ITEM_REPURCHASE("GOOD_ITEM_REP","好物复购");

    private String code;
    private String msg;

    GoodsLadEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
