package com.myapp.boardsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/prac01")
	public String confirmUser() {
		return "잘되는거같아요";
	}
}
