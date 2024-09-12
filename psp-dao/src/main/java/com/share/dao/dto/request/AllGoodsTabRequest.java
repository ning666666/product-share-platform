package com.share.dao.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "查询商品信息请求类")
public class AllGoodsTabRequest {

    @ApiModelProperty(notes = "商品名称")
    private String goodsName;

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @NotNull(message = "当前页数，不为空")
    @ApiModelProperty(notes = "当前页数")
    private Integer page;

    @NotNull(message = "当前页面容量，不为空")
    @ApiModelProperty(notes = "当前页面容量")
    private Integer pageSize;
}
