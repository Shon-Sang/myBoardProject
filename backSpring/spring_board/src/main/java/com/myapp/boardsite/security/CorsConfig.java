package com.myapp.boardsite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.myapp.boardsite.jwt.JwtFilter;
import com.myapp.boardsite.jwt.JwtProvider;

@Configuration
@EnableWebSecurity
public class CorsConfig {
	
//	private JwtProvider jwtProvider;
//	
//	public CorsConfig(JwtProvider jwtProvider) {
//		this.jwtProvider = jwtProvider;
//	}
	
	// 만약 Component 어노테이션을 쓰고싶다면 CorsFilter를 상속한 클래스를 직접 만들어서 해야할듯
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		
		config.addExposedHeader("Authorization");
		config.addExposedHeader("refreshToken");
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
	}

//	@Bean
//	public JwtFilter jwtFilter() {
//		
//		return new JwtFilter(jwtProvider);
//	}
}
