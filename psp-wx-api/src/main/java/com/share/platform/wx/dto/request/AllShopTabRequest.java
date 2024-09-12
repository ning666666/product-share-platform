package com.share.platform.wx.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "查询店铺信息请求类")
public class AllShopTabRequest {

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @ApiModelProperty(notes = "店铺联系电话")
    private String shopPhone;

    @NotNull(message = "当前页数，不为空")
    @ApiModelProperty(notes = "当前页数")
    private Integer page;

    @NotNull(message = "当前页面容量，不为空")
    @ApiModelProperty(notes = "当前页面容量")
    private Integer pageSize;
}
