package com.share.platform.api.mapper;

import com.share.platform.api.model.PspAd;
import com.share.platform.api.model.PspAdExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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