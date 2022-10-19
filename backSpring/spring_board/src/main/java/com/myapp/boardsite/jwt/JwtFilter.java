package com.myapp.boardsite.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// 요청할 때 처음 한번만 실행되는 필터
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	
	private JwtProvider jwtProvider;
	
	public JwtFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = jwtProvider.getJWT(request);

		try {
			if(jwtProvider.validateToken(jwt, "access") && jwt != null) {
				Authentication auth = jwtProvider.createAuthentication(jwt, "access");
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		}catch (Exception e) {
			System.out.println("오류 발생2");
			SecurityContextHolder.clearContext();
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
}
