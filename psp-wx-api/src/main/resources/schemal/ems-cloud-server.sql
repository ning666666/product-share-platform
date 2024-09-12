DROP TABLE IF EXISTS `goods_tab`;
DROP TABLE IF EXISTS `shop_tab`;
DROP TABLE IF EXISTS `psp_category`;
DROP TABLE IF EXISTS `psp_admin`;
DROP TABLE IF EXISTS `psp_permission`;
DROP TABLE IF EXISTS `psp_role`;
DROP TABLE IF EXISTS `psp_user`;
DROP TABLE IF EXISTS `psp_system`;


# 商品表
CREATE TABLE `goods_tab` (
     `id` int(32) NOT NULL AUTO_INCREMENT,
     `parent_id` int(32) NOT NULL COMMENT '店铺id',
     `goods_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编码',
     `shop_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属店铺编码',
     `shop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属店铺名称',
     `goods_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品标题',
     `original_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原价',
     `discounted_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '折后价',
     `opening_time` datetime DEFAULT NULL COMMENT '开团时间',
     `goods_lab` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品标签(新品首团；好物复购)',
     `goods_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品类别(炒菜区；乡下吐火区；日常代购区；水果代购区)',
     `main_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品主图(限制3张，数据格式可用json，或者逗号隔开)',
     `details_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品详情图(限制8张，同上)',
     `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品实拍视频',
     `qualifications` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资质',
     `after_sales` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '售后说明，图文',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
     `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
     `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='商品信息';

# 商品类目表
CREATE TABLE `psp_category`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类目名称',
    `keywords` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类目关键字，以JSON数组格式',
    `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类目广告语介绍',
    `pid` int(11) NOT NULL DEFAULT 0 COMMENT '父类目ID',
    `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类目图标',
    `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类目图片',
    `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'L1',
    `sort_order` tinyint(3) NULL DEFAULT 50 COMMENT '排序',
    `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `parent_id`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '类目表' ROW_FORMAT = Compact;

# 用户表
CREATE TABLE `psp_user` (
    `id` int(32) NOT NULL AUTO_INCREMENT,
    `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
    `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户密码',
    `gender` tinyint(3) NOT NULL DEFAULT 0 COMMENT '性别：0 未知， 1男， 1 女',
    `birthday` date NULL DEFAULT NULL COMMENT '生日',
    `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
    `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最近一次登录IP地址',
    `user_level` tinyint(3) NULL DEFAULT 0 COMMENT '用户层级 0 普通用户，1 VIP用户，2 区域代理用户',
    `nickname` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称或网络名称',
    `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户手机号码',
    `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像图片',
    `weixin_openid` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '微信登录openid',
    `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '0 可用, 1 禁用, 2 注销',
    `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
    `share_user_id` int(11) NULL DEFAULT NULL,
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `user_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表';

# 系统配置表
CREATE TABLE `psp_system`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `key_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置名',
    `key_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统配置值',
    `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = Compact;