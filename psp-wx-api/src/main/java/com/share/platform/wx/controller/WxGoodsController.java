package com.share.platform.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.share.platform.wx.annotation.LoginUser;
import com.share.platform.wx.constant.ResultCode;
import com.share.platform.wx.dto.reponse.WxGoodsResponse;
import com.share.platform.wx.mapper.GoodsTabMapper;
import com.share.platform.wx.model.GoodsTab;
import com.share.platform.wx.model.PspCategory;
import com.share.platform.wx.service.GoodsService;
import com.share.platform.wx.service.PspCategoryService;
import com.share.platform.wx.util.ResultVo;
import com.share.platform.wx.validator.Order;
import com.share.platform.wx.validator.Sort;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 商品服务
 */
@RestController
@RequestMapping("/wx/goods")
@Validated
@Api(tags = "WX-商品")
public class WxGoodsController {
	private static final Logger logger = LoggerFactory.getLogger(WxGoodsController.class);

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsTabMapper goodsTabMapper;

	@Autowired
	private PspCategoryService categoryService;

	private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

	private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

	private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS,
			WORK_QUEUE, HANDLER);

	/**
	 * 商品详情
	 * <p>
	 * 用户可以不登录。 如果用户登录，则记录用户足迹以及返回用户收藏信息。
	 *
	 * @param userId
	 *            用户ID
	 * @param id
	 *            商品ID
	 * @return 商品详情
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("detail")
	public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
		logger.info("【请求开始】商品详情,请求参数,userId:{},id:{}", userId, id);

		// 商品信息
		GoodsTab info = goodsService.findById(id);
		Map<String, Object> data = new HashMap<>();

		try {
			data.put("info", info);
		} catch (Exception e) {
			logger.error("获取商品详情出错:{}", e.getMessage());
			e.printStackTrace();
		}

		logger.info("【请求结束】获取商品详情成功!");// 这里不打印返回的信息，因为此接口查询量大，太耗日志空间
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 商品分类类目
	 *
	 * @param id
	 *            分类类目ID
	 * @return 商品分类类目
	 */
	@GetMapping("category")
	public Object category(@NotNull Integer id) {
		logger.info("【请求开始】商品分类类目,请求参数,id:{}", id);

		PspCategory cur = categoryService.findById(id);
		PspCategory parent = null;
		List<PspCategory> children = null;

		if (cur.getPid() == 0) {
			parent = cur;
			children = categoryService.queryByPid(cur.getId());
			cur = children.size() > 0 ? children.get(0) : cur;
		} else {
			parent = categoryService.findById(cur.getPid());
			children = categoryService.queryByPid(cur.getPid());
		}
		Map<String, Object> data = new HashMap<>();
		data.put("currentCategory", cur);
		data.put("parentCategory", parent);
		data.put("brotherCategory", children);

		logger.info("【请求结束】商品分类类目,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 根据条件搜素商品
	 * <p>
	 * 1. 这里的前五个参数都是可选的，甚至都是空 2. 用户是可选登录，如果登录，则记录用户的搜索关键字
	 *
	 * @param categoryId
	 *            分类类目ID，可选
	 * @param userId
	 *            用户ID
	 * @param page
	 *            分页页数
	 * @param size
	 *            分页大小
	 * @param sort
	 *            排序方式，支持"add_time", "retail_price"或"name",浏览量 "browse",销售量："sales"
	 * @param order
	 *            排序类型，顺序或者降序
	 * @return 根据条件搜素的商品详情
	 */
	@GetMapping("list")
	public Object list(Integer categoryId,
			@LoginUser Integer userId, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size,
			@Sort(accepts = { "create_time", "original_price","goods_title" }) @RequestParam(defaultValue = "create_time") String sort,
			@Order @RequestParam(defaultValue = "asc") String order) {

		logger.info("【请求开始】根据条件搜素商品,请求参数,categoryId:{},brandId:{},keyword:{}", categoryId);


		// 查询列表数据
		List<GoodsTab> goodsList = goodsService.querySelective(categoryId, page, size, sort, order);
		
		Map<String, Object> data = new HashMap<>();
		data.put("goodsList", goodsList);
		long count = PageInfo.of(goodsList).getTotal();
		int totalPages = (int) Math.ceil((double) count / size);
		data.put("count", PageInfo.of(goodsList).getTotal());
		data.put("totalPages", totalPages);

		logger.info("【请求结束】根据条件搜素商品,响应结果:查询的商品数量:{},总数：{},总共 {} 页", goodsList.size(),count,totalPages);
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 商品详情页面“大家都在看”推荐商品
	 *
	 * @param id,
	 *            商品ID
	 * @return 商品详情页面推荐商品
	 */
	@GetMapping("related")
	public Object related(@NotNull Integer id) {
		logger.info("【请求开始】商品详情页面“大家都在看”推荐商品,请求参数,id:{}", id);

		GoodsTab goods = goodsService.findById(id);
		if (goods == null) {
			return ResultVo.buildCode(ResultCode.PARAMETER_ERROR);
		}

		// 目前的商品推荐算法仅仅是推荐同类目的其他商品
		int cid = Integer.parseInt(goods.getGoodsType());
		//
		//// 查找六个相关商品,同店铺，同类优先
		//int limitBid = 10;
		//List<DtsGoods> goodsListBrandId = goodsService.queryByBrandId(brandId, cid, 0, limitBid);
		//List<DtsGoods> relatedGoods = goodsListBrandId == null ? new ArrayList<DtsGoods>() : goodsListBrandId;
		//if (goodsListBrandId == null || goodsListBrandId.size() < 6) {// 同店铺，同类商品小于6件，则获取其他店铺同类商品
		//	int limitCid = 6;
		//	List<DtsGoods> goodsListCategory = goodsService.queryByCategoryAndNotSameBrandId(brandId, cid, 0, limitCid);
		//	relatedGoods.addAll(goodsListCategory);
		//}
		Page<WxGoodsResponse> wxGoodsResponses = goodsTabMapper.queryWxCategoryByCondition(cid);

		Map<String, Object> data = new HashMap<>();
		data.put("relatedGoods", wxGoodsResponses.getResult());

		logger.info("【请求结束】商品详情页面“大家都在看”推荐商品,响应结果:查询的商品数量:{}", wxGoodsResponses.getResult().size());
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

	/**
	 * 在售的商品总数
	 *
	 * @return 在售的商品总数
	 */
	@GetMapping("count")
	public Object count() {
		logger.info("【请求开始】在售的商品总数...");
		Integer goodsCount = goodsService.queryOnSale();
		Map<String, Object> data = new HashMap<>();
		data.put("goodsCount", goodsCount);

		logger.info("【请求结束】在售的商品总数,响应结果:{}", JSONObject.toJSONString(data));
		return ResultVo.buildData(ResultCode.SUCCESS, data);
	}

}