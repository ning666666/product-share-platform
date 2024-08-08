package com.share.platform.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.share.platform.api.captcha.CaptchaCodeManager;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.dto.request.UserLoginInfoRequest;
import com.share.platform.api.model.PspAdmin;
import com.share.platform.api.service.serviceImpl.PermissionService;
import com.share.platform.api.service.serviceImpl.RoleService;
import com.share.platform.api.utils.Base64;
import com.share.platform.api.utils.*;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@Api(tags = "登录/登出")
@RequestMapping("/admin/auth")
public class AdminAuthController {
	private static final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@PostMapping("/login")
	public Object login(@RequestBody UserLoginInfoRequest userLoginInfo) {
		logger.info("【请求开始】系统管理->用户登录,请求参数:body:{}", userLoginInfo);

		//String username = JacksonUtil.parseString(body, "username");
		//String password = JacksonUtil.parseString(body, "password");
		//String code = JacksonUtil.parseString(body, "code");
		//String uuid = JacksonUtil.parseString(body, "uuid");

		if (StringUtils.isEmpty(userLoginInfo.getUsername()) || StringUtils.isEmpty(userLoginInfo.getPassword())) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		//验证码校验
		//String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(uuid);
		//if (cachedCaptcha == null) {
		//	logger.error("系统管理->用户登录  错误:{},", ResultCode.AUTH_CAPTCHA_EXPIRED.getMsg());
		//	return ResultVo.buildCode(ResultCode.AUTH_CAPTCHA_EXPIRED);
		//}
		//if (!code.equalsIgnoreCase(cachedCaptcha)) {
		//	logger.error("系统管理->用户登录  错误:{},输入验证码：{},后台验证码：{}", ResultCode.AUTH_CAPTCHA_ERROR.getMsg(),code,cachedCaptcha);
		//	return ResultVo.buildCode(ResultCode.AUTH_CAPTCHA_ERROR);
		//}
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(new UsernamePasswordToken(userLoginInfo.getUsername(), userLoginInfo.getPassword()));
		} catch (UnknownAccountException uae) {
			logger.error("系统管理->用户登录  错误:{}", ResultCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD.getMsg());
			return ResultVo.buildCode(ResultCode.ADMIN_INVALID_ACCOUNT_OR_PASSWORD);
		} catch (LockedAccountException lae) {
			logger.error("系统管理->用户登录 错误:{}", ResultCode.ADMIN_LOCK_ACCOUNT.getMsg());
			return ResultVo.buildCode(ResultCode.ADMIN_LOCK_ACCOUNT);
		} catch (AuthenticationException ae) {
			logger.error("系统管理->用户登录 错误:{}", ResultCode.ADMIN_INVALID_AUTH.getMsg());
			return ResultVo.buildCode(ResultCode.ADMIN_INVALID_AUTH);
		}

		logger.info("【请求结束】系统管理->用户登录,响应结果:{}", JSONObject.toJSONString(currentUser.getSession().getId()));
		return ResultVo.buildData(ResultCode.LOGIN_SUCCESS, currentUser.getSession().getId());
	}

	/*
	 * 用户注销
	 */
	@RequiresAuthentication
	@PostMapping("/logout")
	public Object login() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();

		logger.info("【请求结束】系统管理->用户注销,响应结果:{}", JSONObject.toJSONString(currentUser.getSession().getId()));
		return ResultVo.buildCode(ResultCode.LOGOUT_SUCCESS);
	}

	@RequiresAuthentication
	@GetMapping("/info")
	public Object info() {
		Subject currentUser = SecurityUtils.getSubject();
		PspAdmin admin = (PspAdmin) currentUser.getPrincipal();

		Map<String, Object> data = new HashMap<>();
		data.put("name", admin.getUsername());
		data.put("avatar", admin.getAvatar());

		// 转为Integer数组
		Integer[] roleIds = admin.getRoleIds();

		Set<String> roles = roleService.queryByIds(roleIds);
		Set<String> permissions = permissionService.queryByRoleIds(roleIds);
		data.put("roles", roles);
		// TODO：这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
		data.put("perms", toAPI(permissions));

		logger.info("【请求结束】系统管理->用户信息获取,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	@Autowired
	private ApplicationContext context;
	private HashMap<String, String> systemPermissionsMap = null;

	private Collection<String> toAPI(Set<String> permissions) {
		if (systemPermissionsMap == null) {
			systemPermissionsMap = new HashMap<>();
			final String basicPackage = "com.share.platform.api";
			List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
			for (Permission permission : systemPermissions) {
				String perm = permission.getRequiresPermissions().value()[0];
				String api = permission.getApi();
				systemPermissionsMap.put(perm, api);
			}
		}

		Collection<String> apis = new HashSet<>();
		for (String perm : permissions) {
			String api = systemPermissionsMap.get(perm);
			apis.add(api);

			if (perm.equals("*")) {
				apis.clear();
				apis.add("*");
				return apis;
				// return systemPermissionsMap.values();
			}
		}
		return apis;
	}
	
	/**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public Object getCode(HttpServletResponse response) throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		logger.info("验证码:{}", verifyCode);
        // 唯一标识
        String uuid = UUID.randomUUID().toString();
		logger.info("uuid:{}", uuid);
        boolean successful = CaptchaCodeManager.addToCache(uuid, verifyCode,10);//存储内存
        if (!successful) {
			logger.error("请求验证码出错:{}", ResultCode.AUTH_CAPTCHA_FREQUENCY.getMsg());
			return ResultVo.buildCode(ResultCode.AUTH_CAPTCHA_FREQUENCY);
		}
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
        	Map<String, Object> data = new HashMap<>();
            data.put("uuid", uuid);
            data.put("img", Base64.encode(stream.toByteArray()));
			return ResultVo.buildData(ResultCode.SUCCESS, data);
        } catch (Exception e){
            e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
        } finally {
            stream.close();
        }
    }

	@GetMapping("/401")
	public Object page401() {
		return ResultVo.buildCode(ResultCode.USER_NOT_LOGIN);
	}

	@GetMapping("/index")
	public Object pageIndex() {
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}

	@GetMapping("/403")
	public Object page403() {
		return ResultVo.buildCode(ResultCode.USER_NO_PERMISSION);
	}
}
