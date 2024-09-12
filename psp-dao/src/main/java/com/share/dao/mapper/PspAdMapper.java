package com.share.dao.mapper;

import com.share.dao.model.PspAd;
import com.share.dao.model.PspAdExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface PspAdMapper {
    long countByExample(PspAdExample example);

    int deleteByExample(PspAdExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspAd row);

    int insertSelective(PspAd row);

    List<PspAd> selectByExampleWithRowbounds(PspAdExample example, RowBounds rowBounds);

    List<PspAd> selectByExample(PspAdExample example);

    PspAd selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspAd row, @Param("example") PspAdExample example);

    int updateByExample(@Param("row") PspAd row, @Param("example") PspAdExample example);

    int updateByPrimaryKeySelective(PspAd row);

    int updateByPrimaryKey(PspAd row);

    int logicalDeleteByPrimaryKey(Integer id);
}