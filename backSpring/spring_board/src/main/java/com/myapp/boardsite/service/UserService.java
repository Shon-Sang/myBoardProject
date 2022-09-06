package com.myapp.boardsite.service;

import java.util.List;

import com.myapp.boardsite.dto.User;

public interface UserService {
	public User searchUserInfo(String username);
	public List<User> searchAllUserInfo();
	public int joinUserInfo(User user);
	public int deleteUserInfo(String username);
}
