package com.myapp.boardsite.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.repository.UserRepository;

// UsernamePasswordAuthenticationFilter 인증 시작 필터자체를 커스텀
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	// 인증을 하기위해 필요한 객체임
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepository;
	
	public JwtAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	// 로그인 할때 인증하는 부분
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("attemptAuthentication 메소드까지 잘 들어옴");
		//1. UsernamePasswordAuthentication에 넣을 username과 password를 받아와야함
		//2. UsernamePasswordAuthentication 생성
		//3. 생성한 UsernamePasswordAuthentication를 DB에 있는지 확인(인증)해야하는데 이를 authManager의 메소드를 이용해서 함
		
		try {
			// 1번 시작
			ObjectMapper obm = new ObjectMapper(); // request에서 user 클래스를 빼내기위해 사용
			
			/*
			 * 여기서 exception 발생할수있기에 강제 try,
			 * request.getInputStream() request의 body값을 읽어옴(로그인이라 당연히 post임),
			 * 역직렬화(json => object)
			*/
			User user = obm.readValue(request.getInputStream(), User.class);
			
			// 2번 시작
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			// 3번 시작
			Authentication authentication = authManager.authenticate(usernamePasswordAuthenticationToken);
			
			CustomUserDetails cud = (CustomUserDetails)authentication.getPrincipal();
			System.out.println("인증 성공 후 나온 키입니다.");
			System.out.println("아이디 : " + cud.getUsername() + "\n비밀번호 : " + cud.getPassword());
			
			return authentication;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("attemptAuthentication메소드의 try부분에서 에러 발생");
			e.printStackTrace();
		}
		return null;
	}
	
	// 성공적으로 위의 attemptAuthentication 메소드가 실행되서 authentication가 리턴 되면 실행되는 메소드
	// 여기서 JWT(access, refesh)를 만들고 response로 보내주면 됨
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		/*
//		 * 1. CustomUserDetail 생성(Authentication을 한번 저 클래스로 풀기어서 JWT만들기 위해)
//		 * 2. JWT 생성(access, refresh)
//		 * 3. response의 Header에 생성한 토큰을 추가(refresh 토큰은 db에 저장)
//		 * 4. response의 body에 유저데이터 추가(Map을 쓰던가 dto를 쓸것)
//		 */
//		 
//		// 1.
//		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();
//		
//		// 2.
//		String accessToken = JWT.create()
//								.withSubject(userDetails.getUsername())
//								.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*3*1L))) // 3분
//								.withClaim("username", userDetails.getUser().getUsername())
//								.withClaim("authRole", userDetails.getUser().getAuthRole())
//								.sign(Algorithm.HMAC512("myAcessKey"));
//		
//		String refreshToken = JWT.create()
//				.withSubject(userDetails.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis() + (60*1000*10*1L))) // 6분
//				.withClaim("username", userDetails.getUser().getUsername())
//				.withClaim("authRole", userDetails.getUser().getAuthRole())
//				.sign(Algorithm.HMAC512("myRefreshKey"));
//		// 3.
//		response.addHeader("AccessToken", "Bearer " + accessToken);
//		response.addHeader("RefreshToken", "Bearer " + refreshToken);
//		//refreshKey db 추가
////		Map<String, String> map = new HashMap<String, String>();
////		map.put("refreshToken", refreshToken);
////		map.put("username", userDetails.getUsername());
////		System.out.println("여기까지는 왔음");
////		
////		// 그냥 내가 직접 bean하나 만들어야할거같음 이방법도 안됨(내가못하는건지.., filter라서 기본적으로 Bean사용이 안된다고함)
//		// Intercepter로 DB에 저장할것
////		// AutoWired는 filter에서는 주입을 못시켜줌
////		userRepository.updateUserRefreshToken(map);
//		
//		// 4. dto 사용
//		ObjectMapper obm = new ObjectMapper();
//		obm.writeValue(response.getOutputStream(), userDetails.getUser());
//		System.out.println("여기까지 오면 다된거임");
//		
//	}
}
