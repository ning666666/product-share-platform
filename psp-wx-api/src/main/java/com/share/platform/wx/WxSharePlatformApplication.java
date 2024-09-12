package com.share.platform.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 小程序服务启动类
 */
@SpringBootApplication(scanBasePackages = { "com.share.platform.wx"})
@EnableScheduling
@EnableTransactionManagement
public class WxSharePlatformApplication {
	public static void main(String[] args) {
		SpringApplication.run(WxSharePlatformApplication.class, args);
	}
}