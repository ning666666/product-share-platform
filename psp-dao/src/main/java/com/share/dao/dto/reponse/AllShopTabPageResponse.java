package com.share.dao.dto.reponse;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "所有店铺分页信息响应类")
public class AllShopTabPageResponse {

    private List<AllShopTabResponse> allShopTabList;

    private Long totalRecords;
}
