package com.example.exceldemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置访问自定义的资源访问路径
 * @author Administrator
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${upload.dir}")
	private String filePath;

	/**
	 *  添加静态资源映射路径，images,css、js等都放在classpath下的static中
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		/**
		 *  addResourceHandler 指的是对外暴露的访问路径
		 *  addResourceLocations 指的是文件配置的目录
		 */
		//文件上传路径映射
		registry.addResourceHandler("/company/**").addResourceLocations("file:"+filePath);
	}
}
