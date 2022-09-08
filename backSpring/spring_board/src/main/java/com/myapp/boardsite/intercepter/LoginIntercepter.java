package com.myapp.boardsite.intercepter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.boardsite.repository.UserRepository;
import com.myapp.boardsite.security.CustomUserDetails;

public class LoginIntercepter implements HandlerInterceptor{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		
		/*
		 * 1. CustomUserDetail 생성(Authentication을 한번 저 클래스로 풀기어서 JWT만들기 위해)
		 * 2. JWT 생성(access, refresh)
		 * 3. response의 Header에 생성한 토큰을 추가(refresh 토큰은 db에 저장)
		 * 4. response의 body에 유저데이터 추가(Map을 쓰던가 dto를 쓸것)
		 */
		 
		// 1.
		CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
		
		// 2.
		String accessToken = JWT.create()
								.withSubject(userDetails.getUsername())
								.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*3*1L))) // 3분
								.withClaim("username", userDetails.getUser().getUsername())
								.withClaim("authRole", userDetails.getUser().getAuthRole())
								.sign(Algorithm.HMAC512("myAcessKey"));
		
		String refreshToken = JWT.create()
				.withSubject(userDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*10*1L))) // 6분
				.withClaim("username", userDetails.getUser().getUsername())
				.withClaim("authRole", userDetails.getUser().getAuthRole())
				.sign(Algorithm.HMAC512("myRefreshKey"));
		// 3.
		response.addHeader("AccessToken", "Bearer " + accessToken);
		response.addHeader("RefreshToken", "Bearer " + refreshToken);
		//refreshKey db 추가
		Map<String, String> map = new HashMap<String, String>();
		map.put("refreshToken", refreshToken);
		map.put("username", userDetails.getUsername());
		System.out.println("여기까지는 왔음");
		
		// 그냥 내가 직접 bean하나 만들어야할거같음 이방법도 안됨(내가못하는건지.., filter라서 기본적으로 Bean사용이 안된다고함)
		// Intercepter로 DB에 저장할것
		// AutoWired는 filter에서는 주입을 못시켜줌
		userRepository.updateUserRefreshToken(map);
		
		// 4. dto 사용
		ObjectMapper obm = new ObjectMapper();
		obm.writeValue(response.getOutputStream(), userDetails.getUser());
		System.out.println("여기까지 오면 다된거임");
		
		return true;
	}
}
