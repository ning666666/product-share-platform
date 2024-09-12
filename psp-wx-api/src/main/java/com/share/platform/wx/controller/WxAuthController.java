package com.share.platform.wx.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.alibaba.fastjson.JSONObject;
import com.share.platform.wx.annotation.LoginUser;
import com.share.platform.wx.constant.Constant;
import com.share.platform.wx.constant.ResultCode;
import com.share.platform.wx.dto.request.UserBindPhoneNumRequest;
import com.share.platform.wx.dto.request.UserLoginInfoRequest;
import com.share.platform.wx.dto.request.UserRegisterInfoRequest;
import com.share.platform.wx.dto.request.UserResetInfoRequest;
import com.share.platform.wx.model.PspUser;
import com.share.platform.wx.service.UserService;
import com.share.platform.wx.service.UserTokenManager;
import com.share.platform.wx.dao.UserInfo;
import com.share.platform.wx.dao.UserToken;
import com.share.platform.wx.dao.WxLoginInfo;
import com.share.platform.wx.util.IpUtil;
import com.share.platform.wx.util.RegexUtil;
import com.share.platform.wx.util.ResultVo;
import com.share.platform.wx.util.UserTypeEnum;
import com.share.platform.wx.util.bcrypt.BCryptPasswordEncoder;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 鉴权服务
 */
