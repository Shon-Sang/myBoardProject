package com.myapp.boardsite.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myapp.boardsite.security.CorsConfig;

public class JwtSecurityConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	private CorsConfig corsConfig;
	
	//private JwtProvider jwtProvider;
	
	private JwtFilter jwtFilter;
	
	//@Autowired
	public JwtSecurityConfigure(JwtFilter jwtFilter,/*JwtProvider jwtProvider,*/ CorsConfig corsConfig) {
		//this.jwtProvider = jwtProvider;
		this.jwtFilter = jwtFilter;
		this.corsConfig = corsConfig;
	}
	
	// 이거 안됨 JwtFilter.doFilterInternal 에서 null jwtProvider가 null이라고 뜸..
//	@Autowired
//	JwtProvider jwtProvider;
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		//JwtFilter jwtFilter = new JwtFilter(jwtProvider);
		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
					.addFilter(corsConfig.corsFilter());
	}
}
