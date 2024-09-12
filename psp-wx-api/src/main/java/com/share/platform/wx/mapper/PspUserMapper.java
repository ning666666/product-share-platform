package com.share.platform.wx.mapper;

import com.share.platform.wx.model.PspUser;
import com.share.platform.wx.model.PspUserExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface PspUserMapper {
    long countByExample(PspUserExample example);

    int deleteByExample(PspUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspUser row);

    int insertSelective(PspUser row);

    List<PspUser> selectByExampleWithRowbounds(PspUserExample example, RowBounds rowBounds);

    List<PspUser> selectByExample(PspUserExample example);

    PspUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspUser row, @Param("example") PspUserExample example);

    int updateByExample(@Param("row") PspUser row, @Param("example") PspUserExample example);

    int updateByPrimaryKeySelective(PspUser row);

    int updateByPrimaryKey(PspUser row);

    PspUser selectOneByExample(PspUserExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}