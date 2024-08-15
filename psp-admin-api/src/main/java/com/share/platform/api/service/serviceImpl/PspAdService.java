package com.share.platform.api.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.share.platform.api.mapper.PspAdMapper;
import com.share.platform.api.model.PspAd;
import com.share.platform.api.model.PspAdExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PspAdService {
	@Resource
	private PspAdMapper adMapper;

	public List<PspAd> queryIndex() {
		PspAdExample example = new PspAdExample();
		example.or().andPositionEqualTo((byte) 1).andDeletedEqualTo(false).andEnabledEqualTo(true);
		return adMapper.selectByExample(example);
	}

	public List<PspAd> querySelective(String name, String content, Integer page, Integer limit, String sort,
			String order) {
		PspAdExample example = new PspAdExample();
		PspAdExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (!StringUtils.isEmpty(content)) {
			criteria.andContentLike("%" + content + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return adMapper.selectByExample(example);
	}

	public int updateById(PspAd ad) {
		ad.setUpdateTime(new Date());
		return adMapper.updateByPrimaryKeySelective(ad);
	}

	public void deleteById(Integer id) {
		adMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(PspAd ad) {
		ad.setCreateTime(new Date());
		ad.setUpdateTime(new Date());
		adMapper.insertSelective(ad);
	}

	public PspAd findById(Integer id) {
		return adMapper.selectByPrimaryKey(id);
	}
}
