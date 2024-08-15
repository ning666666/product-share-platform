package com.share.platform.api.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.share.platform.api.mapper.PspArticleMapper;
import com.share.platform.api.model.PspArticle;
import com.share.platform.api.model.PspArticleExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.share.platform.api.model.PspArticle.Column;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PspArticleService {

	@Resource
	private PspArticleMapper articleMapper;

	private PspArticle.Column[] columns = new Column[] { Column.id, Column.title, Column.addTime, Column.type };

	public PspArticle findById(Integer id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	public List<PspArticle> queryList(int offset, int limit, String sort, String order) {
		PspArticleExample example = new PspArticleExample();
		example.or().andDeletedEqualTo(false);
		example.setOrderByClause(sort + " " + order);
		PageHelper.startPage(offset, limit);
		return articleMapper.selectByExampleSelective(example, columns);
	}
	
	public boolean checkExistByTitle(String title) {
		PspArticleExample example = new PspArticleExample();
		example.or().andTitleEqualTo(title).andDeletedEqualTo(false);
		return articleMapper.countByExample(example) != 0;
	}

	public void add(PspArticle article) {
		article.setCreateTime(new Date());
		article.setUpdateTime(new Date());
		articleMapper.insertSelective(article);
	}

	public List<PspArticle> querySelective(String title, Integer page, Integer size, String sort, String order) {
		PspArticleExample example = new PspArticleExample();
		PspArticleExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(title)) {
			criteria.andTitleLike("%" + title + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return articleMapper.selectByExampleWithBLOBs(example);
	}

	public int updateById(PspArticle article) {
		article.setUpdateTime(new Date());
		return articleMapper.updateByPrimaryKeySelective(article);
	}

	public void deleteById(Integer id) {
		articleMapper.logicalDeleteByPrimaryKey(id);
	}
}
