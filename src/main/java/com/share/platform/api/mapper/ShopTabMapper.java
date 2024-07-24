package com.share.platform.api.mapper;

import com.share.platform.api.dto.reponse.AllShopTabResponse;
import com.share.platform.api.dto.reponse.GoodsToShopInfoResponse;
import com.share.platform.api.dto.request.AllShopTabRequest;
import com.share.platform.api.model.ShopTab;
import com.share.platform.api.model.ShopTabExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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

    List<AllShopTabResponse> getAllShopTabInfo(@Param("requestDTO") AllShopTabRequest allShopTabRequest, @Param("businessId") Integer businessId);

    List<GoodsToShopInfoResponse> getShopInfoByUser();
}