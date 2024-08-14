package com.share.platform.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "小程序用户注册请求类")
public class UserRegisterInfoRequest {

    @ApiModelProperty(notes = "用户名")
    private String username;

    @ApiModelProperty(notes = "密码")
    private String password;

    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "验证码,暂时不用")
    private String code;

    @ApiModelProperty(notes = "微信编码")
    private String wxCode;
}
