package com.myapp.boardsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.service.UserService;

@RestController
@RequestMapping("/all")
public class AllController {

	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/prac01")
	public String myprac01() {
		return "토큰없이 잘 동작합니다.";
	}
	
	@GetMapping("/serachUser/{username}")
	public User searchUser(@PathVariable("username") String username) {
		System.out.println("들어왔음1");
		System.out.println(username);
		return userService.searchUserInfo(username);
	}
	
	@GetMapping("/searchAllUser")
	public List<User> serachAllUser(){
		return userService.searchAllUserInfo();
	}
	
	@PostMapping("/searchUser")
	public User searchUserPost(@RequestBody User user) {
		return userService.searchUserInfo(user.getUsername());
	}
	
	@PostMapping("/insertUser")
	public ResponseEntity<User> joinUser(@RequestBody User user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.joinUserInfo(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/deleteUser/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username){
		if(userService.deleteUserInfo(username) == 1) {
			return ResponseEntity.ok(username);
		}else {
			return ResponseEntity.ok("Not Found Delete Data");
		}
	}
}
