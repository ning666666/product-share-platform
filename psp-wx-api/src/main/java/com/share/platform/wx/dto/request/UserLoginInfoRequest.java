package com.share.platform.wx.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户登录请求类")
public class UserLoginInfoRequest {

    @ApiModelProperty(notes = "用户名")
    private String username;

    @ApiModelProperty(notes = "密码")
    private String password;
}
