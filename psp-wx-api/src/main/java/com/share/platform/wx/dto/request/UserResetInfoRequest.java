package com.share.platform.wx.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "小程序用户账号密码重置请求类")
public class UserResetInfoRequest {

    @ApiModelProperty(notes = "密码")
    private String password;

    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "验证码,暂时不用")
    private String code;

}
