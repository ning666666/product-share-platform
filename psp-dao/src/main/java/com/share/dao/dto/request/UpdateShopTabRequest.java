package com.share.dao.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "修改店铺信息请求类")
public class UpdateShopTabRequest {

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @ApiModelProperty(notes = "店铺联系电话")
    private String shopPhone;

    @ApiModelProperty(notes = "店铺地址")
    private String shopAdd;

    @ApiModelProperty(notes = "店铺联系人")
    private String shopContacts;
}
