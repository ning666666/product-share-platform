package com.share.platform.api.controller;

import com.share.platform.api.annotation.RequiresPermissionsDesc;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.dto.request.AllShopTabRequest;
import com.share.platform.api.dto.request.ShopTabRequest;
import com.share.platform.api.dto.request.UpdateShopTabRequest;
import com.share.platform.api.service.ShopTabService;
import com.share.platform.api.utils.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "店铺管理")
@RequestMapping("/shopTab/api/v1")
public class ShopTabController {

    @Autowired
    private ShopTabService shopTabService;

    /**
     * 获取所有店铺信息
     */
    @RequiresPermissions("admin:shopTab:list")
    @ApiOperation(value = "获取所有店铺信息")
    @PostMapping("/all")
    public ResultVo getAllShopTabInfo(@RequestBody AllShopTabRequest allShopTabRequest) {
        return ResultVo.buildData(ResultCode.SUCCESS, shopTabService.getAllShopTabInfo(allShopTabRequest));
    }

    /**
     * 添加店铺
     */
    @RequiresPermissions("admin:shopTab:create")
    @ApiOperation(value = "添加店铺")
    @PostMapping("/add")
    public ResultVo addShopTab(@RequestBody ShopTabRequest shopTabRequest) {
        return shopTabService.addShopTab(shopTabRequest);
    }

    /**
     * 图片上传
     */
    @RequiresPermissions("admin:shopTab:create")
    @ApiOperation(value = "图片上传")
    @PostMapping("/upload")
    public ResultVo shopQualiImageUpload(@RequestParam("file") MultipartFile file) {
        return shopTabService.shopQualiImageUpload(file);
    }

    /**
     * 根据路径删除文件
     */
    @RequiresPermissions("admin:shopTab:create")
    @ApiOperation(value = "根据路径删除文件")
    @GetMapping("/delete/filePath")
    public ResultVo deleteFileByPath(@Validated @RequestParam(value = "filePath") String filePath) {
        return shopTabService.deleteFileByPath(filePath);
    }

    /**
     * 更新店铺信息
     */
    @RequiresPermissions("admin:shopTab:update")
    @ApiOperation(value = "更新店铺信息")
    @PutMapping("/update/{id}")
    public ResultVo updateShopTab(@PathVariable("id") int id, @RequestBody UpdateShopTabRequest updateShopTabRequest) {
        return shopTabService.updateShopTab(id, updateShopTabRequest);
    }

    /**
     * 删除店铺信息
     */
    @RequiresPermissions("admin:shopTab:delete")
    @ApiOperation(value = "删除店铺信息")
    @DeleteMapping("/delete/{id}")
    public ResultVo deleteShopTab(@PathVariable("id") int id) {
        return shopTabService.deleteShopTab(id);
    }
}
