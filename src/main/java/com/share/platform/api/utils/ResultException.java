package com.share.platform.api.utils;


import com.share.platform.api.constant.ResultCode;

/**
 * @author fan
 */
public class ResultException extends Exception{
	
	private static final long serialVersionUID = 1L;

	private ResultCode emsCode;
	private String message;
	
	public ResultException(Throwable throwable) {
		Throwable causeThrowable = getCauseException(throwable);
		this.emsCode = ResultCode.RUNTIME_ERROR;
		this.message = causeThrowable.getMessage();
		causeThrowable.printStackTrace();
	}
	
	public Throwable getCauseException(Throwable throwable) {
		if(throwable.getCause()!=null) {
			return getCauseException(throwable.getCause());
		}
		return throwable;
	}
	
	public ResultException(String message) {
		this.emsCode = ResultCode.RUNTIME_ERROR;
		this.message = message;
	}
	
	public ResultException(ResultCode emsCode) {
		this.emsCode = emsCode;
		this.message = emsCode.getMsg();
	}
	
	public ResultException(ResultCode emsCode, String message) {
		this.emsCode = emsCode;
		this.message = message;
	}
	
	public ResultCode getResultCode() {
		return emsCode;
	}

	public void setVlanCode(ResultCode emsCode) {
		this.emsCode = emsCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
