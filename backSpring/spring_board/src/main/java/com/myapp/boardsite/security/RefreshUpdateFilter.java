package com.myapp.boardsite.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.boardsite.repository.UserRepository;

public class RefreshUpdateFilter extends GenericFilterBean{
	
	@Autowired
	private UserRepository userRepository;
	
	public RefreshUpdateFilter() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Authentication authResult = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("authResult : " + authResult);
		if(authResult == null) {
			chain.doFilter(request, response);
		}
		
		/*
		 * 1. CustomUserDetail 생성(Authentication을 한번 저 클래스로 풀기어서 JWT만들기 위해)
		 * 2. JWT 생성(access, refresh)
		 * 3. response의 Header에 생성한 토큰을 추가(refresh 토큰은 db에 저장)
		 * 4. response의 body에 유저데이터 추가(Map을 쓰던가 dto를 쓸것)
		 */
		 
		// 1.
		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();
		
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
		((HttpServletResponse) response).addHeader("AccessToken", "Bearer " + accessToken);
		((HttpServletResponse) response).addHeader("RefreshToken", "Bearer " + refreshToken);
		//refreshKey db 추가
		Map<String, String> map = new HashMap<String, String>();
		map.put("refreshToken", refreshToken);
		map.put("username", userDetails.getUsername());
		System.out.println("여기까지는 왔음");
//		
//		// 그냥 내가 직접 bean하나 만들어야할거같음
//		// AutoWired는 filter에서는 주입을 못시켜줌
		userRepository.updateUserRefreshToken(map);
		
		// 4. dto 사용
		ObjectMapper obm = new ObjectMapper();
		obm.writeValue(response.getOutputStream(), userDetails.getUser());
		System.out.println("여기까지 오면 다된거임");
		chain.doFilter(request, response);
		
	}


}
