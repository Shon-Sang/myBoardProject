package com.myapp.boardsite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.service.LoginService;

@RestController
@RequestMapping("/myAuth")
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody User user){
		System.out.println("현재 컨트롤러");
		System.out.println("user : " + user.getUsername());
		return service.SignIn(user);
	}
}
