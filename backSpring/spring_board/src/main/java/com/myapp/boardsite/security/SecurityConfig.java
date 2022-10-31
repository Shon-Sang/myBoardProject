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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.myapp.boardsite.jwt.JwtFilter;
import com.myapp.boardsite.jwt.JwtProvider;
import com.myapp.boardsite.jwt.JwtSecurityConfigure;
import com.myapp.boardsite.repository.UserRepository;

@EnableGlobalMethodSecurity(securedEnabled=true) // 컨트롤러 메소드에 권한 설정가능
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
//	private org.springframework.context.annotation.AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LoginUrlChange.class);
	// 2022-10-19 // jwtFilter를 Bean으로 등록함
	// autoWired가 안되서 이런식으로함(이 방법으로 하니까 됨..)
	private final UserRepository userRepository;
	//private final JwtProvider jwtProvider;
	private final CorsConfig corsConfig;
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(UserRepository userRepository, JwtFilter jwtFilter, /*JwtProvider jwtProvider,*/ CorsConfig corsConfig) {
		this.userRepository = userRepository;
		//this.jwtProvider = jwtProvider;
		this.jwtFilter = jwtFilter;
		this.corsConfig = corsConfig;
	}
	
	@Bean
	public BCryptPasswordEncoder encoderPassword() {
		return new BCryptPasswordEncoder();
	}

	// 여기서 AuthenticationManager을 Bean 등록해야 다른 Component(Bean)에서 사용할 수 있음
	@Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }
	
	// Cors설정, 될지 안될지 모름, jwt 필요한 요청은 안됨, 근데 jwt필요 없는 요청은 됨 
	// CorsFilter 클래스만들어서 밑에 쓴거 그대로 붙여넣고 
	// jwtFilter를 추가하는 Configure 클래스에 addFilter하니까 jwt필요한 요청도 됨(여기도 autoWired안되고 일일이 멤버변수, 생성자로 의존성 주입)
//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOriginPattern("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		
//		config.addExposedHeader("Authorization");
//		config.addExposedHeader("refreshToken");
//		source.registerCorsConfiguration("/**", config);
//		
//		return new CorsFilter(source);
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		// 필터 등록
		//httpSecurity.apply(new MyCustomConfigurer(jwtProvider)); // 수업시간에 배운대로 커스텀필터 등록
		httpSecurity.apply(new JwtSecurityConfigure(/*jwtProvider*/jwtFilter, corsConfig));
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
					.antMatchers("/myAuth/**").permitAll()
					.antMatchers("/board/all/**").permitAll()
					.anyRequest().authenticated();
		
		return httpSecurity.build();
	}
	
	// 현재 프로젝트에서 쓰지 않음
	// 직접 만든 필터 추가
	// https://www.baeldung.com/spring-security-custom-configurer
	public class MyCustomConfigurer extends AbstractHttpConfigurer<MyCustomConfigurer, HttpSecurity>{
		
		// 이렇게 하니 역시나 JwtAuthenticationFilter에서 AuthenticationManager객체가 null이라고뜸
//		@Autowired
//		AuthenticationManager authManager;
		
		// 이거 쓸려면 위에서 new할때 넣어야하는데 Bean을 여기서 생성하기때문에 안됨.
//		private final AuthenticationManager authManager;
//		
//		public MyCustomConfigurer(AuthenticationManager authenticationManager) {
//			this.authManager = authenticationManager;
//		}
		
		private JwtProvider jwtProvider;
		
		public MyCustomConfigurer(JwtProvider jwtProvider) {
			this.jwtProvider = jwtProvider;
		}
		
		@Override
		public void configure(HttpSecurity httpSecurity) throws Exception {
			
			httpSecurity.addFilter(new JwtFilter(jwtProvider));
			// AuthenticationManager를 만들어서 넣어줘야함
//			AuthenticationManager authManager = httpSecurity.getSharedObject(AuthenticationManager.class);
//			httpSecurity.addFilter(new JwtAuthenticationFilter(authManager, userRepository));
			
//			.addFilter(ac.getBean("getJwtAuthenticationFilter", JwtAuthenticationFilter.class));
		}
	}
	
	
}