@RestController
@RequestMapping("/wx/auth")
@Validated
@Api(tags = "WX-鉴权")
public class WxAuthController {
	private static final Logger logger = LoggerFactory.getLogger(WxAuthController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private WxMaService wxService;

	/**
	 * 账号登录
	 * 请求内容，{ username: xxx, password: xxx }
	 * @return 登录结果
	 */
	@PostMapping("/login")
	public Object login(@RequestBody UserLoginInfoRequest userLoginInfoRequest, HttpServletRequest request) {
		logger.info("【请求开始】账户登录,请求参数,body:{}", JSONObject.toJSONString(userLoginInfoRequest));

		String username = userLoginInfoRequest.getUsername();
		String password = userLoginInfoRequest.getPassword();
		if (username == null || password == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		List<PspUser> userList = userService.queryByUsername(username);
		PspUser user = null;
		if (userList.size() > 1) {
			logger.error("账户登录 出现多个同名用户错误,用户名:{},用户数量:{}", username, userList.size());
			return ResultVo.buildCode(ResultCode.BUSINESS_ERROR);
		} else if (userList.size() == 0) {
			logger.error("账户登录 用户尚未存在,用户名:{}", username);
			return ResultVo.buildCode(ResultCode.PARAMETER_VALUE_ERROR);
		} else {
			user = userList.get(0);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(password, user.getPassword())) {
			logger.error("账户登录 ,错误密码：{},{}", password, ResultCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD.getMsg());// 错误的密码打印到日志中作为提示也无妨
			return ResultVo.buildCode(ResultCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD);
		}

		// userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(username);
		userInfo.setAvatarUrl(user.getAvatar());
		
		try {
			String registerDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(user.getCreateTime() == null ? user.getCreateTime() : LocalDateTime.now());
			userInfo.setRegisterDate(registerDate);
			userInfo.setStatus(user.getStatus());
			userInfo.setUserLevel(user.getUserLevel());// 用户层级
			userInfo.setUserLevelDesc(UserTypeEnum.getInstance(user.getUserLevel()).getDesc());// 用户层级描述
		} catch (Exception e) {
			logger.error("账户登录：设置用户指定信息出错："+e.getMessage());
			e.printStackTrace();
		}

		// token
		UserToken userToken = null;
		try {
			userToken = UserTokenManager.generateToken(user.getId());
		} catch (Exception e) {
			logger.error("账户登录失败,生成token失败：{}", user.getId());
			e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}

		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);

		logger.info("【请求结束】账户登录,响应结果:{}", JSONObject.toJSONString(result));
		return ResultVo.buildData(ResultCode.SUCCESS, result);
	}

	/**
	 * 微信登录
	 *
	 * @param wxLoginInfo
	 *            请求内容，{ code: xxx, userInfo: xxx }
	 * @param request
	 *            请求对象
	 * @return 登录结果
	 */
	@PostMapping("/login_by_weixin")
	public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
		logger.info("【请求开始】微信登录,请求参数,wxLoginInfo:{}", JSONObject.toJSONString(wxLoginInfo));

		String code = wxLoginInfo.getCode();
		UserInfo userInfo = wxLoginInfo.getUserInfo();
		if (code == null || userInfo == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		Integer shareUserId = wxLoginInfo.getShareUserId();
		String sessionKey = null;
		String openId = null;
		try {
			WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
			sessionKey = result.getSessionKey();
			openId = result.getOpenid();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (sessionKey == null || openId == null) {
			logger.error("微信登录,调用官方接口失败：{}", code);
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}

		PspUser user = userService.queryByOid(openId);
		
		if (user == null) {
			user = new PspUser();
			user.setUsername(openId);
			user.setPassword(openId);
			user.setWeixinOpenid(openId);
			user.setAvatar(userInfo.getAvatarUrl());
			user.setNickname(userInfo.getNickName());
			user.setGender(userInfo.getGender());
			user.setUserLevel((byte) 0);
			user.setStatus((byte) 0);
			user.setLastLoginTime(new Date());
			user.setLastLoginIp(IpUtil.client(request));
			user.setShareUserId(shareUserId);

			userService.add(user);

			// 新用户发送注册优惠券
			//couponAssignService.assignForRegister(user.getId());
		} else {
			user.setLastLoginTime(new Date());
			user.setLastLoginIp(IpUtil.client(request));
			if (userService.updateById(user) == 0) {
				return ResultVo.buildCode(ResultCode.DATA_UPDATE_FAIL);
			}
		}

		// token
		UserToken userToken = null;
		try {
			userToken = UserTokenManager.generateToken(user.getId());
		} catch (Exception e) {
			logger.error("微信登录失败,生成token失败：{}", user.getId());
			e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}
		userToken.setSessionKey(sessionKey);

		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		userInfo.setUserId(user.getId());
		if (!StringUtils.isEmpty(user.getMobile())) {// 手机号存在则设置
			userInfo.setPhone(user.getMobile());
		}
		try {
			String registerDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(user.getCreateTime() == null ? user.getCreateTime() : LocalDateTime.now());
			userInfo.setRegisterDate(registerDate);
			userInfo.setStatus(user.getStatus());
			userInfo.setUserLevel(user.getUserLevel());// 用户层级
			userInfo.setUserLevelDesc(UserTypeEnum.getInstance(user.getUserLevel()).getDesc());// 用户层级描述
		} catch (Exception e) {
			logger.error("微信登录：设置用户指定信息出错："+e.getMessage());
			e.printStackTrace();
		}
		result.put("userInfo", userInfo);

		logger.info("【请求结束】微信登录,响应结果:{}", JSONObject.toJSONString(result));
		return ResultVo.buildData(ResultCode.SUCCESS, result);
	}

	/**
	 * 请求验证码
	 *
	 * @param body
	 *            手机号码{mobile}
	 * @return
	 */
	@PostMapping("regCaptcha")
	public Object registerCaptcha(@RequestBody String body) {
		//logger.info("【请求开始】请求验证码,请求参数，body:{}", body);
		//
		//String phoneNumber = JacksonUtil.parseString(body, "mobile");
		//if (StringUtils.isEmpty(phoneNumber)) {
		//	return ResponseUtil.badArgument();
		//}
		//if (!RegexUtil.isMobileExact(phoneNumber)) {
		//	return ResponseUtil.badArgumentValue();
		//}
		//
		//if (!notifyService.isSmsEnable()) {
		//	logger.error("请求验证码出错:{}", AUTH_CAPTCHA_UNSUPPORT.desc());
		//	return wx.util.WxResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT);
		//}
		//String code = CharUtil.getRandomNum(6);
		//SmsResult smsResult = notifyService.notifySmsTemplate(phoneNumber, NotifyType.CAPTCHA, new String[] { code, "1" });
		//if (smsResult != null) {
		//	logger.info("*****腾讯云短信发送->请求验证码，短信发送结果：{}",JSONObject.toJSONString(smsResult));
		//}
		//
		//boolean successful = wx.service.CaptchaCodeManager.addToCache(phoneNumber, code,1);
		//if (!successful) {
		//	logger.error("请求验证码出错:{}", AUTH_CAPTCHA_FREQUENCY.desc());
		//	return wx.util.WxResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY);
		//}
		//
		//logger.info("【请求结束】请求验证码成功");
		//return ResponseUtil.ok();
        return null;
    }

	/**
	 * 账号注册
	 * @param userRegisterInfoRequest
	 *		请求内容 { username: xxx, password: xxx, mobile: xxx code: xxx }
	 * 		其中code是手机验证码，目前还不支持手机短信验证码
	 * @param request
	 *            请求对象
	 * @return 登录结果 成功则 { errno: 0, errmsg: '成功', data: { token: xxx, tokenExpire:
	 *         xxx, userInfo: xxx } } 失败则 { errno: XXX, errmsg: XXX }
	 */
	@PostMapping("/register")
	public Object register(@RequestBody UserRegisterInfoRequest userRegisterInfoRequest, HttpServletRequest request) {
		logger.info("【请求开始】账号注册,请求参数，body:{}", JSONObject.toJSONString(userRegisterInfoRequest));

		String username = userRegisterInfoRequest.getUsername();
		String password = userRegisterInfoRequest.getPassword();
		String mobile = userRegisterInfoRequest.getMobile();
		String wxCode = userRegisterInfoRequest.getWxCode();

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(wxCode)) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		List<PspUser> userList = userService.queryByUsername(username);
		if (userList.size() > 0) {
			logger.error("请求账号注册出错:{}", ResultCode.AUTH_NAME_REGISTERED.getMsg());
			return ResultVo.buildCode(ResultCode.AUTH_NAME_REGISTERED);
		}

		userList = userService.queryByMobile(mobile);
		if (userList.size() > 0) {
			logger.error("请求账号注册出错:{}", ResultCode.AUTH_NAME_REGISTERED.getMsg());
			return ResultVo.buildCode(ResultCode.AUTH_NAME_REGISTERED);
		}
		if (!RegexUtil.isMobileExact(mobile)) {
			logger.error("请求账号注册出错:{}", ResultCode.AUTH_INVALID_MOBILE.getMsg());
			return ResultVo.buildCode(ResultCode.AUTH_INVALID_MOBILE);
		}
		// 判断验证码是否正确
		//String cacheCode = wx.service.CaptchaCodeManager.getCachedCaptcha(mobile);
		//if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
		//	logger.error("请求账号注册出错:{}", AUTH_CAPTCHA_UNMATCH.desc());
		//	return WxResponseUtil.fail(AUTH_CAPTCHA_UNMATCH);
		//}

		String openId = null;
		try {
			WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
			openId = result.getOpenid();
		} catch (Exception e) {
			logger.error("请求账号注册出错:{}", ResultCode.AUTH_OPENID_UNACCESS.getMsg());
			e.printStackTrace();
			ResultVo.buildCode(ResultCode.AUTH_OPENID_UNACCESS);
		}
		userList = userService.queryByOpenid(openId);
		if (userList.size() > 1) {
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}
		if (userList.size() == 1) {
			PspUser checkUser = userList.get(0);
			String checkUsername = checkUser.getUsername();
			String checkPassword = checkUser.getPassword();
			if (!checkUsername.equals(openId) || !checkPassword.equals(openId)) {
				logger.error("请求账号注册出错:{}", ResultCode.AUTH_OPENID_BINDED.getMsg());
				return ResultVo.buildCode(ResultCode.AUTH_OPENID_BINDED);
			}
		}

		PspUser user = null;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		user = new PspUser();
		user.setUsername(username);
		user.setPassword(encodedPassword);
		user.setMobile(mobile);
		user.setWeixinOpenid(openId);
		user.setAvatar(Constant.DEFAULT_AVATAR);
		user.setNickname(username);
		user.setGender((byte) 0);
		user.setUserLevel((byte) 0);
		user.setStatus((byte) 0);
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(IpUtil.client(request));
		userService.add(user);

		// 给新用户发送注册优惠券
		//try {
		//	couponAssignService.assignForRegister(user.getId());
		//} catch (Exception e) {
		//	logger.error("账号注册失败,给新用户发送注册优惠券失败：{}", user.getId());
		//	e.printStackTrace();
		//	return ResponseUtil.fail();
		//}

		// userInfo
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(username);
		userInfo.setAvatarUrl(user.getAvatar());

		// token
		UserToken userToken = null;
		try {
			userToken = UserTokenManager.generateToken(user.getId());
		} catch (Exception e) {
			logger.error("账号注册失败,生成token失败：{}", user.getId());
			e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}

		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("token", userToken.getToken());
		result.put("tokenExpire", userToken.getExpireTime().toString());
		result.put("userInfo", userInfo);

		logger.info("【请求结束】账号注册,响应结果:{}", JSONObject.toJSONString(result));
		return ResultVo.buildData(ResultCode.SUCCESS, result);
	}

	/**
	 * 账号密码重置
	 *
	 * @param userResetInfoRequest
	 *            请求内容 { password: xxx, mobile: xxx code: xxx }
	 *            其中code是手机验证码，目前还不支持手机短信验证码
	 * @param request
	 *            请求对象
	 * @return 登录结果 成功则 { errno: 0, errmsg: '成功' } 失败则 { errno: XXX, errmsg: XXX }
	 */
	@PostMapping("/reset")
	public Object reset(@RequestBody UserResetInfoRequest userResetInfoRequest, HttpServletRequest request) {
		logger.info("【请求开始】账号密码重置,请求参数，body:{}", JSONObject.toJSONString(userResetInfoRequest));

		String password = userResetInfoRequest.getPassword();
		String mobile = userResetInfoRequest.getMobile();
		String code = userResetInfoRequest.getCode();

		if (mobile == null || password == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		// 判断验证码是否正确
		//String cacheCode = wx.service.CaptchaCodeManager.getCachedCaptcha(mobile);
		//if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
		//	logger.error("账号密码重置出错:{}", AUTH_CAPTCHA_UNMATCH.desc());
		//	return WxResponseUtil.fail(AUTH_CAPTCHA_UNMATCH);
		//}

		List<PspUser> userList = userService.queryByMobile(mobile);

		PspUser user = null;
		if (userList.size() > 1) {
			logger.error("账号密码重置出错,账户不唯一,查询手机号:{}", mobile);
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		} else if (userList.size() == 0) {
			logger.error("账号密码重置出错,账户不存在,查询手机号:{},{}", mobile, ResultCode.AUTH_MOBILE_UNREGISTERED.getMsg());
			return ResultVo.buildCode(ResultCode.AUTH_MOBILE_UNREGISTERED);
		} else {
			user = userList.get(0);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		user.setPassword(encodedPassword);

		if (userService.updateById(user) == 0) {
			logger.error("账号密码重置更新用户信息出错,id：{}", user.getId());
			return ResultVo.buildCode(ResultCode.DATA_UPDATE_FAIL);
		}

		logger.info("【请求结束】账号密码重置成功!");
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}

	/**
	 * 绑定手机号码
	 * 
	 * @param userId
	 * @param userBindPhoneNumRequest
	 * @return
	 */
	@PostMapping("/bindPhone")
	public Object bindPhone(@LoginUser Integer userId, @RequestBody UserBindPhoneNumRequest userBindPhoneNumRequest) {
		logger.info("【请求开始】绑定手机号码,请求参数，body:{}", JSONObject.toJSONString(userBindPhoneNumRequest));

		String sessionKey = UserTokenManager.getSessionKey(userId);
		String encryptedData = userBindPhoneNumRequest.getEncryptedData();
		String iv = userBindPhoneNumRequest.getIv();
		WxMaPhoneNumberInfo phoneNumberInfo = null;
		try {
			phoneNumberInfo = this.wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
		} catch (Exception e) {
			logger.error("绑定手机号码失败,获取微信绑定的手机号码出错：{}", JSONObject.toJSONString(userBindPhoneNumRequest));
			e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}
		String phone = phoneNumberInfo.getPhoneNumber();
		PspUser user = userService.findById(userId);
		user.setMobile(phone);
		if (userService.updateById(user) == 0) {
			logger.error("绑定手机号码,更新用户信息出错,id：{}", user.getId());
			return ResultVo.buildCode(ResultCode.DATA_UPDATE_FAIL);
		}
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("phone", phone);

		logger.info("【请求结束】绑定手机号码,响应结果：{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 注销登录
	 * 
	 * @param userId
	 * @return
	 */
	@PostMapping("/logout")
	public Object logout(@LoginUser Integer userId) {
		logger.info("【请求开始】注销登录,请求参数，userId:{}", userId);
		if (userId == null) {
			return ResultVo.buildCode(ResultCode.USER_NOT_LOGIN);
		}
		try {
			UserTokenManager.removeToken(userId);
		} catch (Exception e) {
			logger.error("注销登录出错：userId:{}", userId);
			e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}

		logger.info("【请求结束】注销登录成功!");
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}
}
