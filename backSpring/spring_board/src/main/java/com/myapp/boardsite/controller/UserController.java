package com.myapp.boardsite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.security.CustomUserDetails;

// 인증을 해야 접근이 가능함(토큰이 있어야함)
@RestController
@RequestMapping("/user")
public class UserController {
	
	//Secured 작동함(SecurityConfig에서 @EnableGlobalMethodSecurity(securedEnabled=true) 설정으로 되는거같음)
	
	@Secured("ROLE_MANAGER")
	@GetMapping("/prac01")
	public User confirmUser01(Authentication auth) {
		//Map<String, String> map = new HashMap<String, String>();
		CustomUserDetails customUserDetails = (CustomUserDetails)auth.getPrincipal();
		return customUserDetails.getUser();
	}
	
	@Secured("ROLE_MEMBER")
	@GetMapping("/prac02")
	public User confirmUser02(Authentication auth) {
		//Map<String, String> map = new HashMap<String, String>();
		CustomUserDetails customUserDetails = (CustomUserDetails)auth.getPrincipal();
		return customUserDetails.getUser();
	}
}
