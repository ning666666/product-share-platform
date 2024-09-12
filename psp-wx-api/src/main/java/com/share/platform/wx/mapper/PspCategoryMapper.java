package com.share.platform.wx.mapper;

import com.share.platform.wx.model.PspCategory;
import com.share.platform.wx.model.PspCategoryExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface PspCategoryMapper {
    long countByExample(PspCategoryExample example);

    int deleteByExample(PspCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspCategory row);

    int insertSelective(PspCategory row);

    List<PspCategory> selectByExampleWithRowbounds(PspCategoryExample example, RowBounds rowBounds);

    List<PspCategory> selectByExample(PspCategoryExample example);

    PspCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspCategory row, @Param("example") PspCategoryExample example);

    int updateByExample(@Param("row") PspCategory row, @Param("example") PspCategoryExample example);

    int updateByPrimaryKeySelective(PspCategory row);

    int updateByPrimaryKey(PspCategory row);

    int logicalDeleteByPrimaryKey(Integer id);

    List<PspCategory> selectByExampleSelective(@Param("example") PspCategoryExample example, @Param("selective") PspCategory.Column ... selective);
}