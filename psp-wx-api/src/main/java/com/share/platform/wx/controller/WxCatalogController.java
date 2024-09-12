package com.share.platform.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.share.platform.wx.constant.ResultCode;
import com.share.platform.wx.dto.reponse.WxGoodsPageResponse;
import com.share.platform.wx.model.PspCategory;
import com.share.platform.wx.service.GoodsService;
import com.share.platform.wx.service.HomeCacheManager;
import com.share.platform.wx.service.PspCategoryService;
import com.share.platform.wx.system.SystemConfig;
import com.share.platform.wx.util.ResultVo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类目服务
 */
@RestController
@RequestMapping("/wx/catalog")
@Validated
@Api(tags = "WX-商品分类")
public class WxCatalogController {
	private static final Logger logger = LoggerFactory.getLogger(WxCatalogController.class);

	@Autowired
	private PspCategoryService categoryService;

	@Autowired
	private GoodsService goodsTabService;
	/**
	 * 分类详情
	 *
	 * @param id
	 *            分类类目ID。 如果分类类目ID是空，则选择第一个分类类目。 需要注意，这里分类类目是一级类目
	 * @return 分类详情
	 */
	@GetMapping("/index")
	public Object index(Integer id) {
		logger.info("【请求开始】分类详情,请求参数,id:{}", id);
		// 所有一级分类目录
		List<PspCategory> l1CatList = categoryService.queryL1();

		// 当前一级分类目录
		PspCategory currentCategory = null;
		if (id != null) {
			currentCategory = categoryService.findById(id);
		} else {
			currentCategory = l1CatList.get(0);
		}

		// 当前一级分类目录对应的二级分类目录
		//List<PspCategory> currentSubCategory = null;
		//if (null != currentCategory) {
		//	currentSubCategory = categoryService.queryByPid(currentCategory.getId());
		//}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("categoryList", l1CatList);
		data.put("currentCategory", currentCategory);
		//data.put("currentSubCategory", currentSubCategory);

		logger.info("【请求结束】分类详情,响应结果：{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 所有分类数据
	 *
	 * @return 所有分类数据
	 */
	@GetMapping("/all")
	public Object queryAll() {
		logger.info("【请求开始】所有分类查询...");
		// 优先从缓存中读取
		if (HomeCacheManager.hasData(HomeCacheManager.CATALOG)) {
			return ResultVo.buildData(ResultCode.SUCCESS, HomeCacheManager.getCacheData(HomeCacheManager.CATALOG));
		}

		// 所有一级分类目录
		List<PspCategory> l1CatList = categoryService.queryL1();

		// 所有子分类列表
		//Map<Integer, List<PspCategory>> allList = new HashMap<>();
		//List<PspCategory> sub;
		//for (PspCategory category : l1CatList) {
		//	sub = categoryService.queryByPid(category.getId());
		//	allList.put(category.getId(), sub);
		//}

		// 当前一级分类目录
		PspCategory currentCategory = l1CatList.get(0);

		// 当前一级分类目录对应的二级分类目录(修改为一级分类对应的商品)
		//List<PspCategory> currentSubCategory = null;
		//if (null != currentCategory) {
		//	currentSubCategory = categoryService.queryByPid(currentCategory.getId());
		//}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("categoryList", l1CatList);
		//data.put("allList", allList);
		data.put("currentCategory", currentCategory);
		//data.put("currentSubCategory", currentSubCategory);

		// 缓存数据
		try {
			HomeCacheManager.loadData(HomeCacheManager.CATALOG, data);
		} catch (Exception e) {
			logger.error("所有分类查询出错：缓存分类数据错误：{}", e.getMessage());
			e.printStackTrace();
		}

		logger.info("【请求结束】所有分类查询,响应结果：{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 当前分类栏目
	 *
	 * @param id
	 *            分类类目ID
	 * @return 当前分类栏目
	 */
	@GetMapping("/current")
	public Object current(@NotNull Integer id) {
		logger.info("【请求开始】当前分类栏目查询,id:{}", id);

		// 当前分类
		PspCategory currentCategory = categoryService.findById(id);
		//List<PspCategory> currentSubCategory = categoryService.queryByPid(currentCategory.getId());

		// 当前分类对应商品
		WxGoodsPageResponse wxGoodsPageResponse = goodsTabService.queryWxCategoryByCondition(currentCategory.getId(), 0, SystemConfig.getCatlogMoreLimit());

		// 响应数据归纳
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("currentCategory", currentCategory);
		data.put("goodsList", wxGoodsPageResponse.getAllGoodsTabList());

		logger.info("【请求结束】当前分类栏目查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}
}