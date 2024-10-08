package com.share.dao.mapper;

import com.share.dao.model.PspArticle;
import com.share.dao.model.PspArticleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface PspArticleMapper {
    long countByExample(PspArticleExample example);

    int deleteByExample(PspArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspArticle row);

    int insertSelective(PspArticle row);

    List<PspArticle> selectByExampleWithBLOBsWithRowbounds(PspArticleExample example, RowBounds rowBounds);

    List<PspArticle> selectByExampleWithBLOBs(PspArticleExample example);

    List<PspArticle> selectByExampleWithRowbounds(PspArticleExample example, RowBounds rowBounds);

    List<PspArticle> selectByExample(PspArticleExample example);

    PspArticle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspArticle row, @Param("example") PspArticleExample example);

    int updateByExampleWithBLOBs(@Param("row") PspArticle row, @Param("example") PspArticleExample example);

    int updateByExample(@Param("row") PspArticle row, @Param("example") PspArticleExample example);

    int updateByPrimaryKeySelective(PspArticle row);

    int updateByPrimaryKeyWithBLOBs(PspArticle row);

    int updateByPrimaryKey(PspArticle row);

    List<PspArticle> selectByExampleSelective(@Param("example") PspArticleExample example, @Param("selective") PspArticle.Column ... selective);

    int logicalDeleteByPrimaryKey(Integer id);
}