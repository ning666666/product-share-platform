package com.share.platform.api.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.dto.reponse.*;
import com.share.platform.api.dto.request.AllGoodsTabRequest;
import com.share.platform.api.dto.request.GoodsTabRequest;
import com.share.platform.api.dto.request.UpdateGoodsTabRequest;
import com.share.platform.api.mapper.GoodsTabMapper;
import com.share.platform.api.mapper.ShopTabMapper;
import com.share.platform.api.model.GoodsTab;
import com.share.platform.api.model.GoodsTabExample;
import com.share.platform.api.model.ShopTab;
import com.share.platform.api.model.ShopTabExample;
import com.share.platform.api.service.GoodsTabService;
import com.share.platform.api.utils.AuthSupport;
import com.share.platform.api.utils.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Repository
@Slf4j
public class GoodsTabServiceImpl implements GoodsTabService {

    @Autowired
    private GoodsTabMapper goodsTabMapper;

    @Autowired
    private ShopTabMapper shopTabMapper;

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");


    @Value("${file.upload.path}")
    private String fileSavePath;

    /**
     * 限制图片大小1MB
     */
    private static final long MAX_FILE_SIZE = 1048576;


    @Override
    public AllGoodsTabPageResponse getAllGoodsTabInfo(AllGoodsTabRequest allGoodsTabRequest) {
        AllGoodsTabPageResponse allGoodsTabPageResponse = new AllGoodsTabPageResponse();
        PageHelper.startPage(allGoodsTabRequest.getPage(), allGoodsTabRequest.getPageSize());
        Page<AllGoodsTabResponse> allGoodsTabInfo = goodsTabMapper.getAllGoodsTabInfo(allGoodsTabRequest);
        List<AllGoodsTabResponse> allGoodsTabInfoResult = allGoodsTabInfo.getResult();
        Integer businessId = AuthSupport.adminId();
        // 获取当前商家所属店铺
        ShopTabExample shopTabExample = new ShopTabExample();
        shopTabExample.createCriteria().andBusinessIdEqualTo(businessId);
        List<ShopTab> shopTabs = shopTabMapper.selectByExample(shopTabExample);
        Set<Integer> shopTabIdSet = shopTabs.stream().map(ShopTab::getId).collect(Collectors.toSet());
        allGoodsTabInfoResult = allGoodsTabInfoResult.stream().filter(e -> !shopTabIdSet.contains(e.getParentId())).collect(Collectors.toList());
        allGoodsTabPageResponse.setAllGoodsTabList(allGoodsTabInfoResult);
        allGoodsTabPageResponse.setTotalRecords((long) allGoodsTabInfoResult.size());
        return allGoodsTabPageResponse;
    }

    @Override
    public ResultVo addGoodsTab(GoodsTabRequest goodsTabRequest) {
        // 保存商品信息
        GoodsTab goodsTab = new GoodsTab();
        goodsTab.setParentId(goodsTabRequest.getParentId());
        goodsTab.setShopName(goodsTabRequest.getShopName());
        goodsTab.setGoodsTitle(goodsTabRequest.getGoodsTitle());
        goodsTab.setOriginalPrice(goodsTabRequest.getOriginalPrice());
        goodsTab.setDiscountedPrice(goodsTabRequest.getDiscountedPrice());
        goodsTab.setOpeningTime(goodsTabRequest.getOpeningTime());
        goodsTab.setGoodsLab(goodsTabRequest.getGoodsLab());
        goodsTab.setGoodsType(goodsTabRequest.getGoodsType());
        goodsTab.setMainImg(goodsTabRequest.getMainImg());
        goodsTab.setDetailsImg(goodsTabRequest.getDetailsImg());
        goodsTab.setAfterSales(goodsTab.getAfterSales());
        goodsTab.setCreateTime(new Date());
        goodsTab.setUpdateTime(new Date());
        goodsTab.setCreatedBy(AuthSupport.userName());
        goodsTab.setUpdateBy(AuthSupport.userName());
        goodsTabMapper.insertSelective(goodsTab);

        log.info("goods tab add success");
        return ResultVo.buildSuccess();
    }

