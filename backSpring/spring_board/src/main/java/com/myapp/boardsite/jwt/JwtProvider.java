package com.myapp.boardsite.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.myapp.boardsite.security.CustomUserDetails;

@Component
public class JwtProvider {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	public JwtProvider() {
	}
	
	// 로그인할 때 사용: 인증성공 후 jwt 생성
	// Access, Refresh
	public String createJwt(Authentication auth, String key, int keyTime) {
		CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
		
		String jwt = JWT.create()
								.withSubject(userDetails.getUsername())
								.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*keyTime*1L))) // 3분
								.withClaim("username", userDetails.getUser().getUsername())
								.withClaim("authRole", userDetails.getUser().getAuthRole())
								.sign(Algorithm.HMAC512(key));
		return jwt;
	}
	
	
	//--------------------
	//이 밑의 메소드들은 토큰을 가지고 로그인이 필요한 url에 요청할 때 쓰는 메소드들임 (인가) 
	
	// request의 헤더에서 JWT 뽑아옴
	public String getJWT(HttpServletRequest request) {
		
		String jwtHeader = request.getHeader("Authorization");
		// 이거 먼저 확인을 해야함
		if(jwtHeader == null) {
			return null;
		}
		else if(jwtHeader.startsWith("Bearer ")) {
			return jwtHeader.replace("Bearer ", "");
		}
		
		return null;
	}
	
	// 뽑아온 JWT가 유효한지 확인
	public boolean validateToken(String jwt, String key) {
		
		try {
			JWT.require(Algorithm.HMAC512(key)).build().verify(jwt);
			return true;
		}catch(TokenExpiredException e){
			System.out.println("JWT가 만료되었습니다.");
			return false;
		}catch(Exception e) {
			System.out.println("JWT가 잘못되었습니다.(여기는 인증이 필요없는 요청도 지나가는 곳입니다.)");
			return false;
		}
		
	}
	
	// JWT로 인증키객체를 생성
	public Authentication createAuthentication(String jwt, String key) {
		String username = JWT.require(Algorithm.HMAC512(key)).build().verify(jwt).getClaim("username").asString();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
}
