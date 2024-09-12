package com.share.dao.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.share.dao.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "新增商品信息请求类")
public class GoodsTabRequest {

    @ApiModelProperty(notes = "店铺id")
    @NotNull(message = "店铺id不能为空")
    private Integer parentId;

    @ApiModelProperty(notes = "商品标题")
    @NotBlank(message = "商品标题不能为空")
    @Length(message = "商品标题不能超过{max}字符", max = 100)
    private String goodsTitle;

    @ApiModelProperty(notes = "商品原价")
    @NotBlank(message = "商品原价不能为空")
    @Length(message = "商品原价不能超过{max}字符", max = 100)
    private String originalPrice;

    @ApiModelProperty(notes = "商品折扣价")
    @NotBlank(message = "商品折扣价不能为空")
    @Length(message = "商品折扣价不能超过{max}字符", max = 100)
    private String discountedPrice;

    @ApiModelProperty(notes = "开团时间", example = "2024-7-10")
    @JsonFormat(pattern = Constant.TIME.DATE, timezone = Constant.TIME.TIME_ZONE)
    @NotNull(message = "开团时间不能为空")
    private Date openingTime;

    @ApiModelProperty(notes = "店铺名称")
    @NotBlank(message = "店铺名称不能为空")
    @Length(message = "店铺名称不能超过{max}字符", max = 100)
    private String shopName;

    @ApiModelProperty(notes = "商品标签")
    @NotBlank(message = "商品标签不能为空")
    @Length(message = "商品标签不能超过{max}字符", max = 100)
    private String goodsLab;

    @ApiModelProperty(notes = "商品类别")
    @NotBlank(message = "商品类别不能为空")
    @Length(message = "商品类别不能超过{max}字符", max = 100)
    private String goodsType;

    @ApiModelProperty(notes = "商品主图")
    private String mainImg;

    @ApiModelProperty(notes = "商品详细内容")
    private String detailsImg;

    @ApiModelProperty(notes = "售后内容")
    private String afterSales;
}
