package com.share.platform.api.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.dto.reponse.AllShopTabPageResponse;
import com.share.platform.api.dto.reponse.AllShopTabResponse;
import com.share.platform.api.dto.request.AllShopTabRequest;
import com.share.platform.api.dto.request.ShopTabRequest;
import com.share.platform.api.dto.request.UpdateShopTabRequest;
import com.share.platform.api.mapper.ShopTabMapper;
import com.share.platform.api.model.ShopTab;
import com.share.platform.api.model.ShopTabExample;
import com.share.platform.api.service.ShopTabService;
import com.share.platform.api.utils.AuthSupport;
import com.share.platform.api.utils.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class ShopTabServiceImpl implements ShopTabService {

    @Autowired
    private ShopTabMapper shopTabMapper;

    @Value("${file.upload.path}")
    private String fileSavePath;

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");

    /**
     * 限制图片大小1MB
     */
    private static final long MAX_FILE_SIZE = 1048576;

    @Override
    public AllShopTabPageResponse getAllShopTabInfo(AllShopTabRequest allShopTabRequest) {
        AllShopTabPageResponse allShopTabPage = new AllShopTabPageResponse();
        // 获取当前管理员id作为商家id
        Integer businessId = AuthSupport.adminId();
        PageHelper.startPage(allShopTabRequest.getPage(), allShopTabRequest.getPageSize());
        Page<AllShopTabResponse> allShopTabInfos = shopTabMapper.getAllShopTabInfo(allShopTabRequest, businessId);
        allShopTabPage.setAllShopTabList(allShopTabInfos.getResult());
        allShopTabPage.setTotalRecords(allShopTabInfos.getTotal());
        return allShopTabPage;
    }

    @Override
    public ResultVo addShopTab(ShopTabRequest shopTabRequest) {
        // 保存店铺信息
        ShopTab shopTab = new ShopTab();
        //shopTab.setShopCode(ProductAndTabCodeGenerator.generateProductCode());
        shopTab.setBusinessId(AuthSupport.currentUser().getId());
        shopTab.setShopName(shopTabRequest.getShopName());
        shopTab.setShopAdd(shopTabRequest.getShopAdd());
        shopTab.setShopContacts(shopTabRequest.getShopContacts());
        shopTab.setShopPhone(shopTabRequest.getShopPhone());
        shopTab.setShopQualificate(shopTabRequest.getImageAddress());
        shopTab.setCreateTime(new Date());
        shopTab.setUpdateTime(new Date());
        shopTab.setCreatedBy(AuthSupport.userName());
        shopTab.setUpdateBy(AuthSupport.userName());
        shopTabMapper.insertSelective(shopTab);

        log.info("shop tab add success");
        return ResultVo.buildSuccess();
    }

    @Override
    public ResultVo shopQualiImageUpload(MultipartFile file) {
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

    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return lastIndexOfDot != -1 ? fileName.substring(lastIndexOfDot) : "";
    }


    @Override
    public ResultVo deleteFileByPath(String filePath) {
        try {
            if (filePath != null) {
                String[] parts = filePath.split("/");
                if (parts.length > 2) {
                    // 第二个'/'之后的内容是parts数组的第三个元素（索引为2）
                    String filename = parts[2];
                    File file = new File(fileSavePath + filename);
                    if (file.exists()) {
                        // 尝试删除图片
                        boolean isDeleted = file.delete();
                        if (isDeleted) {
                            return ResultVo.buildCode(ResultCode.IMAGE_DELETE_SUCCESS);
                        } else {
                            return ResultVo.buildCode(ResultCode.IMAGE_DELETE_FAIL);
                        }
                    } else {
                        return ResultVo.buildCode(ResultCode.IMAGE_NOT_EXIST);
                    }
                } else {
                    return ResultVo.buildCode(ResultCode.IMAGE_PATH_NOT_COMPLETE);
                }
            } else {
                return ResultVo.buildCode(ResultCode.IMAGE_NOT_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
        }
    }

    @Override
    public ResultVo updateShopTab(int id, UpdateShopTabRequest updateShopTabRequest) {
        ShopTabExample shopTabExample = new ShopTabExample();
        ShopTabExample.Criteria shopCriteria = shopTabExample.createCriteria();
        shopCriteria.andIdEqualTo(id);

        ShopTab shopTab = new ShopTab();
        BeanUtils.copyProperties(updateShopTabRequest, shopTab);
        shopTab.setUpdateTime(new Date());
        shopTab.setUpdateBy(AuthSupport.userName());

        int result = shopTabMapper.updateByExampleSelective(shopTab, shopTabExample);
        if (result == 1) {
            return ResultVo.buildCode(ResultCode.UPDATE_SUCCESS);
        } else {
            return ResultVo.buildCode(ResultCode.UPDATE_FAIL);
        }
    }

    @Override
    public ResultVo deleteShopTab(int id) {
        try {
            ShopTab shopTab = shopTabMapper.selectByPrimaryKey(id);
            if (shopTab != null) {
                String shopQualificate = shopTab.getShopQualificate();
                if (shopQualificate != null && shopQualificate != "") {
                    // 删除图片
                    File file = new File(fileSavePath + shopQualificate);
                    // 检查图片是否存在
                    if (file.exists()) {
                        // 尝试删除图片
                        boolean isDeleted = file.delete();
                        if (!isDeleted) {
                            return ResultVo.buildCode(ResultCode.IMAGE_DELETE_FAIL);
                        }
                    }
                }
                // 删除当前记录
                int result = shopTabMapper.deleteByPrimaryKey(id);
                if (result == 1) {
                    return ResultVo.buildCode(ResultCode.DELETE_SUCCESS);
                } else {
                    return ResultVo.buildCode(ResultCode.DELETE_FAIL);
                }
            } else {
                return ResultVo.buildCode(ResultCode.SHOPTAB_INFO_NULL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
        }
    }
}
