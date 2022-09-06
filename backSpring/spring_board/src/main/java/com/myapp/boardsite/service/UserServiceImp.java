package com.myapp.boardsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.boardsite.dto.User;
import com.myapp.boardsite.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	public UserServiceImp() {
	}
	
	@Override
	public User searchUserInfo(String username) {
		return userRepository.selectUser(username);
	}
	
	@Override
	public int joinUserInfo(User user) {
		return userRepository.insertUser(user);
	}
	
	@Override
	public int deleteUserInfo(String username) {
		return userRepository.deleteUser(username);
	}
	
	@Override
	public List<User> searchAllUserInfo() {
		return userRepository.selectAllUser();
	}
}
