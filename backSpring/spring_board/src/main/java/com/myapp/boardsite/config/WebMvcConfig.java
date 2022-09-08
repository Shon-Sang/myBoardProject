package com.myapp.boardsite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myapp.boardsite.intercepter.LoginIntercepter;

// 인터셉터 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoginIntercepter())
//				.addPathPatterns("/login"); //안됨
//	}
}
