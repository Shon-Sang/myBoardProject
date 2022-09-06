package com.myapp.boardsite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(securedEnabled=true) // 컨트롤러 메소드에 권한 설정가능
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encoderPassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		// 세큐리티 설정
		httpSecurity.csrf().disable()
					.formLogin().disable()
					.httpBasic().disable()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// 세큐리티 인증이 필요한 주소, 필요없는 주소 설정
		httpSecurity.authorizeHttpRequests()
					.antMatchers("/all/**").permitAll()
					.anyRequest().authenticated();
		
		return httpSecurity.build();
	}
}
