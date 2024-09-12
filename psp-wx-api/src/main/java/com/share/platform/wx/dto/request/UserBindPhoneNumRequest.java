package com.share.platform.wx.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "小程序用户账号密码重置请求类")
public class UserBindPhoneNumRequest {

    @ApiModelProperty(notes = "加密数据")
    private String encryptedData;

    @ApiModelProperty(notes = "iv")
    private String iv;
}
