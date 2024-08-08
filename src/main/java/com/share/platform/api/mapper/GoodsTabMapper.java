package com.share.platform.api.mapper;

import com.github.pagehelper.Page;
import com.share.platform.api.dto.reponse.AllGoodsTabResponse;
import com.share.platform.api.dto.request.AllGoodsTabRequest;
import com.share.platform.api.model.GoodsTab;
import com.share.platform.api.model.GoodsTabExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface GoodsTabMapper {
    long countByExample(GoodsTabExample example);

    int deleteByExample(GoodsTabExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsTab row);

    int insertSelective(GoodsTab row);

    List<GoodsTab> selectByExampleWithRowbounds(GoodsTabExample example, RowBounds rowBounds);

    List<GoodsTab> selectByExample(GoodsTabExample example);

    GoodsTab selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") GoodsTab row, @Param("example") GoodsTabExample example);

    int updateByExample(@Param("row") GoodsTab row, @Param("example") GoodsTabExample example);

    int updateByPrimaryKeySelective(GoodsTab row);

    int updateByPrimaryKey(GoodsTab row);

    Page<AllGoodsTabResponse> getAllGoodsTabInfo(@Param("requestDTO") AllGoodsTabRequest allGoodsTabRequest);
}