    @Override
    public List<GoodsToShopInfoResponse> getShopInfoByUser() {
        // 所有店铺
        List<GoodsToShopInfoResponse> shopInfoByUserAll = shopTabMapper.getShopInfoByUser();
        // 获取当前商家所属店铺
        Integer businessId = AuthSupport.adminId();
        ShopTabExample shopTabExample = new ShopTabExample();
        shopTabExample.createCriteria().andBusinessIdEqualTo(businessId);
        List<ShopTab> shopTabs = shopTabMapper.selectByExample(shopTabExample);
        Set<Integer> shopTabIdSet = shopTabs.stream().map(ShopTab::getId).collect(Collectors.toSet());
        shopInfoByUserAll = shopInfoByUserAll.stream().filter(e -> !shopTabIdSet.contains(e.getId())).collect(Collectors.toList());
        return shopInfoByUserAll;
    }

    @Override
    public ResultVo goodsImageUpload(List<MultipartFile> files) {
        return batchUploadFile(files, 3);
    }

    public void deleteFile(List<String> successUploadList) {
        if (!CollectionUtils.isEmpty(successUploadList)) {
            for (String filePath : successUploadList) {
                String[] parts = filePath.split("/");
                if (parts.length > 2) {
                    String filename = parts[2];
                    File file = new File(fileSavePath + filename);
                    if (file.exists()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            log.info("Deleted file: {}", filePath);
                        } else {
                            log.error("Failed to delete file: {}, it may not exist or you may not have permission", filePath);
                        }
                    } else {
                        log.error("File does not exist: {}", filePath);
                    }
                } else {
                    log.error("File path not complete: {}", filePath);
                }
            }
        }
    }

    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return lastIndexOfDot != -1 ? fileName.substring(lastIndexOfDot) : "";
    }

    @Override
    public ResultVo goodsDetailImageUpload(List<MultipartFile> files) {
        return batchUploadFile(files, 8);
    }

    private ResultVo<?> batchUploadFile(List<MultipartFile> files, int count) {
        try {
            // 不为图片的文件名
            List<String> extensionList = new ArrayList<>();
            // 超出规定大小的文件名
            List<String> sizeList = new ArrayList<>();
            // 成功上传的文件路径
            List<String> successUploadList = new ArrayList<>();

            if (files.size() > count) {
                log.error("上传的图片数量超过限制，最多允许" + count + "张");
                return ResultVo.buildCode(ResultCode.UPLOAD_FAILED, "上传的图片数量超过限制，最多允许" + count +"张");
            }

            // 遍历文件列表，处理每个文件
            for (MultipartFile file : files) {
                // 获取文件名
                String fileName = file.getOriginalFilename();
                // 检查文件后缀名
                String fileExtension = getFileExtension(fileName);
                if (ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension)) {
                    // 检查文件大小
                    long fileSize = file.getSize();
                    if (fileSize < MAX_FILE_SIZE) {
                        // 生成新文件名
                        String newFileName = UUID.randomUUID().toString() + fileExtension;
                        // 创建文件目录
                        File dest = new File(fileSavePath + newFileName);
                        if (!dest.getParentFile().exists()) {
                            dest.getParentFile().mkdirs();
                        }
                        // 保存文件
                        file.transferTo(dest);
                        // 获取保存后的文件路径
                        //String filePath = fileSavePath + newFileName;
                        String filePath = "/image/" + newFileName;

                        log.info(String.format("image upload success address:{%s}", filePath));
                        // 保存成功上传的文件路径
                        successUploadList.add(filePath);
                    } else {
                        log.error(String.format("image upload fail size:{%s}", fileSize));
                        sizeList.add(fileName);
                        continue;
                    }
                } else {
                    log.error(String.format("image upload fail type:{%s}", fileExtension));
                    extensionList.add(fileName);
                    continue;
                }
            }

            // 响应
            if (!CollectionUtils.isEmpty(extensionList)) {
                // 删除上传成功的图片
                deleteFile(successUploadList);
                return ResultVo.buildCode(ResultCode.AGAIN_FAILED, "存在不允许的图片类型，只允许：" + ALLOWED_IMAGE_EXTENSIONS);
            }
            if (!CollectionUtils.isEmpty(extensionList)) {
                deleteFile(successUploadList);
                return ResultVo.buildCode(ResultCode.AGAIN_FAILED, "存在过大图片，最大允许：" + MAX_FILE_SIZE + "字节");
            }
            log.info("all image upload success");
            return ResultVo.buildData(ResultCode.UPLOAD_SUCCESS, successUploadList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
        }
    }

    @Override
    public ResultVo goodsSaleAfterImageUpload(List<MultipartFile> files) {
        return batchUploadFile(files, 5);
    }

    public ResultVo<?> fileUpload(MultipartFile file) {
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 检查文件后缀名
            String fileExtension = getFileExtension(fileName);
            if (ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension)) {
                // 检查文件大小
                long fileSize = file.getSize();
                if (fileSize < MAX_FILE_SIZE) {
                    // 生成新文件名
                    String newFileName = UUID.randomUUID().toString() + fileExtension;
                    // 创建文件目录
                    File dest = new File(fileSavePath + newFileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    // 保存文件
                    file.transferTo(dest);
                    // 获取保存后的文件路径
                    //String filePath = fileSavePath + newFileName;
                    String filePath = "/image/" + newFileName;

                    log.info(String.format("image upload success address:{%s}", filePath));
                    return ResultVo.buildData(ResultCode.UPLOAD_SUCCESS, filePath);
                } else {
                    log.error(String.format("image upload fail size:{%s}", fileSize));
                    return ResultVo.buildCode(ResultCode.UPLOAD_FAILED, "图片太大，最大允许：" + MAX_FILE_SIZE + "字节");
                }
            } else {
                log.error(String.format("image upload fail type:{%s}", fileExtension));
                return ResultVo.buildCode(ResultCode.UPLOAD_FAILED, "不允许的图片类型，只允许：" + ALLOWED_IMAGE_EXTENSIONS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
        }
    }

    @Override
    public ResultVo updateGoodsTab(int id, UpdateGoodsTabRequest updateGoodsTabRequest) {
        GoodsTabExample goodsTabExample = new GoodsTabExample();
        GoodsTabExample.Criteria goodsCriteria = goodsTabExample.createCriteria();
        goodsCriteria.andIdEqualTo(id);

        GoodsTab goodsTab = new GoodsTab();
        BeanUtils.copyProperties(updateGoodsTabRequest, goodsTab);
        goodsTab.setUpdateTime(new Date());
        goodsTab.setUpdateBy(AuthSupport.userName());

        int result = goodsTabMapper.updateByExampleSelective(goodsTab, goodsTabExample);
        if (result == 1) {
            return ResultVo.buildCode(ResultCode.UPDATE_SUCCESS);
        } else {
            return ResultVo.buildCode(ResultCode.UPDATE_FAIL);
        }
    }

    @Override
    public ResultVo deleteGoodsTab(int id) {
        try {
            GoodsTab goodsTab = goodsTabMapper.selectByPrimaryKey(id);
            if (goodsTab != null) {
                // 删除商品主图
                String mainImg = goodsTab.getMainImg();
                if (mainImg != null && mainImg != "") {
                    String[] imgs = mainImg.split(",");
                    for (String img : imgs) {
                        if (deleteImg(img))
                            return ResultVo.buildCode(ResultCode.IMAGE_DELETE_FAIL);
                    }
                }
                // 删除商品详细
                String detailsImg = goodsTab.getDetailsImg();
                if (detailsImg != null && detailsImg != "") {
                    String[] detailsImgs = detailsImg.split(",");
                    for (String img : detailsImgs) {
                        if (deleteImg(img))
                            return ResultVo.buildCode(ResultCode.IMAGE_DELETE_FAIL);
                    }
                }
                // 删除售后内容
                String afterSales = goodsTab.getAfterSales();
                if (afterSales != null && afterSales != "") {
                    String[] afterSalesImg = afterSales.split(",");
                    for (String img : afterSalesImg) {
                        if (deleteImg(img))
                            return ResultVo.buildCode(ResultCode.IMAGE_DELETE_FAIL);
                    }
                }
                // 删除当前记录
                int result = goodsTabMapper.deleteByPrimaryKey(id);
                if (result == 1) {
                    return ResultVo.buildCode(ResultCode.DELETE_SUCCESS);
                } else {
                    return ResultVo.buildCode(ResultCode.DELETE_FAIL);
                }
            } else {
                return ResultVo.buildCode(ResultCode.GOODS_INFO_NULL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
        }
    }

    @Override
    public ResultVo goodsqualiImageUpload(MultipartFile file) {
        return fileUpload(file);
    }


    public boolean deleteImg(String img) {
        // 删除图片
        String[] parts = img.split("/");
        if (parts.length > 2) {
            String filename = parts[2];
            File file = new File(fileSavePath + filename);
            // 检查图片是否存在
            if (file.exists()) {
                // 尝试删除图片
                boolean isDeleted = file.delete();
                if (!isDeleted) {
                    return true;
                }
            }
        }
        return false;
    }
}
