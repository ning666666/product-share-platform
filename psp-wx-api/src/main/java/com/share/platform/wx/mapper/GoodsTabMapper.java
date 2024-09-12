package com.share.platform.wx.mapper;

import com.github.pagehelper.Page;
import com.share.platform.wx.dto.reponse.AllGoodsTabResponse;
import com.share.platform.wx.dto.reponse.WxGoodsResponse;
import com.share.platform.wx.dto.request.AllGoodsTabRequest;
import com.share.platform.wx.model.GoodsTab;
import com.share.platform.wx.model.GoodsTabExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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

    Page<WxGoodsResponse> queryByCondition();

    Page<WxGoodsResponse> queryWxCategoryByCondition(@Param("id") Integer catlogId);
}