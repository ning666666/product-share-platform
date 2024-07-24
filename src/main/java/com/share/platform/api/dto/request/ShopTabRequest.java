package com.share.platform.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(description = "新增店铺信息请求类")
public class ShopTabRequest {

    @ApiModelProperty(notes = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    @Length(message = "店铺名称不能超过{max}字符", max = 100)
    private String shopName;

    @ApiModelProperty(notes = "店铺联系电话")
    @NotBlank(message = "店铺联系电话不能为空")
    @Length(message = "店铺联系电话不能超过{max}字符", max = 100)
    private String shopPhone;

    @ApiModelProperty(notes = "店铺地址")
    @NotBlank(message = "店铺地址不能为空")
    @Length(message = "店铺地址不能超过{max}字符", max = 100)
    private String shopAdd;

    @ApiModelProperty(notes = "店铺联系人")
    @NotBlank(message = "店铺联系人不能为空")
    @Length(message = "店铺联系人不能超过{max}字符", max = 100)
    private String shopContacts;

    @ApiModelProperty(notes = "图片地址")
    private String imageAddress;
}
