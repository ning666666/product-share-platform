package com.share.dao.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfig {
	@Autowired
	private WxProperties properties;

	@Bean
	public WxMaConfig wxMaConfig() {
		WxMaInMemoryConfig config = new WxMaInMemoryConfig();
		config.setAppid(properties.getAppId());
		config.setSecret(properties.getAppSecret());
		// 可以设置更多配置，如token、aesKey等
		return config;
	}

	@Bean
	public WxMaService wxMaService(WxMaConfig maConfig) {
		WxMaService service = new WxMaServiceImpl();
		service.setWxMaConfig(maConfig);
		return service;
	}
}