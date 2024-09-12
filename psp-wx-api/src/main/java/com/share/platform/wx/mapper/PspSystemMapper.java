package com.share.platform.wx.mapper;

import com.share.platform.wx.model.PspSystem;
import com.share.platform.wx.model.PspSystemExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface PspSystemMapper {
    long countByExample(PspSystemExample example);

    int deleteByExample(PspSystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspSystem row);

    int insertSelective(PspSystem row);

    List<PspSystem> selectByExampleWithRowbounds(PspSystemExample example, RowBounds rowBounds);

    List<PspSystem> selectByExample(PspSystemExample example);

    PspSystem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspSystem row, @Param("example") PspSystemExample example);

    int updateByExample(@Param("row") PspSystem row, @Param("example") PspSystemExample example);

    int updateByPrimaryKeySelective(PspSystem row);

    int updateByPrimaryKey(PspSystem row);

    void queryAll();
}