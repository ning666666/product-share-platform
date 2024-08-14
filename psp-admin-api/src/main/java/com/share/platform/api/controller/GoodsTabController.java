package com.share.platform.api.controller;

import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.dto.request.AllGoodsTabRequest;
import com.share.platform.api.dto.request.GoodsTabRequest;
import com.share.platform.api.dto.request.UpdateGoodsTabRequest;
import com.share.platform.api.service.GoodsTabService;
import com.share.platform.api.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Api(tags = "商品管理")
@RequestMapping("/goodsTab/api/v1")
public class GoodsTabController {

    @Autowired
    private GoodsTabService goodsTabService;

    /**
     * 获取所有商品信息
     */
    @RequiresPermissions("admin:goodsTab:list")
    @ApiOperation(value = "获取所有商品信息")
    @PostMapping("/all")
    public ResultVo getAllGoodsTabInfo(@RequestBody AllGoodsTabRequest allGoodsTabRequest) {
        return ResultVo.buildData(ResultCode.SUCCESS, goodsTabService.getAllGoodsTabInfo(allGoodsTabRequest));
    }

    /**
     * 添加商品
     */
    @RequiresPermissions("admin:goodsTab:create")
    @ApiOperation(value = "添加商品")
    @PostMapping("/add")
    public ResultVo addGoodsTab(@RequestBody GoodsTabRequest goodsTabRequest) {
        return goodsTabService.addGoodsTab(goodsTabRequest);
    }

    /**
     * 查询商品所属店铺
     */
    @RequiresPermissions("admin:goodsTab:create")
    @ApiOperation(value = "查询商品所属店铺")
    @GetMapping("/getShopInfoByUser")
    public ResultVo getShopInfoByUser() {
        return ResultVo.buildData(ResultCode.SUCCESS, goodsTabService.getShopInfoByUser());
    }

    /**
     * 商品图片上传（限制最多三张）
     */
    @RequiresPermissions("admin:goodsTab:create")
    @ApiOperation(value = "商品图片上传")
    @PostMapping("/goods/upload")
    public ResultVo goodsImageUpload(@RequestParam("files") List<MultipartFile> files) {
        return goodsTabService.goodsImageUpload(files);
    }

    /**
     * 资质图片上传
     */
    @RequiresPermissions("admin:goodsTab:create")
    @ApiOperation(value = "资质图片上传")
    @PostMapping("/goods/quali/upload")
    public ResultVo goodsqualiImageUpload(@RequestParam("file") MultipartFile file) {
        return goodsTabService.goodsqualiImageUpload(file);
    }

    /**
     * 商品详细图片上传
     */
    @RequiresPermissions("admin:goodsTab:create")
    @ApiOperation(value = "商品详细图片上传")
    @PostMapping("/goods/detail/upload")
    public ResultVo goodsDetailImageUpload(@RequestParam("files") List<MultipartFile> files) {
        return goodsTabService.goodsDetailImageUpload(files);
    }

    /**
     * 售后内容图片上传
     */
    @RequiresPermissions("admin:goodsTab:create")
    @ApiOperation(value = "售后内容图片上传")
    @PostMapping("/goods/saleAfter/upload")
    public ResultVo goodsSaleAfterImageUpload(@RequestParam("files") List<MultipartFile> files) {
        return goodsTabService.goodsSaleAfterImageUpload(files);
    }

    /**
     * 更新商品信息
     */
    @RequiresPermissions("admin:goodsTab:update")
    @ApiOperation(value = "更新商品信息")
    @PutMapping("/update/{id}")
    public ResultVo updateGoodsTab(@PathVariable("id") int id, @RequestBody UpdateGoodsTabRequest updateGoodsTabRequest) {
        return goodsTabService.updateGoodsTab(id, updateGoodsTabRequest);
    }

    /**
     * 删除商品信息
     */
    @RequiresPermissions("admin:goodsTab:update")
    @ApiOperation(value = "删除商品信息")
    @DeleteMapping("/delete/{id}")
    public ResultVo deleteGoodsTab(@PathVariable("id") int id) {
        return goodsTabService.deleteGoodsTab(id);
    }
}
