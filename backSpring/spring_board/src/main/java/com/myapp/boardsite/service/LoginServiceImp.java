package com.myapp.boardsite.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.jwt.JwtProvider;
import com.myapp.boardsite.repository.UserRepository;
import com.myapp.boardsite.security.CustomUserDetails;

@Service
public class LoginServiceImp implements LoginService{
	
	// 이거 중요함(신경 써서 생각할 것, SecurityConfig에 Bean으로 어떻게 등록하는지 이해할 것)
	// 이게 있어야 인증을 할 수 있음
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Map<String, String> SignIn(User user) {
		// 입력받은 아이디 비번 으로 인증을 확인할 토큰 생성
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		// 실제 인증이 이루어져서 성공하면 토큰 생성
		Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		String accessToken = jwtProvider.createJwt(auth, "access", 10);
		String refreshToken = jwtProvider.createJwt(auth, "refresh", 60);
		
		Map<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("refreshToken", refreshToken);
		updateMap.put("username", user.getUsername());
		userRepository.updateUserRefreshToken(updateMap);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("refreshToken", refreshToken);
		return map;
	}
	
	@Override
	public Map<String, String> refresh(Map<String, String> map, HttpServletResponse response) {
		Map<String, String> tokenMap = new HashMap<String, String>();
		String refreshToken = map.get("refreshToken");
		try {
			String username = JWT.require(Algorithm.HMAC512("refresh")).build().verify(refreshToken).getClaim("username").asString();
			User user = userRepository.selectUser(username);
			String accessToken = jwtProvider.createJwt(user, "access", 10);
			tokenMap.put("accessToken", accessToken);
			
		}catch(TokenExpiredException e) {
			response.addHeader("RefreshTokenError", "Expired RefreshToken!!");
			tokenMap.put("RefreshTokenError", "Expired");
		}catch(Exception e) {
			response.addHeader("RefreshTokenError", "Invalid RefreshToken!!");
			tokenMap.put("RefreshTokenError", "Invalid");
		}
		
		return tokenMap;
	}
}
