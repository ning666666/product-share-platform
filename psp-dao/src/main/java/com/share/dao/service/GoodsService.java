package com.share.dao.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.share.dao.dto.reponse.WxGoodsPageResponse;
import com.share.dao.dto.reponse.WxGoodsResponse;
import com.share.dao.mapper.GoodsTabMapper;
import com.share.dao.model.GoodsTab;
import com.share.dao.model.GoodsTabExample;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GoodsService {

	@Autowired
	private GoodsTabMapper goodsTabMapper;
	private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);

	public WxGoodsPageResponse queryByCondition(Integer offset, Integer newLimit) {
		WxGoodsPageResponse wxGoodsPageResponse = new WxGoodsPageResponse();

		PageHelper.startPage(offset, newLimit);
		Page<WxGoodsResponse> wxGoodsInfo = goodsTabMapper.queryByCondition();
		wxGoodsPageResponse.setAllGoodsTabList(wxGoodsInfo.getResult());
		wxGoodsPageResponse.setTotalRecords(wxGoodsInfo.getTotal());
		return wxGoodsPageResponse;
	}

	public WxGoodsPageResponse queryWxCategoryByCondition(Integer catlogId, Integer offset, Integer catlogMoreLimit) {
		WxGoodsPageResponse wxGoodsPageResponse = new WxGoodsPageResponse();

		PageHelper.startPage(offset, catlogMoreLimit);
		Page<WxGoodsResponse> wxGoodsInfo = goodsTabMapper.queryWxCategoryByCondition(catlogId);
		wxGoodsPageResponse.setAllGoodsTabList(wxGoodsInfo.getResult());
		wxGoodsPageResponse.setTotalRecords(wxGoodsInfo.getTotal());
		return wxGoodsPageResponse;
	}

	public GoodsTab findById(Integer id) {
		return goodsTabMapper.selectByPrimaryKey(id);
	}

	public Integer queryOnSale() {
		GoodsTabExample example = new GoodsTabExample();
		example.or();
		return (int) goodsTabMapper.countByExample(example);
	}

	public List<GoodsTab> querySelective(Integer categoryId, Integer page, Integer size, String sort, String order) {
		GoodsTabExample example = new GoodsTabExample();
		GoodsTabExample.Criteria criteria1 = example.or();

		if (!StringUtils.isEmpty(categoryId) && categoryId != 0) {
			criteria1.andGoodsTypeEqualTo(String.valueOf(categoryId));
		}

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		return goodsTabMapper.selectByExampleWithRowbounds(example, new RowBounds(page, size));
	}

}
