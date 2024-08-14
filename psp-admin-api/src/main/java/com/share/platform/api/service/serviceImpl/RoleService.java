package com.share.platform.api.service.serviceImpl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.share.platform.api.mapper.PspRoleMapper;
import com.share.platform.api.model.PspRole;
import com.share.platform.api.model.PspRoleExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RoleService {
	@Resource
	private PspRoleMapper roleMapper;

	public Set<String> queryByIds(Integer[] roleIds) {
		Set<String> roles = new HashSet<String>();
		if (roleIds.length == 0) {
			return roles;
		}

		PspRoleExample example = new PspRoleExample();
		example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
		List<PspRole> roleList = roleMapper.selectByExample(example);

		for (PspRole role : roleList) {
			roles.add(role.getName());
		}

		return roles;

	}

	public List<PspRole> querySelective(String roleName, Integer page, Integer size, String sort, String order) {
		PspRoleExample example = new PspRoleExample();
		PspRoleExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(roleName)) {
			criteria.andNameEqualTo("%" + roleName + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return roleMapper.selectByExample(example);
	}

	public PspRole findById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	public void add(PspRole role) {
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		roleMapper.insertSelective(role);
	}

	public void deleteById(Integer id) {
		roleMapper.logicalDeleteByPrimaryKey(id);
	}

	public void updateById(PspRole role) {
		role.setUpdateTime(new Date());
		roleMapper.updateByPrimaryKeySelective(role);
	}

	public boolean checkExist(String name) {
		PspRoleExample example = new PspRoleExample();
		example.or().andNameEqualTo(name).andDeletedEqualTo(false);
		return roleMapper.countByExample(example) != 0;
	}

	public List<PspRole> queryAll() {
		PspRoleExample example = new PspRoleExample();
		example.or().andDeletedEqualTo(false);
		return roleMapper.selectByExample(example);
	}
}
