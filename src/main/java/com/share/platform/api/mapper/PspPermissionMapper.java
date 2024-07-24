package com.share.platform.api.mapper;

import com.share.platform.api.model.PspPermission;
import com.share.platform.api.model.PspPermissionExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PspPermissionMapper {
    long countByExample(PspPermissionExample example);

    int deleteByExample(PspPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspPermission row);

    int insertSelective(PspPermission row);

    List<PspPermission> selectByExampleWithRowbounds(PspPermissionExample example, RowBounds rowBounds);

    List<PspPermission> selectByExample(PspPermissionExample example);

    PspPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspPermission row, @Param("example") PspPermissionExample example);

    int updateByExample(@Param("row") PspPermission row, @Param("example") PspPermissionExample example);

    int updateByPrimaryKeySelective(PspPermission row);

    int updateByPrimaryKey(PspPermission row);

    int logicalDeleteByExample(@Param("example") PspPermissionExample example);
}