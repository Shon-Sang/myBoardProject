package com.myapp.boardsite.service;

import java.util.Map;

import com.myapp.boardsite.dto.User;

public interface LoginService {
	public Map<String, String> SignIn(User user);
}