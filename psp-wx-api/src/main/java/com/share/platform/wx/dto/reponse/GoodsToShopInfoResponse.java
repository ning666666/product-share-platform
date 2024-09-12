package com.share.platform.wx.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "商品所属店铺信息")
public class GoodsToShopInfoResponse {

    @ApiModelProperty(notes = "店铺id")
    private Integer id;

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;
}
