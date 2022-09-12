package com.myapp.boardsite.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.jwt.JwtProvider;

@Service
public class LoginServiceImp implements LoginService{
	
	// 이거 중요함(신경 써서 생각할 것, SecurityConfig에 Bean으로 어떻게 등록하는지 이해할 것)
	// 이게 있어야 인증을 할 수 있음
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;

	@Override
	public Map<String, String> SignIn(User user) {
		// 입력받은 아이디 비번 으로 인증을 확인할 토큰 생성
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		System.out.println("여기까지 들어왔음");
		// 실제 인증이 이루어져서 성공하면 토큰 생성
		Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		System.out.println("제발 성공해주세요.. auth : "+auth);
		
		String accessToken = jwtProvider.createJwt(auth, "access");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		return map;
	}
}
