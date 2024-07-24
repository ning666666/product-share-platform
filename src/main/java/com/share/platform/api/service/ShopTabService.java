package com.share.platform.api.service;

import com.share.platform.api.dto.reponse.AllShopTabResponse;
import com.share.platform.api.dto.request.AllShopTabRequest;
import com.share.platform.api.dto.request.ShopTabRequest;
import com.share.platform.api.dto.request.UpdateShopTabRequest;
import com.share.platform.api.utils.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ShopTabService {

    List<AllShopTabResponse> getAllShopTabInfo(AllShopTabRequest allShopTabRequest);

    ResultVo addShopTab( ShopTabRequest shopTabRequest);

    ResultVo updateShopTab(int id, UpdateShopTabRequest updateShopTabRequest);

    ResultVo deleteShopTab(int id);

    ResultVo shopQualiImageUpload(MultipartFile file);

    ResultVo deleteFileByPath(String filePath);
}
