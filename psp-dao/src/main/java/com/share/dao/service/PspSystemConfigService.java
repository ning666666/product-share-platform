package com.share.dao.service;

import com.share.dao.mapper.PspSystemMapper;
import com.share.dao.model.PspSystem;
import com.share.dao.model.PspSystemExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PspSystemConfigService {
	@Resource
	private PspSystemMapper systemMapper;

	public List<PspSystem> queryAll() {
		PspSystemExample example = new PspSystemExample();
		example.or();
		List<PspSystem> pspSystemList = systemMapper.selectByExample(example);
		return pspSystemList;
	}
}
