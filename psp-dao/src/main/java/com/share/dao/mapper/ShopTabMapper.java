package com.share.dao.mapper;

import com.github.pagehelper.Page;
import com.share.dao.dto.reponse.AllShopTabResponse;
import com.share.dao.dto.reponse.GoodsToShopInfoResponse;
import com.share.dao.dto.request.AllShopTabRequest;
import com.share.dao.model.ShopTab;
import com.share.dao.model.ShopTabExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ShopTabMapper {
    long countByExample(ShopTabExample example);

    int deleteByExample(ShopTabExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShopTab row);

    int insertSelective(ShopTab row);

    List<ShopTab> selectByExampleWithRowbounds(ShopTabExample example, RowBounds rowBounds);

    List<ShopTab> selectByExample(ShopTabExample example);

    ShopTab selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") ShopTab row, @Param("example") ShopTabExample example);

    int updateByExample(@Param("row") ShopTab row, @Param("example") ShopTabExample example);

    int updateByPrimaryKeySelective(ShopTab row);

    int updateByPrimaryKey(ShopTab row);

    Page<AllShopTabResponse> getAllShopTabInfo(@Param("requestDTO") AllShopTabRequest allShopTabRequest, @Param("businessId") Integer businessId);

    List<GoodsToShopInfoResponse> getShopInfoByUser();
}