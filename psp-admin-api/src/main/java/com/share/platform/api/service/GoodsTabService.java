package com.share.platform.api.service;

import com.share.platform.api.dto.reponse.AllGoodsTabPageResponse;
import com.share.platform.api.dto.reponse.AllGoodsTabResponse;
import com.share.platform.api.dto.reponse.GoodsToShopInfoResponse;
import com.share.platform.api.dto.request.AllGoodsTabRequest;
import com.share.platform.api.dto.request.GoodsTabRequest;
import com.share.platform.api.dto.request.UpdateGoodsTabRequest;
import com.share.platform.api.model.GoodsTab;
import com.share.platform.api.utils.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface GoodsTabService {

    AllGoodsTabPageResponse getAllGoodsTabInfo(AllGoodsTabRequest allGoodsTabRequest);

    ResultVo addGoodsTab(GoodsTabRequest goodsTabRequest);

    List<GoodsToShopInfoResponse> getShopInfoByUser();

    ResultVo goodsImageUpload(List<MultipartFile> files);

    ResultVo goodsDetailImageUpload(List<MultipartFile> files);

    ResultVo goodsSaleAfterImageUpload(List<MultipartFile> files);

    ResultVo updateGoodsTab(int id, UpdateGoodsTabRequest updateGoodsTabRequest);

    ResultVo deleteGoodsTab(int id);

    ResultVo goodsqualiImageUpload(MultipartFile file);
}
