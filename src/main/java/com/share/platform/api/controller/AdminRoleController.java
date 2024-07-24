package com.share.platform.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.share.platform.api.annotation.RequiresPermissionsDesc;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.model.PspPermission;
import com.share.platform.api.model.PspRole;
import com.share.platform.api.service.serviceImpl.PermissionService;
import com.share.platform.api.service.serviceImpl.RoleService;
import com.share.platform.api.utils.*;
import com.share.platform.api.validator.Order;
import com.share.platform.api.validator.Sort;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/admin/role")
@Validated
@Api(tags = "角色")
public class AdminRoleController {
	private static final Logger logger = LoggerFactory.getLogger(AdminRoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@RequiresPermissions("admin:role:list")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "角色查询")
	@GetMapping("/list")
	public Object list(String name, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "create_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->角色查询,请求参数,name:{},page:{}", name, page);

		List<PspRole> roleList = roleService.querySelective(name, page, limit, sort, order);
		long total = PageInfo.of(roleList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", roleList);

		logger.info("【请求结束】系统管理->角色管理->角色查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	@GetMapping("/options")
	public Object options() {
		List<PspRole> roleList = roleService.queryAll();
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->查询所有角色");

		List<Map<String, Object>> options = new ArrayList<>(roleList.size());
		for (PspRole role : roleList) {
			Map<String, Object> option = new HashMap<>(2);
			option.put("value", role.getId());
			option.put("label", role.getName());
			options.add(option);
		}

		logger.info("【请求结束】系统管理->角色管理->查询所有角色,响应结果:{}", JSONObject.toJSONString(options));
		return ResultVo.buildData(ResultCode.SUCCESS, options);
	}

	@RequiresPermissions("admin:role:read")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "角色详情")
	@GetMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->角色详情,请求参数,id:{}", id);

		PspRole role = roleService.findById(id);

		logger.info("【请求结束】系统管理->角色管理->角色详情,响应结果:{}", JSONObject.toJSONString(role));
		return ResultVo.buildData(ResultCode.SUCCESS, role);
	}

	private Object validate(PspRole role) {
		String name = role.getName();
		if (StringUtils.isEmpty(name)) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		return null;
	}

	@RequiresPermissions("admin:role:create")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "角色添加")
	@PostMapping("/create")
	public Object create(@RequestBody PspRole role) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->角色添加,请求参数:{}", JSONObject.toJSONString(role));

		Object error = validate(role);
		if (error != null) {
			return error;
		}

		if (roleService.checkExist(role.getName())) {
			logger.info("系统管理->角色管理->角色添加错误:{}", ResultCode.ROLE_NAME_EXIST.getMsg());
			return ResultVo.buildCode(ResultCode.ROLE_NAME_EXIST);
		}

		roleService.add(role);

		logger.info("【请求结束】系统管理->角色管理->角色添加,响应结果:{}", JSONObject.toJSONString(role));
		return ResultVo.buildData(ResultCode.SUCCESS, role);
	}

	@RequiresPermissions("admin:role:update")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "角色编辑")
	@PostMapping("/update")
	public Object update(@RequestBody PspRole role) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->角色编辑,请求参数:{}", JSONObject.toJSONString(role));

		Object error = validate(role);
		if (error != null) {
			return error;
		}

		roleService.updateById(role);
		logger.info("【请求结束】系统管理->角色管理->角色编辑,响应结果:{}", "成功!");
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}

	@RequiresPermissions("admin:role:delete")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "角色删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody PspRole role) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->角色删除,请求参数,id:{}", JSONObject.toJSONString(role));

		Integer id = role.getId();
		if (id == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}
		roleService.deleteById(id);

		logger.info("【请求结束】系统管理->角色管理->角色删除,响应结果:{}", "成功!");
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}

	@Autowired
	private ApplicationContext context;
	private List<PermVo> systemPermissions = null;
	private Set<String> systemPermissionsString = null;

	private List<PermVo> getSystemPermissions() {
		final String basicPackage = "com.qiguliuxing.dts.admin";
		if (systemPermissions == null) {
			List<Permission> permissions = PermissionUtil.listPermission(context, basicPackage);
			systemPermissions = PermissionUtil.listPermVo(permissions);
			systemPermissionsString = PermissionUtil.listPermissionString(permissions);
		}
		return systemPermissions;
	}

	private Set<String> getAssignedPermissions(Integer roleId) {
		// 这里需要注意的是，如果存在超级权限*，那么这里需要转化成当前所有系统权限。
		// 之所以这么做，是因为前端不能识别超级权限，所以这里需要转换一下。
		Set<String> assignedPermissions = null;
		if (permissionService.checkSuperPermission(roleId)) {
			getSystemPermissions();
			assignedPermissions = systemPermissionsString;
		} else {
			assignedPermissions = permissionService.queryByRoleId(roleId);
		}

		return assignedPermissions;
	}

	/**
	 * 管理员的权限情况
	 *
	 * @return 系统所有权限列表和管理员已分配权限
	 */
	@RequiresPermissions("admin:role:permission:get")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "权限详情")
	@GetMapping("/permissions")
	public Object getPermissions(Integer roleId) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->权限详情,请求参数,roleId:{}", roleId);

		List<PermVo> systemPermissions = getSystemPermissions();
		Set<String> assignedPermissions = getAssignedPermissions(roleId);

		Map<String, Object> data = new HashMap<>();
		data.put("systemPermissions", systemPermissions);
		data.put("assignedPermissions", assignedPermissions);

		logger.info("【请求结束】系统管理->角色管理->权限详情,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 更新管理员的权限
	 *
	 * @param body
	 * @return
	 */
	@RequiresPermissions("admin:role:permission:update")
	@RequiresPermissionsDesc(menu = { "系统管理", "角色管理" }, button = "权限变更")
	@PostMapping("/permissions")
	public Object updatePermissions(@RequestBody String body) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->角色管理->权限变更,请求参数,body:{}", body);

		Integer roleId = JacksonUtil.parseInteger(body, "roleId");
		List<String> permissions = JacksonUtil.parseStringList(body, "permissions");
		if (roleId == null || permissions == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		// 如果修改的角色是超级权限，则拒绝修改。
		if (permissionService.checkSuperPermission(roleId)) {
			logger.error("系统管理->角色管理->权限变更 错误:{}", ResultCode.ROLE_SUPER_SUPERMISSION.getMsg());
			return ResultVo.buildCode(ResultCode.ROLE_SUPER_SUPERMISSION);
		}

		// 先删除旧的权限，再更新新的权限
		permissionService.deleteByRoleId(roleId);
		for (String permission : permissions) {
			PspPermission permissionP = new PspPermission();
			permissionP.setRoleId(roleId);
			permissionP.setPermission(permission);
			permissionService.add(permissionP);
		}

		logger.info("【请求结束】系统管理->角色管理->权限变更,响应结果:{}", "成功!");
		return ResultVo.buildCode(ResultCode.SUCCESS);
	}

}
