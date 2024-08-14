package com.share.platform.api.shiro;

import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.utils.ResultVo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理类
 */
@ControllerAdvice(basePackages = "com.share.platform.api.controller")
public class ShiroExceptionHandler {

	public Logger log = LoggerFactory.getLogger(this.getClass());

	//  全局异常：默认异常
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultVo defaultExceptionHandler(HttpServletRequest request, Exception e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData(request.getRequestURI() + e.toString());
	}

	@ExceptionHandler(BindException.class)
	@ResponseBody
	public ResultVo bindExceptionHandler(HttpServletRequest request, BindException e) {
		return ResultVo.buildData(e.toString());
	}


	//  全局异常：请求header缺少HeaderToken
	@ExceptionHandler(ServletRequestBindingException.class)
	@ResponseBody
	public ResultVo ServletRequestBindingExceptionHandler(HttpServletRequest request, ServletRequestBindingException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildCode(ResultCode.NOT_TOKEN_LOGIN);
	}

	//  全局异常：请求内容类型异常
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseBody
	public ResultVo HttpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData(e.toString());
	}

	//  全局异常：请求方法异常
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResultVo HttpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData(e.toString());
	}

	//  全局异常：请求参数格式或者参数类型不正确异常
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResultVo HttpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData(e.toString());
	}

	//  shiro 权限不可用
	@ExceptionHandler(InvalidPermissionStringException.class)
	@ResponseBody
	public ResultVo InvalidPermissionStringException(HttpServletRequest request, IncorrectCredentialsException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("你的权限不可用");
	}

	// shiro 未授权异常
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	public ResultVo UnauthorizedExceptionHandler(HttpServletRequest request, UnauthorizedException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("未授权，请重新登录");
	}

	//  shiro 授权异常
	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public ResultVo AuthorizationException(HttpServletRequest request, AuthorizationException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("授权用户不存在或已经过期，请重新登录");
	}

	//  shiro 未经身份验证或身份验证异常
	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseBody
	public ResultVo UnauthenticatedException(HttpServletRequest request, UnauthenticatedException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("未经身份验证，身份验证异常，请重新登录");
	}

	//  shiro 账号锁定异常
	@ExceptionHandler(LockedAccountException.class)
	@ResponseBody
	public ResultVo LockedAccountException(HttpServletRequest request, LockedAccountException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("你的账号已锁定，请联系管理员解锁");
	}

	//  shiro 未找到用户异常
	@ExceptionHandler(UnknownAccountException.class)
	@ResponseBody
	public ResultVo UnknownAccountException(HttpServletRequest request, UnknownAccountException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("你的账号不存在");
	}

	//  shiro 登录用户密码校验异常
	@ExceptionHandler(IncorrectCredentialsException.class)
	@ResponseBody
	public ResultVo IncorrectCredentialsException(HttpServletRequest request, IncorrectCredentialsException e) {
		log.error(request.getRequestURI() + "----" + e.toString());
		return ResultVo.buildData("你输入的密码错误");
	}
}

