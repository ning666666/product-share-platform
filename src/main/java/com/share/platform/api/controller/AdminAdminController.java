package com.share.platform.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.share.platform.api.annotation.RequiresPermissionsDesc;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.model.PspAdmin;
import com.share.platform.api.service.serviceImpl.AdminService;
import com.share.platform.api.utils.*;
import com.share.platform.api.utils.bcrypt.BCryptPasswordEncoder;
import com.share.platform.api.validator.Order;
import com.share.platform.api.validator.Sort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "管理员")
@RequestMapping("/admin/admin")
public class AdminAdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminAdminController.class);

	@Autowired
	private AdminService adminService;

	@RequiresPermissions("admin:admin:list")
	@RequiresPermissionsDesc(menu = { "系统管理", "管理员管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String username, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "create_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->管理员管理->查询,请求参数:username:{},page:{}", username, page);

		List<PspAdmin> adminList = adminService.querySelective(username, page, limit, sort, order);
		long total = PageInfo.of(adminList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", adminList);

		logger.info("【请求结束】系统管理->管理员管理->查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS,data);
	}

	public ResultVo validate(PspAdmin admin) {
		String name = admin.getUsername();
		if (StringUtils.isEmpty(name)) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}
		if (!RegexUtil.isUsername(name)) {
			logger.error("校验错误：{}", ResultCode.ADMIN_INVALID_NAME.getMsg());
			return ResultVo.buildCode(ResultCode.ADMIN_INVALID_NAME);
		}
		String password = admin.getPassword();
		if (StringUtils.isEmpty(password) || password.length() < 6) {
			logger.error("校验错误：{}", ResultCode.ADMIN_INVALID_PASSWORD.getMsg());
			return ResultVo.buildCode(ResultCode.ADMIN_INVALID_PASSWORD);
		}
		return null;
	}

	@RequiresPermissions("admin:admin:create")
	@RequiresPermissionsDesc(menu = { "系统管理", "管理员管理" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody PspAdmin admin) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->管理员管理->添加,请求参数:{}", JSONObject.toJSONString(admin));

		ResultVo resultVo = validate(admin);
		if (resultVo != null) {
			return resultVo;
		}

		String username = admin.getUsername();
		List<PspAdmin> adminList = adminService.findAdmin(username);
		if (adminList.size() > 0) {
			logger.error("系统管理->管理员管理->添加 ,错误：{}", ResultCode.ADMIN_NAME_EXIST.getMsg());
			return ResultVo.buildCode(ResultCode.ADMIN_NAME_EXIST);
		}

		String rawPassword = admin.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(rawPassword);
		admin.setPassword(encodedPassword);
		adminService.add(admin);

		logger.info("【请求结束】系统管理->管理员管理->添加,响应结果:{}", JSONObject.toJSONString(admin));
		return ResultVo.buildData(ResultCode.SUCCESS, admin);
	}

	@RequiresPermissions("admin:admin:read")
	@RequiresPermissionsDesc(menu = { "系统管理", "管理员管理" }, button = "详情")
	@GetMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->管理员管理->详情,请求参数,id:{}", id);

		PspAdmin admin = adminService.findById(id);

		logger.info("【请求结束】系统管理->管理员管理->详情,响应结果:{}", JSONObject.toJSONString(admin));
		return ResultVo.buildData(ResultCode.SUCCESS, admin);
	}

	@RequiresPermissions("admin:admin:update")
	@RequiresPermissionsDesc(menu = { "系统管理", "管理员管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody PspAdmin admin) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->管理员管理->编辑,请求参数:{}", JSONObject.toJSONString(admin));

		Object error = validate(admin);
		if (error != null) {
			return error;
		}

		Integer anotherAdminId = admin.getId();
		if (anotherAdminId == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		String rawPassword = admin.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(rawPassword);
		admin.setPassword(encodedPassword);

		if (adminService.updateById(admin) == 0) {
			logger.error("系统管理->管理员管理-编辑 ,错误：{}", "更新数据失败！");
			return ResultVo.buildCode(ResultCode.UPDATE_FAIL);
		}

		logger.info("【请求结束】系统管理->管理员管理->编辑,响应结果:{}", JSONObject.toJSONString(admin));
		return ResultVo.buildData(ResultCode.SUCCESS, admin);
	}

	@RequiresPermissions("admin:admin:delete")
	@RequiresPermissionsDesc(menu = { "系统管理", "管理员管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody PspAdmin admin) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->管理员管理->删除,请求参数:{}", JSONObject.toJSONString(admin));

		Integer anotherAdminId = admin.getId();
		if (anotherAdminId == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		adminService.deleteById(anotherAdminId);

		logger.info("【请求结束】系统管理->管理员管理->删除 成功！");
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}

	/**
	 * 管理员头像上传
	 */
	@ApiOperation(value = "管理员头像上传")
	@PostMapping("/storage/upload")
	public ResultVo storageUpload(@RequestParam("file") MultipartFile file) {
		return adminService.storageUpload(file);
	}
}
