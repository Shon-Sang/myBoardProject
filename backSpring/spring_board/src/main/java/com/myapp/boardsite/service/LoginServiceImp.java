package com.myapp.boardsite.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.jwt.JwtProvider;
import com.myapp.boardsite.repository.UserRepository;

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
		System.out.println("로그인 아이디 비번맞아서 여기까지 옴");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		System.out.println("여기까지 들어왔음");
		// 실제 인증이 이루어져서 성공하면 토큰 생성
		Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		System.out.println("제발 성공해주세요.. auth : "+auth);
		
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
	public Map<String, String> refresh(Map<String, String> map) {
		
		String refreshToken = map.get("refreshToken");
		try {
			String username = JWT.require(Algorithm.HMAC512("refresh")).build().verify(refreshToken).getClaim("username").asString();
			User user = userRepository.selectUser(username);
			String accessToken = JWT.create()
					.withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*10*1L))) // 10분
					.withClaim("username", user.getUsername())
					.withClaim("authRole", user.getAuthRole())
					.sign(Algorithm.HMAC512("access"));
			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put("accessToken", accessToken);
			return tokenMap;
		}catch(TokenExpiredException e) {
			System.out.println("refresh토큰이 만료됨");
			System.out.println("다시 로그인 하세요");
			return null;
		}catch(Exception e) {
			System.out.println("refresh토큰이 잘못됨");
			return null;
		}
	}
}
