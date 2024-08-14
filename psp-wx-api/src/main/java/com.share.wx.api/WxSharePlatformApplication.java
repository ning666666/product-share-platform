package com.share.wx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 小程序服务启动类
 */
@SpringBootApplication(scanBasePackages = {"com.share.platform.api"})
public class WxSharePlatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(WxSharePlatformApplication.class, args);
	}
}