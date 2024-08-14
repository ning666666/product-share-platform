package com.share.platform.api.service.serviceImpl;

import com.share.platform.api.mapper.PspPermissionMapper;
import com.share.platform.api.model.PspPermission;
import com.share.platform.api.model.PspPermissionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PermissionService {
	@Resource
	private PspPermissionMapper permissionMapper;

	public Set<String> queryByRoleIds(Integer[] roleIds) {
		Set<String> permissions = new HashSet<String>();
		if (roleIds.length == 0) {
			return permissions;
		}

		PspPermissionExample example = new PspPermissionExample();
		example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
		List<PspPermission> permissionList = permissionMapper.selectByExample(example);

		for (PspPermission permission : permissionList) {
			permissions.add(permission.getPermission());
		}

		return permissions;
	}

	public Set<String> queryByRoleId(Integer roleId) {
		Set<String> permissions = new HashSet<String>();
		if (roleId == null) {
			return permissions;
		}

		PspPermissionExample example = new PspPermissionExample();
		example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
		List<PspPermission> permissionList = permissionMapper.selectByExample(example);

		for (PspPermission permission : permissionList) {
			permissions.add(permission.getPermission());
		}

		return permissions;
	}

	public boolean checkSuperPermission(Integer roleId) {
		if (roleId == null) {
			return false;
		}

		PspPermissionExample example = new PspPermissionExample();
		example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andDeletedEqualTo(false);
		return permissionMapper.countByExample(example) != 0;
	}

	public void deleteByRoleId(Integer roleId) {
		PspPermissionExample example = new PspPermissionExample();
		example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
		permissionMapper.logicalDeleteByExample(example);
	}

	public void add(PspPermission DtsPermission) {
		DtsPermission.setCreateTime(new Date());
		DtsPermission.setUpdateTime(new Date());
		permissionMapper.insertSelective(DtsPermission);
	}
}
