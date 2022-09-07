package com.myapp.boardsite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(securedEnabled=true) // 컨트롤러 메소드에 권한 설정가능
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
//	private org.springframework.context.annotation.AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LoginUrlChange.class);
	
	@Bean
	public BCryptPasswordEncoder encoderPassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		// 필터 등록
		httpSecurity.apply(new MyCustomConfigurer()); // 수업시간에 배운대로 커스텀필터 등록
		// 이 방법 예상과 다르게 안됨
//		AuthenticationManager authManager = httpSecurity.getSharedObject(AuthenticationManager.class);
//		httpSecurity.addFilter(new JwtAuthenticationFilter(authManager));
		
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
	
	// 직접 만든 필터 추가
	// https://www.baeldung.com/spring-security-custom-configurer
	public class MyCustomConfigurer extends AbstractHttpConfigurer<MyCustomConfigurer, HttpSecurity>{
		@Override
		public void configure(HttpSecurity httpSecurity) throws Exception {
			// AuthenticationManager를 만들어서 넣어줘야함
			AuthenticationManager authManager = httpSecurity.getSharedObject(AuthenticationManager.class);
			httpSecurity.addFilter(new JwtAuthenticationFilter(authManager))
						.addFilterAfter(new RefreshUpdateFilter(), JwtAuthenticationFilter.class);
//			.addFilter(ac.getBean("getJwtAuthenticationFilter", JwtAuthenticationFilter.class));
		}
	}
	
	
}
