package com.share.platform.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String fileSavePath;

    /**
     * 图片存储在D:\web\image
     * 支持访问外部目录中的静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadedImagesPath = "file:" + fileSavePath;

        // 添加资源处理器，将/image/**路径的请求映射到指定的文件位置
        registry.addResourceHandler("/image/**")
                .addResourceLocations(uploadedImagesPath);
    }
}