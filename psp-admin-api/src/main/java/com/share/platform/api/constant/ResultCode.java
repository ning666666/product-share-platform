package com.share.platform.api.constant;

public enum ResultCode {
	/**
	 * 成功
	 */
	SUCCESS(200,"SUCCESS"),

	/**
	 * 用户为登录或登录过期
	 */
	NOT_AUTH_USER(401,"登录过期"),

	/**
	 * 系统错误
	 */
	RUNTIME_ERROR(500,"系统错误"),

	LOGIN_SUCCESS(200,"登录成功"),

	LOGOUT_SUCCESS(200,"登出成功"),

	LOAD_ERROR(400,"登录错误"),

	/**
	 * 参数错误
	 */
	PARAMETER_ERROR(401, "参数错误"),

	PARAMETER_VALUE_ERROR(402, "参数值错误"),

	UPLOAD_FAILED(500, "上传失败"),

	AGAIN_FAILED(500, "请重新上传"),

	UPLOAD_SUCCESS(200, "上传成功"),

	SUCCESS_CALCULATING(201, "计算中"),

	QUEUE_CALCULATING(202, "排队计算中"),

	UPDATE_SUCCESS(200,"修改成功"),

	UPDATE_FAIL(500,"修改失败"),

	DELETE_SUCCESS(200,"删除成功"),

	DELETE_FAIL(500,"删除失败"),

	IMAGE_NOT_EXIST(500,"图片路径不存在"),

	IMAGE_PATH_NOT_COMPLETE(500,"图片路径不完整"),

	IMAGE_DELETE_SUCCESS(200,"图片删除成功"),

	IMAGE_DELETE_FAIL(500,"图片删除失败"),

	GOODS_INFO_NULL(500,"当前商品信息为空"),

	SHOPTAB_INFO_NULL(500,"当前店铺信息为空"),

	SYSTEM_ERROR(-1, "系统异常"),

	BUSINESS_ERROR(500, "系统异常"),

	USER_NOT_LOGIN(501, "未登录,请登录"),

	NOT_TOKEN_LOGIN(402, "未携带token，请求无效"),

	NOT_AUTH_LOGIN(403, "未认证，跳转重新登录"),

	TOKEN_INVALIDATION_JUMP_LOGIN(401, "token失效，跳转重新登录"),

	USER_NO_PERMISSION(506, "无操作权限"),

	TOURISTS_CAN_ACCESS(200, "匿名游客用户可访问"),

	AUTH_USER_CAN_ACCESS(200, "认证用户可访问"),

	ERROR_USERNAME(409, "用户名错误"),

	ERROR_PASSWORD(410, "密码错误"),

	USER_LOCKED(411, "用户为锁定状态"),

	FAILED_DEL_OWN(485, "不能删除自己"),

	FAILED_USER_ALREADY_EXIST(486, "该用户已存在"),

	FAILED_ROLE_ALREADY_EXIST(487, "该角色已存在"),

	FAILED_RESOURCE_ALREADY_EXIST(488, "该资源已存在"),

	FAILED_USER_NOT_EXIST(492, "该用户不存在"),

	FAILED_ROLE_NOT_EXIST(493, "该角色不存在"),

	FAILED_RESOURCE_NOT_EXIST(494, "该资源不存在"),

	FAILED_GROUP_NOT_EXIST(495, "该用户组不存在"),

	FAILED_ORGANIZATION_NOT_EXIST(496, "该组织不存在"),

	ADMIN_INVALID_NAME(600, "管理员名称不符合规定"),

	ADMIN_INVALID_PASSWORD(601, "管理员密码长度不能小于6"),

	ADMIN_NAME_EXIST(602, "管理员已经存在"),

	AUTH_CAPTCHA_EXPIRED(645,"验证码过期"),

	AUTH_CAPTCHA_ERROR(644,"验证码错误"),

	ADMIN_LOCK_ACCOUNT(606, "用户帐号已锁定不可用"),

	ADMIN_INVALID_AUTH(607, "认证失败"),

	AUTH_CAPTCHA_FREQUENCY(643,"验证码请求过于频繁"),

	ADMIN_INVALID_ACCOUNT_OR_PASSWORD(605, "用户帐号或密码不正确"),

	ROLE_NAME_EXIST(640, "角色已经存在"),

	ROLE_SUPER_SUPERMISSION(641, "当前角色的超级权限不能变更"),

	DATA_UPDATE_FAIL(505, "更新数据失败"),

	AUTH_NAME_REGISTERED(701, "用户名已注册"),

	AUTH_MOBILE_REGISTERED(702, "手机号已注册"),

	AUTH_INVALID_MOBILE(703, "手机号格式不正确"),

	AUTH_OPENID_UNACCESS(704, "获取腾讯官方 openid失败"),

	AUTH_OPENID_BINDED(705, "openid已绑定账号"),

	AUTH_MOBILE_UNREGISTERED(706, "手机号未注册");


	private Integer code;
	private String msg;

	ResultCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
