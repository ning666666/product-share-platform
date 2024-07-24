package com.share.platform.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.share.platform.api.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@ApiModel(description = "修改店铺信息请求类")
public class UpdateGoodsTabRequest {

    @ApiModelProperty(notes = "店铺id")
    private Integer parentId;

    @ApiModelProperty(notes = "商品标题")
    private String goodsTitle;

    @ApiModelProperty(notes = "商品原价")
    private String originalPrice;

    @ApiModelProperty(notes = "商品折扣价")
    private String discountedPrice;

    @ApiModelProperty(notes = "开团时间", example = "2024-7-10")
    @JsonFormat(pattern = Constant.TIME.DATE, timezone = Constant.TIME.TIME_ZONE)
    private Date openingTime;

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @ApiModelProperty(notes = "商品标签")
    private String goodsLab;

    @ApiModelProperty(notes = "商品类别")
    private String goodsType;

    @ApiModelProperty(notes = "商品主图")
    private String mainImg;

    @ApiModelProperty(notes = "商品详细内容")
    private String detailsImg;

    @ApiModelProperty(notes = "售后内容")
    private String afterSales;
}
