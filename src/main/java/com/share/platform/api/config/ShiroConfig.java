package com.share.platform.api.config;

import com.share.platform.api.shiro.AdminAuthorizingRealm;
import com.share.platform.api.shiro.AdminWebSessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

	@Bean
	public Realm realm() {
		return new AdminAuthorizingRealm();
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = getStringStringMap();

		shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");
		shiroFilterFactoryBean.setSuccessUrl("/admin/auth/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/admin/auth/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	private static Map<String, String> getStringStringMap() {
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/admin/auth/login", "anon");
		filterChainDefinitionMap.put("/admin/auth/captchaImage", "anon");
		filterChainDefinitionMap.put("/admin/auth/401", "anon");
		filterChainDefinitionMap.put("/admin/auth/index", "anon");
		filterChainDefinitionMap.put("/admin/auth/403", "anon");

		//  静态路径放开  anon：匿名用户可访问
		filterChainDefinitionMap.put("/public/**", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");

		//  调试工具全部放开    anon：匿名用户可访问
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/platform-swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/swagger-resources", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/v2/api-docs/**", "anon");
		filterChainDefinitionMap.put("/platFormApi/**", "anon");

		// 所有其他路径都需要认证
		filterChainDefinitionMap.put("/**", "authc");
		return filterChainDefinitionMap;
	}

	@Bean
	public SessionManager sessionManager() {
		AdminWebSessionManager mySessionManager = new AdminWebSessionManager();
		return mySessionManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public static DefaultAdvisorAutoProxyCreator customAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}
}
