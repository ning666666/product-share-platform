package com.share.platform.api.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "所有商品信息响应类")
public class AllGoodsTabResponse {

    private Integer id;

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @ApiModelProperty(notes = "商品名称")
    private String goodsTitle;

    @ApiModelProperty(notes = "商品标签")
    private String goodsLab;

    @ApiModelProperty(notes = "商品类别")
    private String goodsType;
}
