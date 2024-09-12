package com.share.platform.wx.controller;

import com.share.platform.wx.annotation.LoginUser;
import com.share.platform.wx.constant.ResultCode;
import com.share.platform.wx.dto.reponse.WxGoodsPageResponse;
import com.share.platform.wx.model.PspCategory;
import com.share.platform.wx.service.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 首页服务
 */
@RestController
@RequestMapping("/wx/home")
@Validated
@Api(tags = "WX-首页")
public class WxHomeController {
	private static final Logger logger = LoggerFactory.getLogger(WxHomeController.class);

	@Autowired
	private PspAdService adService;

	@Autowired
	private PspCategoryService categoryService;

	@Autowired
	private PspArticleService articleService;

	@Autowired
	private GoodsService goodsTabService;

	private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

	private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

	@SuppressWarnings("unused")
	private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS,
			WORK_QUEUE, HANDLER);

	@GetMapping("/cache")
	public Object cache(@NotNull String key) {
		logger.info("【请求开始】缓存已清除,请求参数,key:{}", key);

		if (!key.equals("Dts_cache")) {
			logger.error("缓存已清除出错:非本平台标识！！！");
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}

		// 清除缓存
		HomeCacheManager.clearAll();

		logger.info("【请求结束】缓存已清除成功!");
		return ResultVo.buildCode(ResultCode.CACHE_CLEAR_SUCCESS);
	}

	/**
	 * 首页数据
	 * 
	 * @param userId
	 *            当用户已经登录时，非空。为登录状态为null
	 * @return 首页数据
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/index")
	public Object index(@LoginUser Integer userId) {
		logger.info("【请求开始】访问首页,请求参数,userId:{}", userId);

		Map<String, Object> data = new HashMap<String, Object>();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		// 先查询和用户有关的信息
		try {
			Callable<List> bannerListCallable = () -> adService.queryIndex();
			Callable<List> articleListCallable = () -> articleService.queryList(0, 5, "create_time", "desc");
			Callable<List> channelListCallable = () -> categoryService.queryChannel();
			Callable<List> newGoodsListCallable = () -> goodsTabService.queryByCondition(0, SystemConfig.getNewLimit()).getAllGoodsTabList();

			FutureTask<List> bannerTask = new FutureTask<>(bannerListCallable);
			FutureTask<List> articleTask = new FutureTask<>(articleListCallable);
			FutureTask<List> channelTask = new FutureTask<>(channelListCallable);
			FutureTask<List> goodsTask = new FutureTask<>(newGoodsListCallable);

			executorService.submit(bannerTask);
			executorService.submit(articleTask);
			executorService.submit(channelTask);
			executorService.submit(goodsTask);

			data.put("banner", bannerTask.get());
			data.put("articles", articleTask.get());
			data.put("channel", channelTask.get());
			data.put("goods", goodsTask.get());

			// 缓存数据首页缓存数据
			HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
			executorService.shutdown();
		} catch (Exception e) {
			logger.error("首页信息获取失败：{}", e.getMessage());
			e.printStackTrace();
		}

		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	@SuppressWarnings("rawtypes")
	private List<Map> getCategoryList() {
		List<Map> categoryList = new ArrayList<>();
		List<PspCategory> catL1List = categoryService.queryL1WithoutRecommend(0, SystemConfig.getCatlogListLimit());
		for (PspCategory catL1 : catL1List) {
			WxGoodsPageResponse wxGoodsPageResponse = goodsTabService.queryWxCategoryByCondition(catL1.getId(), 0, SystemConfig.getCatlogMoreLimit());

			Map<String, Object> catGoods = new HashMap<>();
			catGoods.put("id", catL1.getId());
			catGoods.put("name", catL1.getName());
			catGoods.put("goodsList", wxGoodsPageResponse.getAllGoodsTabList());
			categoryList.add(catGoods);
		}
		return categoryList;
	}
}