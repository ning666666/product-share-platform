package com.share.platform.wx.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.share.platform.wx.constant.ResultCode;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author fan.fu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<D> {
	
	/**
	 * 返回码
	 */
	private Integer code;
	
	/**
	 * 提示信息
	 */
	private String msg = "";
	
	/**
	 * 输出对象
	 */
	private D data;

	/**
	 * 额外数据
	 */
	private Map<String, Object> extra;

	public ResultVo<D> addExtraIfTrue(boolean val, String key, Object value) {
		if (val) {
			addExtra(key, value);
		}
		return this;
	}

	public ResultVo<D> addExtra(String key, Object value) {
		extra = extra == null ? new HashMap<>(16) : extra;
		extra.put(key, value);
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public ResultVo<D> setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ResultVo<D> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public D getData() {
		return data;
	}

	public ResultVo<D> setData(D data) {
		this.data = data;
		return this;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

	public static  <D> ResultVo<D> buildInstance() {
		return new ResultVo<D>();
	}
	
	public static <D> ResultVo<D> buildData(D data) {
		ResultVo<D> resultVo = ResultVo.buildInstance();
		resultVo.setData(data);
		return resultVo;
	}

	public static <D> ResultVo<D> buildData(ResultCode emsCode, D data, Object... params) {
		ResultVo<D> resultVo = ResultVo.buildInstance();
		resultVo.setCode(emsCode.getCode());
		resultVo.setMsg(String.format(emsCode.getMsg(), params));
		resultVo.setData(data);
		return resultVo;
	}
	
	public static ResultVo<?> buildError(ResultException exception) {
		return ResultVo.buildInstance().setCode(exception.getResultCode().getCode())
									   .setMsg(exception.getMessage());

	}
	
	public static ResultVo<?> buildSuccess() {
		return ResultVo.buildInstance().setCode(ResultCode.SUCCESS.getCode())
										.setMsg(ResultCode.SUCCESS.getMsg());
	}
	
	public static ResultVo<?> buildCode(ResultCode emsCode) {
		return ResultVo.buildInstance().setCode(emsCode.getCode())
									   .setMsg(emsCode.getMsg());
	}
	
	public static ResultVo<?> buildCode(ResultCode emsCode,Object... params) {
		return ResultVo.buildInstance().setCode(emsCode.getCode())
									   .setMsg(String.format(emsCode.getMsg(), params))
									   .setData(new WeakHashMap<>());
	}

	public static ResultVo<?> buildCode(ResultCode emsCode, String customMsg) {
		return ResultVo.buildInstance().setCode(emsCode.getCode())
				.setMsg(emsCode.getMsg() + "，" + customMsg);
	}
}

