package com.share.platform.wx.constant;

public class Constant {

    public static class TIME {
        public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

        public static final String MOBILE_FORMAT = "yyyy/MM/dd HH:mm";

        public static final String FORMAT_WITH_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";

        public static final String DATE = "yyyy-MM-dd";

        public static final String FORMAT_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";

        public static final String FORMAT_WITHOUT_YEAR_SECOND = "MM-dd HH:mm";

        public static final String CHINA_YEAR_MONTH = "yyyy年MM月";

        public static final String YEAR_MONTH = "yyyy-MM";

        public static final String YEAR = "yyyy";

        public static final String TIME_ZONE = "GMT+8";
    }

    /**
     * 当前用户
     */
    public static final String CURRENT_USER = "user";

    /**
     * 权限集合
     */
    public static final String PERMISSIONS = "permissions";

    /**
     * 菜单根id
     */
    public static final Integer MENU_ROOT_ID = 1;

    /**
     * 菜单树
     */
    public static final String MENU_TREE = "menuTree";

    public static final String SHARP = "#";
    /**
     * 组织机构根id
     */
    public static final Long ORG_ROOT_ID = 0L;


    /**
     * 构造函数私有化，避免被实例化
     */
    private Constant() {
    }

    /**
     * http请求header中的key
     */
    public static class HEADER_KEY {
        /**
         * 前端请求的token
         */
        public static final String TOKEN = "token";
        /**
         * token过期时间
         */
        public static final String TOKEN_EXPIRE = "token-expire";
        /**
         * 刷新的token
         */
        public static final String REFRESH_TOKEN = "refresh-token";
    }

    public static final String DEFAULT_AVATAR = "https://img1.baidu.com/it/u=3436975220,2297888082&fm=253&fmt=auto&app=138&f=JPEG?w=300&h=300";

    public static final String DEFAULT_ORDER_FIX = "小程序订单：";

    public static final String MISS_PARAMS = "缺少必要参数";

    public static final Long CACHE_EXPIRE_MINUTES = 60 * 6L; // 设置缓存 6小时，有效期单位 ： 分钟
}
