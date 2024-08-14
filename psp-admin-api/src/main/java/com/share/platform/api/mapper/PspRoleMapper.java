package com.share.platform.api.mapper;

import com.share.platform.api.model.PspRole;
import com.share.platform.api.model.PspRoleExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PspRoleMapper {
    long countByExample(PspRoleExample example);

    int deleteByExample(PspRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspRole row);

    int insertSelective(PspRole row);

    List<PspRole> selectByExampleWithRowbounds(PspRoleExample example, RowBounds rowBounds);

    List<PspRole> selectByExample(PspRoleExample example);

    PspRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspRole row, @Param("example") PspRoleExample example);

    int updateByExample(@Param("row") PspRole row, @Param("example") PspRoleExample example);

    int updateByPrimaryKeySelective(PspRole row);

    int updateByPrimaryKey(PspRole row);

    int logicalDeleteByPrimaryKey(Integer id);
}