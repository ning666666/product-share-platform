package com.share.platform.api.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.share.platform.api.mapper.PspCategoryMapper;
import com.share.platform.api.model.PspCategory;
import com.share.platform.api.model.PspCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PspCategoryService {
	@Resource
	private PspCategoryMapper categoryMapper;
	private PspCategory.Column[] CHANNEL = { PspCategory.Column.id, PspCategory.Column.name,
			PspCategory.Column.iconUrl };

	public List<PspCategory> queryL1WithoutRecommend(int offset, int limit) {
		PspCategoryExample example = new PspCategoryExample();
		example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
		PageHelper.startPage(offset, limit);
		return categoryMapper.selectByExample(example);
	}

	public List<PspCategory> queryL1(int offset, int limit) {
		PspCategoryExample example = new PspCategoryExample();
		example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
		PageHelper.startPage(offset, limit);
		return categoryMapper.selectByExample(example);
	}

	public List<PspCategory> queryL1() {
		PspCategoryExample example = new PspCategoryExample();
		example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
		return categoryMapper.selectByExample(example);
	}

	public List<PspCategory> queryByPid(Integer pid) {
		PspCategoryExample example = new PspCategoryExample();
		example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
		return categoryMapper.selectByExample(example);
	}

	public List<PspCategory> queryL2ByIds(List<Integer> ids) {
		PspCategoryExample example = new PspCategoryExample();
		example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
		return categoryMapper.selectByExample(example);
	}

	public PspCategory findById(Integer id) {
		return categoryMapper.selectByPrimaryKey(id);
	}

	public List<PspCategory> querySelective(String id, String name, Integer page, Integer size, String sort,
			String order) {
		PspCategoryExample example = new PspCategoryExample();
		PspCategoryExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(id)) {
			criteria.andIdEqualTo(Integer.valueOf(id));
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return categoryMapper.selectByExample(example);
	}

	public int updateById(PspCategory category) {
		category.setUpdateTime(new Date());
		return categoryMapper.updateByPrimaryKeySelective(category);
	}

	public void deleteById(Integer id) {
		categoryMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(PspCategory category) {
		category.setCreateTime(new Date());
		category.setUpdateTime(new Date());
		categoryMapper.insertSelective(category);
	}

	public List<PspCategory> queryChannel() {
		PspCategoryExample example = new PspCategoryExample();
		example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
		PageHelper.startPage(1, 9);// 设置分页10
		return categoryMapper.selectByExampleSelective(example, CHANNEL);
	}
}
