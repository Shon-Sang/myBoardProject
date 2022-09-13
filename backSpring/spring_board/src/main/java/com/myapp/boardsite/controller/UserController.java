package com.myapp.boardsite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.security.CustomUserDetails;
import com.myapp.boardsite.service.UserService;

// 인증을 해야 접근이 가능함(토큰이 있어야함)
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
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
	
	// 요청보내면 해당유저 삭제
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(Authentication auth){
		String username = getUserName(auth);
		userService.deleteUserInfo(username);
		return ResponseEntity.ok(username);
	}
	
	public String getUserName(Authentication auth) {
		CustomUserDetails customUserDetails = (CustomUserDetails)auth.getPrincipal();
		return customUserDetails.getUsername();
	}
}
