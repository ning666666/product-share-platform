package com.share.dao.service;

import com.github.pagehelper.PageHelper;
import com.share.dao.mapper.PspUserMapper;
import com.share.dao.model.PspUser;
import com.share.dao.model.PspUserExample;
import com.share.dao.model.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

	@Resource
	private PspUserMapper userMapper;

	public PspUser findById(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	public UserVo findUserVoById(Integer userId) {
		PspUser user = findById(userId);
		UserVo userVo = new UserVo();
		userVo.setNickname(user.getNickname());
		userVo.setAvatar(user.getAvatar());
		return userVo;
	}

	public PspUser queryByOid(String openId) {
		PspUserExample example = new PspUserExample();
		example.or().andWeixinOpenidEqualTo(openId).andDeletedEqualTo(false);
		return userMapper.selectOneByExample(example);
	}

	public void add(PspUser user) {
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		userMapper.insertSelective(user);
	}

	public int updateById(PspUser user) {
		user.setUpdateTime(new Date());
		return userMapper.updateByPrimaryKeySelective(user);
	}

	public List<PspUser> querySelective(String username, String mobile, Integer page, Integer size, String sort,
										String order) {
		PspUserExample example = new PspUserExample();
		PspUserExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(username)) {
			criteria.andNicknameLike("%" + username + "%");
		}
		if (!StringUtils.isEmpty(mobile)) {
			criteria.andMobileEqualTo(mobile);
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return userMapper.selectByExample(example);
	}

	public int count() {
		PspUserExample example = new PspUserExample();
		example.or().andDeletedEqualTo(false);

		return (int) userMapper.countByExample(example);
	}

	public List<PspUser> queryByUsername(String username) {
		PspUserExample example = new PspUserExample();
		example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
		return userMapper.selectByExample(example);
	}

	public boolean checkByUsername(String username) {
		PspUserExample example = new PspUserExample();
		example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
		return userMapper.countByExample(example) != 0;
	}

	public List<PspUser> queryByMobile(String mobile) {
		PspUserExample example = new PspUserExample();
		example.or().andMobileEqualTo(mobile).andDeletedEqualTo(false);
		return userMapper.selectByExample(example);
	}

	public List<PspUser> queryByOpenid(String openid) {
		PspUserExample example = new PspUserExample();
		example.or().andWeixinOpenidEqualTo(openid).andDeletedEqualTo(false);
		return userMapper.selectByExample(example);
	}

	public void deleteById(Integer id) {
		userMapper.logicalDeleteByPrimaryKey(id);
	}


	public List<PspUser> queryPspUserListByNickname(String username,String mobile) {
		PspUserExample example = new PspUserExample();
		PspUserExample.Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(username)) {
			criteria.andNicknameLike("%" + username + "%");
		}
		if (!StringUtils.isEmpty(mobile)) {
			criteria.andMobileEqualTo(mobile);
		}
		criteria.andDeletedEqualTo(false);
		return userMapper.selectByExample(example);
	}

}
