package com.share.platform.api.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class AdminWebSessionManager extends DefaultWebSessionManager {

	public AdminWebSessionManager() {
		super();
		//在这里设置ShiroSession失效时间
		//setGlobalSessionTimeout(MILLIS_PER_MINUTE * 30);
		setGlobalSessionTimeout(Long.MAX_VALUE);
	}


	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		//获取请求头中的token值，如果请求头中有token值，则取巧认为其值为会话的sessionId(那么用户在登陆的时候需要给前端传送这个sessionId)
		String sessionId = WebUtils.toHttp(request).getHeader(ShiroConstant.authorization_token);
		if (StringUtils.isEmpty(sessionId)){
			//如果没有携带sessionId的参数,直接按照父类的方式在cookie进行获取sessionId
			return super.getSessionId(request, response);
		} else {
			//请求头中如果有token, 则其值为sessionId(登陆的时候就传送这个sessionId)
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "request cookie");
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId); // 这里加上sessionId
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return sessionId;
		}
	}
}
