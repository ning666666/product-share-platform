package com.share.platform.api.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "所有店铺分页信息响应类")
public class AllShopTabPageResponse {

    private List<AllShopTabResponse> allShopTabList;

    private Long totalRecords;
}
