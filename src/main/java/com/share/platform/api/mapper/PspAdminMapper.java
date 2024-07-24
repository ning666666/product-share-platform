package com.share.platform.api.mapper;

import com.share.platform.api.model.PspAdmin;
import com.share.platform.api.model.PspAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface PspAdminMapper {
    long countByExample(PspAdminExample example);

    int deleteByExample(PspAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PspAdmin row);

    int insertSelective(PspAdmin row);

    List<PspAdmin> selectByExampleWithRowbounds(PspAdminExample example, RowBounds rowBounds);

    List<PspAdmin> selectByExample(PspAdminExample example);

    PspAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PspAdmin row, @Param("example") PspAdminExample example);

    int updateByExample(@Param("row") PspAdmin row, @Param("example") PspAdminExample example);

    int updateByPrimaryKeySelective(PspAdmin row);

    int updateByPrimaryKey(PspAdmin row);

    List<PspAdmin> selectByExampleSelective(@Param("example") PspAdminExample example, @Param("selective") PspAdmin.Column ... selective);

    int logicalDeleteByPrimaryKey(@Param("id") Integer id);

    PspAdmin selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") PspAdmin.Column ... selective);
}