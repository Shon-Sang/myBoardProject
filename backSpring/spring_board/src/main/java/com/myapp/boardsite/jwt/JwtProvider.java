package com.myapp.boardsite.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.myapp.boardsite.security.CustomUserDetails;

@Component
public class JwtProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	public JwtProvider() {
	}
	
	// 로그인할 때 사용: 인증성공 후 jwt 생성
	// Access, Refresh
	public String createJwt(Authentication auth, String key) {
		CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
		
		String jwt = JWT.create()
								.withSubject(userDetails.getUsername())
								.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*3*1L))) // 3분
								.withClaim("username", userDetails.getUser().getUsername())
								.withClaim("authRole", userDetails.getUser().getAuthRole())
								.sign(Algorithm.HMAC512(key));
		return jwt;
	}
	
	// 로그인 후 사용: 인가에서 사용할 인증키 생성 
	public Authentication createAuthentication() {
		return null;
	}
}
