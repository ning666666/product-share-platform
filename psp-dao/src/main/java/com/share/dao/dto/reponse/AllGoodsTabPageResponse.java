package com.share.dao.dto.reponse;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "所有商品信息分页响应类")
public class AllGoodsTabPageResponse {

   private List<AllGoodsTabResponse> allGoodsTabList;

   private Long totalRecords;
}
