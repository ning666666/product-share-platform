package com.share.dao.dto.reponse;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "微信首页商品信息分页响应类")
public class WxGoodsPageResponse {

   private List<WxGoodsResponse> allGoodsTabList;

   private Long totalRecords;
}
