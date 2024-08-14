package com.share.platform.api.constant.enumvalue;

/**
 * ems上报数据类型
 * @auther ning
 */
public enum GoodsTypeEnum {

    FIRE_VEGETABLES_AREA("FIRE_AREA","炒菜区"),
    COUNTRY_AREA("COUNTRY_AREA","乡下吐火区"),
    DAILY_BUY_AREA("DAILY_BUY_AREA","日常代购区"),
    FRUITS_BUY_AREA("FRUITS_BUY_AREA","水果代购区");

    private String code;
    private String msg;

    GoodsTypeEnum(String code, String msg) {
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
