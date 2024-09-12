package com.share.dao.dto.reponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "所有商品信息响应类")
public class AllGoodsTabResponse {

    private Integer id;

    @ApiModelProperty(notes = "店铺id")
    private String parentId;

    @ApiModelProperty(notes = "店铺名称")
    private String shopName;

    @ApiModelProperty(notes = "商品名称")
    private String goodsTitle;

    @ApiModelProperty(notes = "商品标签")
    private String goodsLab;

    @ApiModelProperty(notes = "商品类别")
    private String goodsType;

    @ApiModelProperty(notes = "商品原价")
    private String originalPrice;

    @ApiModelProperty(notes = "商品折扣价")
    private String discountedPrice;

    @ApiModelProperty(notes = "开团时间")
    private Date openingTime;

    @ApiModelProperty(notes = "商品主图")
    private String mainImg;

    @ApiModelProperty(notes = "商品详情图")
    private String detailsImg;

    @ApiModelProperty(notes = "商品实拍视频")
    private String video;

    @ApiModelProperty(notes = "资质")
    private String qualifications;

    @ApiModelProperty(notes = "售后说明")
    private String afterSales;
}
