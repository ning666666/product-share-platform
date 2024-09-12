package com.share.dao.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "所有店铺信息响应类")
public class AllShopTabResponse {

    private Integer id;

    @ApiModelProperty(notes = "店铺编码")
    private String shopCode;

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @ApiModelProperty(notes = "店铺地址")
    private String shopAdd;

    @ApiModelProperty(notes = "店铺联系人")
    private String shopContacts;

    @ApiModelProperty(notes = "店铺联系电话")
    private String shopPhone;
}
