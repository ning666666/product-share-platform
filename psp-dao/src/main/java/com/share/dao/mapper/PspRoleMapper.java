package com.share.dao.mapper;

import com.share.dao.model.PspRole;
import com.share.dao.model.PspRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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